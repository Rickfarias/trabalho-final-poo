package main.java.com.meuapp.model.varejo;

import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Loja {

    private String nomeLoja;
    private String cnpj;
    private Endereco endereco;
    private Categoria categoria;
    private Contato contato;
    private StatusLoja statusLoja;
    private BigDecimal caixaLoja;

    public Loja() {
    }

    public Loja(String nomeLoja, String cnpj, Endereco endereco, Categoria categoria, Contato contato, StatusLoja statusLoja, BigDecimal caixaLoja) {
        this.nomeLoja = nomeLoja;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.categoria = categoria;
        this.contato = contato;
        this.statusLoja = statusLoja;
        this.caixaLoja = caixaLoja;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public StatusLoja getStatusLoja() {
        return statusLoja;
    }

    public void setStatusLoja(StatusLoja statusLoja) {
        this.statusLoja = statusLoja;
    }

    public BigDecimal getCaixaLoja() {
        return caixaLoja;
    }

    public void setCaixaLoja(BigDecimal caixaLoja) {
        this.caixaLoja = caixaLoja;
    }
}