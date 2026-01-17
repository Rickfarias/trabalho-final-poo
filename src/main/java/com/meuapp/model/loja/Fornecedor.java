package main.java.com.meuapp.model.loja;

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.enums.Categoria;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.varejo.Loja;

import java.math.BigDecimal;

public class Fornecedor extends Loja {

    public Fornecedor(String nomeLoja, String cnpj, Endereco endereco, Categoria categoria, Contato contato, StatusLoja statusLoja, BigDecimal caixaLoja, ContaBancaria contaEmpresarial) {
        super(nomeLoja, cnpj, endereco, categoria, contato, statusLoja, caixaLoja, contaEmpresarial);
    }


}
