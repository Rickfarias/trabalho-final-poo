package main.java.com.meuapp.service.produto;

import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.ProdutoRepository;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void adicionarAoEstoque(String idProduto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Produto produto = produtoRepository.buscarProdutoPorId(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        produto.adicionarQuantidade(quantidade);
        produtoRepository.salvar(produto);
    }

    public void removerDoEstoque(String idProduto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Produto produto = produtoRepository.buscarProdutoPorId(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (produto.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }

        produto.removerQuantidade(quantidade);
        produtoRepository.salvar(produto);
    }

    public void listarEstoque() {
        produtoRepository.listarProdutos();
    }
}
