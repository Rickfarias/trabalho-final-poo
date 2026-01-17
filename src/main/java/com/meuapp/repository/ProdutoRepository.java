package main.java.com.meuapp.repository;
import main.java.com.meuapp.model.varejo.Produto;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;

public class ProdutoRepository {
    private static ArrayList<Produto> produtos = new ArrayList<>();

    public static String buscarProdutoPorId(int idProduto){
        for (Produto p : produtos) {
            if (p.getIdProduto() == idProduto) {
                return p.getNomeProduto();
            }
        }
        return null;
    }

    public void listarProdutos() {
        StringBuilder sb = new StringBuilder("Produtos disponíveis:\n\n");
        produtos.forEach(p -> sb.append(p).append("\n"));
        InputUtil.info(sb.toString());
    }
}
