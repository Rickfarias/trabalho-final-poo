package main.java.com.meuapp.model.loja;


import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.enums.Categoria;

public class Fornecedor {
    private String nomeFornecedor;
    private String cnpjFornecedor;
    private Categoria categoriaFornecedor;
    private Contato contatoFornecedor;
    private ContaBancaria contaEmpresarialFornecedor;

    public Fornecedor(
            String nomeFornecedor,
            String cnpjFornecedor,
            Categoria categoriaFornecedor,
            Contato contatoFornecedor,
            ContaBancaria contaEmpresarialFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
        this.cnpjFornecedor = cnpjFornecedor;
        this.categoriaFornecedor = categoriaFornecedor;
        this.contatoFornecedor = contatoFornecedor;
        this.contaEmpresarialFornecedor = contaEmpresarialFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public Categoria getCategoriaFornecedor() {
        return categoriaFornecedor;
    }

    public void setCategoriaFornecedor(Categoria categoriaFornecedor) {
        this.categoriaFornecedor = categoriaFornecedor;
    }

    public Contato getContatoFornecedor() {
        return contatoFornecedor;
    }

    public void setContatoFornecedor(Contato contatoFornecedor) {
        this.contatoFornecedor = contatoFornecedor;
    }

    public ContaBancaria getContaEmpresarialFornecedor() {
        return contaEmpresarialFornecedor;
    }

    public void setContaEmpresarialFornecedor(ContaBancaria contaEmpresarialFornecedor) {
        this.contaEmpresarialFornecedor = contaEmpresarialFornecedor;
    }
}
