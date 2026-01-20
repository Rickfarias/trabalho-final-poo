package main.java.com.meuapp.service.loja;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.LojaBloqueadaException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.FornecedorRepository;
import main.java.com.meuapp.repository.ProdutoRepository;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.produto.ProdutoService;
import main.java.com.meuapp.service.venda.CompraFornecedor;
import main.java.com.meuapp.service.venda.ItemCompra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FornecedorService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final ContaService contaService;
    FornecedorRepository fornecedorRepository;

    // compras em andamento (notaFiscal -> compra)
    private final Map<String, CompraFornecedor> comprasEmAndamento = new HashMap<>();

    public FornecedorService(
            ProdutoRepository produtoRepository,
            ProdutoService produtoService,
            ContaService contaService,
            FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
        this.contaService = contaService;
        this.fornecedorRepository = fornecedorRepository;
    }

    public void iniciarCompra(String numeroNotaFiscal, Loja loja, Fornecedor fornecedor) {
        if (comprasEmAndamento.containsKey(numeroNotaFiscal)) {
            throw new IllegalArgumentException("Compra já iniciada com essa nota fiscal.");
        }

        CompraFornecedor novaCompra = new CompraFornecedor(numeroNotaFiscal, loja, fornecedor);
        comprasEmAndamento.put(numeroNotaFiscal, novaCompra);
    }

    public void adicionarItem(String numeroNotaFiscal, String idProduto, int qtdProduto) {
        CompraFornecedor compra = comprasEmAndamento.get(numeroNotaFiscal);

        if (compra == null) {
            throw new IllegalArgumentException("Inicie a compra com a Nota Fiscal primeiro.");
        }

        Produto produto = produtoRepository.buscarProdutoPorId(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não cadastrado no sistema."));

        compra.adicionarItem(produto, qtdProduto);
    }

    public void finalizarCompra(String numeroNotaFiscal) throws ContaInexistenteException {
        CompraFornecedor compra = comprasEmAndamento.get(numeroNotaFiscal);

        if (compra == null) {
            throw new IllegalArgumentException("Compra não encontrada");
        }

        if (compra.isFinalizada()) {
            throw new IllegalStateException("Compra já finalizada");
        }

        Loja loja = compra.getLoja();

        if (loja.getStatusLoja() == StatusLoja.BLOQUEADA) {
            throw new LojaBloqueadaException(
                    "Loja bloqueada! Foram realizadas " + loja.getTentativasFalhadas() +
                            " operações mal sucedidas. Entre em contato com o suporte."
            );
        }

        Fornecedor fornecedor = compra.getFornecedor();
        double valorTotal = compra.calcularTotalNota();
        double saldoAtual = loja.getContaEmpresarial().getSaldo();

        boolean isPrimeiraCompra = loja.getTentativasFalhadas() == 0
                && saldoAtual == 0.0
                && loja.getStatusLoja() == StatusLoja.ATIVA;

        if (!isPrimeiraCompra && saldoAtual < valorTotal) {
            loja.incrementarTentativasFalhadas();

            if (loja.getTentativasFalhadas() >= 3 || loja.deveBloqueada()) {
                loja.setStatusLoja(StatusLoja.BLOQUEADA);
                throw new LojaBloqueadaException(String.format(
                        """
                        LOJA BLOQUEADA! Saldo insuficiente pela %d consecutiva vez.
                        Saldo insuficiente! Saldo disponível: R$ %.2f | Valor da compra: R$ %.2f | Faltam: R$ %.2f
                        """, loja.getTentativasFalhadas(), saldoAtual, valorTotal, valorTotal - saldoAtual
                ));
            }
            else {
                loja.setStatusLoja(StatusLoja.PENDENTE);
                throw new SaldoInsuficienteException(
                        String.format(
                                """
                                Saldo insuficiente! (%d/%d tentativas falhadas)
                                Saldo insuficiente! Saldo disponível: R$ %.2f | Valor da compra: R$ %.2f | Faltam: R$ %.2f
                                """,loja.getTentativasFalhadas(), 3, saldoAtual, valorTotal, valorTotal - saldoAtual
                        ));
            }
        }

        contaService.transferir(
                loja.getContaEmpresarial(),
                fornecedor.getContaEmpresarialFornecedor(),
                valorTotal
        );

        try {
            for (ItemCompra item : compra.getItens()) {
                produtoService.adicionarAoEstoque(
                        item.getProduto().getIdProduto(),
                        item.getQuantidade()
                );
            }
        } catch (Exception e) {
            try {
                contaService.transferir(
                        fornecedor.getContaEmpresarialFornecedor(),
                        loja.getContaEmpresarial(),
                        valorTotal
                );
                System.err.println("Transferência revertida devido a erro no estoque.");
            } catch (Exception ex) {
                System.err.println("ERRO CRÍTICO: Não foi possível reverter transferência!");
            }
            throw new IllegalStateException("Erro ao atualizar estoque: " + e.getMessage());
        }


        compra.finalizar();
        loja.setStatusLoja(StatusLoja.ATIVA);

        if (loja.getContaEmpresarial().getSaldo() == 0) {
            loja.incrementarTentativasFalhadas();
        } else {
            loja.resetarTentativasFalhadas();
        }

        if (loja.getTentativasFalhadas() >= 3) {
            loja.setStatusLoja(StatusLoja.BLOQUEADA);
        }

        comprasEmAndamento.remove(numeroNotaFiscal);
    }

    public boolean existeCompraEmAndamento(String numeroNotaFiscal) {
        return comprasEmAndamento.containsKey(numeroNotaFiscal);
    }

    public Optional<Fornecedor> buscarPorId(String idFornecedor) {
        return fornecedorRepository.buscarProdutoPorId(idFornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.acharTodos();
    }
}
