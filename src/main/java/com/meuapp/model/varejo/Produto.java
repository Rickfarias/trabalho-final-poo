package main.java.com.meuapp.model.varejo;

/*
 * TODO: Separar as funcoes de regra de negocio, colocar em ProdutoService
 */

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private double preco;
    private int quantidade;

    public Produto(int idProduto, String nomeProduto, double preco, int quantidade) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
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
