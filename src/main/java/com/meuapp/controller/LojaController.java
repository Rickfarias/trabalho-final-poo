package main.java.com.meuapp.controller;

import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.util.EnumUtil;
import main.java.com.meuapp.util.InputUtil;

import java.math.BigDecimal;

public class LojaController {
    public static void menuPrincipalLoja() {
        String opcoes = """
                    1 - Cadastrar loja
                    2 - Listar lojas
                    0 - Voltar                
                """;

        while (true) {
            int escolha = InputUtil.inputInt(opcoes);

            switch (escolha) {
                case 1 -> menuCadastroLoja();
                case 2 -> LojaService.listarLojas();
                case 0 -> {
                    return;
                }
                default -> InputUtil.info("Opção inválida.");
            }
        }
    }


    public static void menuCadastroLoja() {

        String nomeLoja = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o nome da loja"),
                LojaService::validarNomeLoja
        );

        String cnpj = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o CNPJ"),
                ValidacaoLojaService::validarCNPJ
        );

        Endereco end = InputUtil.inputValidado(
                () -> {
                    String cep = ValidacaoLojaService.validarCEP(
                            InputUtil.inputString("Insira o CEP")
                    );
                    String estado = InputUtil.inputString("Insira o estado");
                    String cidade = InputUtil.inputString("Insira a cidade");
                    String bairro = InputUtil.inputString("Insira o bairro");
                    String rua = InputUtil.inputString("Insira a rua");
                    String numero = InputUtil.inputValidado(
                            () -> InputUtil.inputString("Insira o número (ou s/n)"),
                            ValidacaoLojaService::validarNumeroCasa
                    );

                    return new Endereco(cep, estado, cidade, bairro, rua, numero);
                },
                LojaService::validarEndereco
        );


        Categoria categoria = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Categorias",
                        "Selecione a categoria:",
                        Categoria.values()
                ),
                LojaService::validarCategoria
        );

        Contato contato = InputUtil.inputValidado(
                () -> {
                    String telefone = InputUtil.inputString("Insira o telefone");
                    String email = InputUtil.inputString("Insira o email");

                    telefone = ValidacaoLojaService.validarTelefone(telefone);
                    email = ValidacaoLojaService.validarEmail(email);

                    return new Contato(telefone, email);
                },
                LojaService::validarContato
        );


        StatusLoja statusLoja = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Status",
                        "Selecione o status:",
                        StatusLoja.values()
                ),
                LojaService::validarStatusLoja
        );

        BigDecimal caixaLoja = BigDecimal.ZERO;


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
    }
}
