package main.java.com.meuapp.repository;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {
    private static List<Produto> produtos = new ArrayList<>();

    public ProdutoRepository() {
        inicializarProdutosPadrao();
    }

    private void inicializarProdutosPadrao() {
        // Produtos de Vestuário
        salvar(new Produto("12.345.678/0001-10", "1001", "Camiseta Básica", 29.90, 100));
        salvar(new Produto("12.345.678/0001-10", "1002", "Calça Jeans", 89.90, 50));
        salvar(new Produto("12.345.678/0001-10", "1003", "Vestido Social", 149.90, 30));
        salvar(new Produto("12.345.678/0001-10", "1004", "Jaqueta de Couro", 299.90, 20));
        salvar(new Produto("12.345.678/0001-10", "1005", "Bermuda Cargo", 59.90, 60));

        // Produtos de Eletrônicos
        salvar(new Produto("23.456.789/0001-20", "2001", "Smartphone Galaxy", 1499.90, 40));
        salvar(new Produto("23.456.789/0001-20", "2002", "Notebook Dell", 2999.90, 25));
        salvar(new Produto("23.456.789/0001-20", "2003", "Smart TV 50''", 1899.90, 30));
        salvar(new Produto("23.456.789/0001-20", "2004", "Fone Bluetooth", 199.90, 80));
        salvar(new Produto("23.456.789/0001-20", "2005", "Tablet Samsung", 899.90, 35));

        // Produtos de Alimentos
        salvar(new Produto("34.567.890/0001-30", "3001", "Arroz 5kg", 25.90, 200));
        salvar(new Produto("34.567.890/0001-30", "3002", "Feijão 1kg", 8.90, 150));
        salvar(new Produto("34.567.890/0001-30", "3003", "Óleo de Soja 900ml", 6.50, 180));
        salvar(new Produto("34.567.890/0001-30", "3004", "Açúcar 1kg", 4.90, 160));
        salvar(new Produto("34.567.890/0001-30", "3005", "Café 500g", 15.90, 120));

        // Produtos de Eletrodomésticos
        salvar(new Produto("45.678.901/0001-40", "4001", "Geladeira Frost Free", 2499.90, 15));
        salvar(new Produto("45.678.901/0001-40", "4002", "Fogão 4 Bocas", 899.90, 20));
        salvar(new Produto("45.678.901/0001-40", "4003", "Micro-ondas 30L", 499.90, 25));
        salvar(new Produto("45.678.901/0001-40", "4004", "Máquina de Lavar", 1699.90, 18));
        salvar(new Produto("45.678.901/0001-40", "4005", "Aspirador de Pó", 299.90, 30));

        // Produtos de Informática
        salvar(new Produto("56.789.012/0001-50", "5001", "Mouse Gamer", 149.90, 70));
        salvar(new Produto("56.789.012/0001-50", "5002", "Teclado Mecânico", 349.90, 50));
        salvar(new Produto("56.789.012/0001-50", "5003", "Monitor 24'' LED", 699.90, 40));
        salvar(new Produto("56.789.012/0001-50", "5004", "Webcam Full HD", 249.90, 60));
        salvar(new Produto("56.789.012/0001-50", "5005", "HD Externo 1TB", 299.90, 55));
    }

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

    public void atualizar(Produto produtoAtualizado) {

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);

            if (p.getIdProduto().equals(produtoAtualizado.getIdProduto())) {
                produtos.set(i, produtoAtualizado);
                return;
            }
        }

        throw new IllegalArgumentException("Produto não encontrado para atualização.");
    }

    public void excluirPorId(String idProduto) {
        boolean removido = produtos.removeIf(
                p -> p.getIdProduto().equals(idProduto)
        );

        if (!removido) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }

    public List<Produto> buscarPorIdLoja(String idLoja) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getIdLoja() != null && p.getIdLoja().equals(idLoja)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

}
