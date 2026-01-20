package main.java.com.meuapp.service.produto;

import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarNovoProduto(Produto novoProduto) {
        boolean jaExiste = buscarPorId(novoProduto.getIdProduto()).isPresent();

        if (jaExiste) {
            throw new IllegalArgumentException("Erro: Já existe um produto cadastrado com o ID " + novoProduto.getIdProduto());
        }

        if (novoProduto.getIdProduto().length() != 4) {
            throw new IllegalArgumentException("Erro: O ID do produto deve conter exatamente 4 dígitos.");
        }

        produtoRepository.salvar(novoProduto);
    }

    public void adicionarAoEstoque(String idProduto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Produto produto = buscarPorId(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        produto.adicionarQuantidade(quantidade);
        produtoRepository.salvar(produto);
    }

    public void removerDoEstoque(String idProduto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Produto produto = buscarPorId(idProduto)
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

    public void atualizarProduto(Produto produtoAtualizado) {

        if (produtoAtualizado == null) {
            throw new IllegalArgumentException("Produto inválido.");
        }

        if (produtoAtualizado.getPrecoCusto() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }

        produtoRepository.atualizar(produtoAtualizado);
    }

    public Optional<Produto> buscarPorId(String idProduto) {
        return produtoRepository.buscarProdutoPorId(idProduto);
    }

    public void excluirProduto(String idProduto) {
        produtoRepository.excluirPorId(idProduto);
    }

    public List<Produto> buscarPorIdLoja(String idLoja) {
        return produtoRepository.buscarPorIdLoja(idLoja);
    }

}
