package main.java.com.meuapp.controller;

import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.util.InputUtil;


public class LojaController {
    public static void menuPrincipalLoja() {
        while (true) {

        }
    }
    Object[] selecaoCategorias = ;


    public static void menuCadastroLoja() {
        String nomeLoja = InputUtil.inputString("Insira o nome da sua loja");
        String cnpj = InputUtil.inputString("Insira o CNPJ da sua loja");

        // Endereco
        String cep = InputUtil.inputString("Insira o CEP");
        String estado = InputUtil.inputString("Insira o seu estado");
        String cidade = InputUtil.inputString("Insira a sua cidade");
        String bairro = InputUtil.inputString("Insira o seu bairro");
        String rua = InputUtil.inputString("Insira a sua rua");
        String numeroCasa = InputUtil.inputString("Insira o número da sua casa (ou s/n)");
        Endereco end = new Endereco(cep, estado, cidade, bairro, rua, numeroCasa);

        // TODO: fazer logica de selecao de categorias

        // Contato
        String telefone = InputUtil.inputString("Insira o seu numero");
        String email = InputUtil.inputString("insira seu email");
        Contato contato = new Contato(telefone, email);


    }
}
