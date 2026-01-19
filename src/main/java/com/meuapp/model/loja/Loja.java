package main.java.com.meuapp.model.loja;

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.enums.Categoria;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.model.venda.Venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loja {

    private String nomeLoja;
    private String cnpj;
    private Endereco endereco;
    private Categoria categoria;
    private Contato contato;
    private StatusLoja statusLoja;
    private BigDecimal caixaLoja;
    private ContaBancaria contaEmpresarial;

    private List<Fornecedor> fornecedores = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();
    private Map<String, Produto> estoque = new HashMap<>();

    public Loja(
            String nomeLoja,
            String cnpj,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja,
            ContaBancaria contaEmpresarial) {
        this.nomeLoja = nomeLoja;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.categoria = categoria;
        this.contato = contato;
        this.statusLoja = statusLoja;
        this.caixaLoja = caixaLoja;
        this.contaEmpresarial = contaEmpresarial;
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

    public ContaBancaria getContaEmpresarial() {
        return contaEmpresarial;
    }

    public void setContaEmpresarial(ContaBancaria contaEmpresarial) {
        this.contaEmpresarial = contaEmpresarial;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public void adicionarFornecedor(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public Fornecedor buscarFornecedorPorNome(String nome) {
        return fornecedores.stream()
                .filter(f -> f.getNomeFornecedor().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public void registrarVenda(Venda venda) {
        vendas.add(venda);
    }

    public List<Venda> getHistoricoVendas() {
        return vendas;
    }

    public Produto buscarProduto(String id) {
        return estoque.get(id);
    }

    public void adicionarProduto(Produto produto) {
        estoque.put(produto.getIdProduto(), produto);
    }
}