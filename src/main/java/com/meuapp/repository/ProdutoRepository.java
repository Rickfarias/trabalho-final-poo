package main.java.com.meuapp.repository;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {
    private static List<Produto> produtos = new ArrayList<>();

    public Optional<Produto> buscarProdutoPorId(String idProduto) {
        for (Produto p : produtos) {
            if (p.getIdProduto().equals(idProduto)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }


    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    public void listarProdutos() {
        StringBuilder sb = new StringBuilder("Produtos disponíveis:\n\n");
        produtos.forEach(p -> sb.append(p).append("\n"));
        InputUtil.info(sb.toString());
    }
}
