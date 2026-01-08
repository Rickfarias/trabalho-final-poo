package main.java.com.meuapp.controller;

import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.util.EnumUtil;
import main.java.com.meuapp.util.InputUtil;

import java.math.BigDecimal;

public class LojaController {
    public static void menuPrincipalLoja() {
        while (true) {

        }
    }

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

        Categoria categoria = EnumUtil.selecionarEnum(
                "Categorias",
                "Selecione a categoria:",
                Categoria.values()
        );

        if (categoria == null) {
            InputUtil.info("Nenhuma categoria selecionada");
            return;
        }
        InputUtil.info("Categoria selecionada: " + categoria);

        // Contato
        String telefone = InputUtil.inputString("Insira o seu numero");
        String email = InputUtil.inputString("insira seu email");
        Contato contato = new Contato(telefone, email);

        StatusLoja statusLoja = EnumUtil.selecionarEnum(
                "Status",
                "Selecione o status:",
                StatusLoja.values()
        );

        if (statusLoja == null) {
            InputUtil.info( "Nenhum status selecionado.");
            return;
        }
        InputUtil.info("Status atual: " + statusLoja);

        BigDecimal caixaLoja = BigDecimal.ZERO;

        try {
            LojaService.cadastrarLoja(
                    nomeLoja,
                    cnpj,
                    end,
                    categoria,
                    contato,
                    statusLoja,
                    caixaLoja
            );

            InputUtil.info("Loja cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            InputUtil.info("Erro no cadastro: " + e.getMessage());
        }

    }
}
