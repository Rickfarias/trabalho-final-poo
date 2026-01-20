package main.java.com.meuapp.service.venda;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.model.venda.Venda;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.ContaService;

import java.util.List;

public class VendaService {
    LojaRepository lojaRepository;
    ContaService contaService;

    public VendaService(LojaRepository lojaRepository, ContaService contaService) {
        this.lojaRepository = lojaRepository;
        this.contaService = contaService;
    }

    public void realizarVenda(
            String nomeLoja,
            Cliente cliente,
            String idProduto,
            int quantidade) throws ContaInexistenteException {

        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));


        ContaBancaria contaCliente = cliente.getConta();
        ContaBancaria contaLoja = loja.getContaEmpresarial();
        Produto produto = loja.getEstoqueList().get(idProduto);

        if (produto == null) {
            throw new IllegalArgumentException("Produto não existe no estoque desta loja.");
        }
        if (produto.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente! Disponível: " + produto.getQuantidade());
        }

        contaService.transferir(contaCliente, contaLoja, quantidade);
        // Para o cliente é 30% mais caro e o getPrecoVenda ja tem isso registrado
        double valorTotal = produto.getPrecoVenda() * quantidade;
        contaService.transferir(cliente.getConta(), loja.getContaEmpresarial(), valorTotal);


        produto.setQuantidade(produto.getQuantidade() - quantidade);

        if (loja.getStatusLoja() == StatusLoja.PENDENTE) {
            loja.setStatusLoja(StatusLoja.ATIVA);
            loja.resetarTentativasFalhadas();
        }

        Venda venda = new Venda(cliente, loja, quantidade);
        loja.registrarVenda(venda);
    }

    public List<String> listarNomesFornecedores(String nomeLoja) {
        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        return loja.getFornecedores().stream()
                .map(Fornecedor::getNomeFornecedor)
                .toList();
    }

    public List<Venda> historicoVendas(String nomeLoja) {
        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        return loja.getHistoricoVendas();
    }

    public double calcularValorVenda(double valorCompra) {
        if (valorCompra <= 0) {
            throw new IllegalArgumentException("Valor de compra inválido");
        }

        return valorCompra * 1.3;
    }



}
