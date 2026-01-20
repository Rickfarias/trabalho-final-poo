package main.java.com.meuapp.service.venda;

import main.java.com.meuapp.model.produto.Produto;

public class ItemCompra {
    private Produto produto;
    private int quantidade;

    public ItemCompra(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
