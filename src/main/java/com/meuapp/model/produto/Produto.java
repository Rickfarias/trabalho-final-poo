package main.java.com.meuapp.model.produto;

/*
 * TODO: Separar as funcoes de regra de negocio, colocar em ProdutoService
 */

public class Produto {
    private String idProduto;
    private String nomeProduto;
    private double preco;
    private int quantidade;

    public Produto(String idProduto, String nomeProduto, double preco, int quantidade) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public void removerQuantidade(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        if (this.quantidade < qtd) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        this.quantidade -= qtd;
    }

    @Override
    public String toString() {
        return String.format("""
               Produto:
               id: %d,
               nome: %s,
               preço: %.2f,
               quantidade: %d
               """, idProduto, nomeProduto, preco, quantidade);
    }
}
