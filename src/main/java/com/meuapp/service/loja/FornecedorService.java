package main.java.com.meuapp.service.loja;

import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.ProdutoRepository;
import main.java.com.meuapp.service.produto.ProdutoService;
import main.java.com.meuapp.service.venda.CompraFornecedor;
import main.java.com.meuapp.service.venda.ItemCompra;

import java.util.HashMap;
import java.util.Map;

public class FornecedorService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    // compras em andamento (notaFiscal -> compra)
    private final Map<String, CompraFornecedor> comprasEmAndamento = new HashMap<>();

    public FornecedorService(
            ProdutoRepository produtoRepository,
            ProdutoService produtoService) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
    }

    public void adicionarItem(
            String numeroNotaFiscal,
            String idProduto,
            int qtdProduto
    ) {

        CompraFornecedor compra = comprasEmAndamento.computeIfAbsent(
                numeroNotaFiscal,
                CompraFornecedor::new
        );

        Produto produto = produtoRepository.buscarProdutoPorId(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        compra.adicionarItem(produto, qtdProduto);
    }

    public void finalizarCompra(String numeroNotaFiscal) {

        CompraFornecedor compra = comprasEmAndamento.get(numeroNotaFiscal);

        if (compra == null) {
            throw new IllegalArgumentException("Compra não encontrada");
        }

        if (compra.isFinalizada()) {
            throw new IllegalStateException("Compra já finalizada");
        }

        for (ItemCompra item : compra.getItens()) {
            produtoService.adicionarAoEstoque(
                    item.getProduto().getIdProduto(),
                    item.getQuantidade()
            );
        }

        compra.finalizar();
        comprasEmAndamento.remove(numeroNotaFiscal);
    }
}
