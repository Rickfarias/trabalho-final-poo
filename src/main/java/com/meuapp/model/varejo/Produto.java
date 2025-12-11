package main.java.com.meuapp.model.varejo;

import javax.swing.*;
import java.util.Random;

/*
 * TODO: Separar as funcoes de regra de negocio, colocar em ProdutoService
 */

public class Produto {
    private long idProduto;
    private double preco;
    private int quantidade;
    private String descricao;

    public Produto() {
    }

    public Produto(String descricao, double preco, int quantidade) {
        this.idProduto = gerarId();
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public long getIdProduto() {
        return idProduto;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long gerarId() {
        Random random = new Random();
        return random.nextLong(1000000000);
    }

    // camada UI usa o InputUtil.error...
    public void retirarDoEstoque(int qtd) {
        if (qtd > quantidade) {
            JOptionPane.showMessageDialog(null, "ERRO: a quantidade a ser retirada é maior que a disponivel no estoque");
            return;
        }
        quantidade -= qtd;
    }

    @Override
    public String toString() {
        return String.format("""
                ------------------------------------------------------------------
                Nome: %s | Preco: %.2f | Quantidade: %d
                ------------------------------------------------------------------
                """, descricao, preco, quantidade);
    }

}
