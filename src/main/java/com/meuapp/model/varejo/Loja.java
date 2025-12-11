package main.java.com.meuapp.model.varejo;

import javax.swing.*;
import java.util.ArrayList;

/*
* TODO: verificar se vale a pena utilizar, melhorando como está ou é melhor começar outra classe do 0
* */

public class Loja {

    ArrayList<Produto> produtos = new ArrayList<>();
    private String nomeLoja;
    private String localizacao;


    public Loja() {
    }

    public Loja(ArrayList<Produto> produtos, String nomeLoja, String localizacao) {
        this.produtos = produtos;
        this.nomeLoja = nomeLoja;
        this.localizacao = localizacao;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void addProdutos(Produto p) {
        produtos.add(p);
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void cadastroLoja() {
        JOptionPane.showMessageDialog(null, """
                    ------------------------- LOJA -------------------------
                    Aqui voce podera cadastrar a sua loja
                    -----------------------------------------------------------
                    """);

        nomeLoja = JOptionPane.showInputDialog("Insira o nome da loja");
        localizacao = JOptionPane.showInputDialog("Insira o endereco da loja");

        JOptionPane.showMessageDialog(null, "Agora seguiremos para o cadastro dos 5 produtos de sua loja");

        for (int i  = 0; i < 5; i++) {
            String nome = JOptionPane.showInputDialog("Insira o nome do produto " + (i + 1));
            double preco = Double.parseDouble(JOptionPane.showInputDialog("Insira o valor unitario do produto " + (i + 1)));
            int qtd = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade do produto " + (i + 1)));

            Produto p = new Produto(nome, preco, qtd);

            addProdutos(p);
        }

        StringBuilder sb = new StringBuilder("Aqui estao os produtos cadastrados\n");

        for (Produto p : getProdutos()) {
            sb.append(p.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}


