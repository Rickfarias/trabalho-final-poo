package main.java.com.meuapp.controller;

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.StatusLoja;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.util.EnumUtil;
import main.java.com.meuapp.util.InputUtil;

import java.math.BigDecimal;

public class LojaController {
    AgenciaService agenciaService;
    ValidacaoLojaService validacaoLojaService;
    LojaService lojaService;

    public void menuPrincipalLoja() {
        String opcoes = """
                    1 - Cadastrar loja
                    2 - Listar lojas
                    0 - Voltar
                    """;

        while (true) {
            int escolha = InputUtil.inputInt(opcoes);

            switch (escolha) {
                case 1 -> menuCadastroLoja();
                case 2 -> lojaService.listarLojas();
                case 0 -> {
                    return;
                }
                default -> InputUtil.info("Opção inválida.");
            }
        }
    }


    public void menuCadastroLoja() {

        String nomeLoja = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o nome da loja"),
                lojaService::validarNomeLoja
        );

        String cnpj = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o CNPJ"),
                validacaoLojaService::validarCNPJ
        );

        Endereco end = InputUtil.inputValidado(
                () -> {
                    String cep = validacaoLojaService.validarCEP(
                            InputUtil.inputString("Insira o CEP")
                    );
                    String estado = InputUtil.inputString("Insira o estado");
                    String cidade = InputUtil.inputString("Insira a cidade");
                    String bairro = InputUtil.inputString("Insira o bairro");
                    String rua = InputUtil.inputString("Insira a rua");
                    String numero = InputUtil.inputValidado(
                            () -> InputUtil.inputString("Insira o número (ou s/n)"),
                            validacaoLojaService::validarNumeroCasa
                    );

                    return new Endereco(cep, estado, cidade, bairro, rua, numero);
                },
                lojaService::validarEndereco
        );


        Categoria categoria = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Categorias",
                        "Selecione a categoria:",
                        Categoria.values()
                ),
                lojaService::validarCategoria
        );

        Contato contato = InputUtil.inputValidado(
                () -> {
                    String telefone = InputUtil.inputString("Insira o telefone");
                    String email = InputUtil.inputString("Insira o email");

                    telefone = validacaoLojaService.validarTelefone(telefone);
                    email = validacaoLojaService.validarEmail(email);

                    return new Contato(telefone, email);
                },
                lojaService::validarContato
        );


        StatusLoja statusLoja = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Status",
                        "Selecione o status:",
                        StatusLoja.values()
                ),
                lojaService::validarStatusLoja
        );

        String senhaContaLoja = InputUtil.inputString(
                "Crie uma senha para sua loja");

        BigDecimal caixaLoja = BigDecimal.ZERO;
        ContaBancaria contaEmpresarial = agenciaService.criarContaLoja(
                nomeLoja,
                senhaContaLoja,
                cnpj,
                contato.getEmail(),
                end
        );


        lojaService.cadastrarLoja(
                nomeLoja,
                cnpj,
                end,
                categoria,
                contato,
                statusLoja,
                caixaLoja,
                contaEmpresarial
        );

        InputUtil.info("Loja cadastrada com sucesso! ID da Conta Bancária: " + contaEmpresarial.getId());
    }

    public void menuRealizarComprar() {
        InputUtil.info("--- NOVA COMPRA ---");
        String cnpjLoja = InputUtil.inputString("Digite o cnpj da loja vendedora");
            

    }
}
