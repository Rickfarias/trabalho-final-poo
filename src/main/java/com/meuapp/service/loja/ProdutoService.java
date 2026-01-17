package main.java.com.meuapp.service.loja;

import main.java.com.meuapp.repository.ProdutoRepository;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void aumentarEstoque() {
            
    }

    public void adicionarAoEstoque() {

    }

    public void removerDoEstoque() {

    }

    public void listarEstoque() {
        produtoRepository.listarProdutos();
    }
}
