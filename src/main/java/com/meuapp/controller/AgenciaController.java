package main.java.com.meuapp.controller;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.exception.ValorInvalidoException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ValidacaoService;
import main.java.com.meuapp.util.InputUtil;

public class AgenciaController {
    public static void menuPrincipalUI() {
        String menu = """
               Bem-vindo(a) ao Banco UFC!
               1 - Criar conta
               2 - Acessar conta
               0 - Sair
               """;

        while (true) {
            int opcao = InputUtil.inputInt(menu);

            switch (opcao) {
                case 1 -> criarContaUI();
                case 2 -> acessarContaUI();
                case 0 -> {
                    InputUtil.info("Simulação encerrada. Obrigado por usar o sistema!");
                    return;
                }
                default -> {
                    InputUtil.info("Valor Inválido, tente novamente");
                    return;
                }
            }
        }
    }

    public static void criarContaUI() {
        String nome = InputUtil.inputString("Insira o seu nome completo");
        String senha = InputUtil.inputString("Insira a senha da conta");
        senha = ValidacaoService.confirmarSenha(senha);
        String cpf = InputUtil.inputString("Insira o seu cpf");
        String email = InputUtil.inputString("Insira o seu email");
        String endereco = InputUtil.inputString("Insira o seu endereço");

        ContaBancaria novaConta = AgenciaService.criarConta(nome, senha, cpf, email, endereco);

        InputUtil.info("Conta criada com sucesso! Seu id é: " + novaConta.getId());
    }

    public static void acessarContaUI(){
        String idLogin = InputUtil.inputString("Digite o ID da conta");
        String senhaLogin = InputUtil.inputString("Digite a senha");

        try {
            ContaBancaria conta = AgenciaService.acessarConta(idLogin, senhaLogin);
            menuSecundarioUI(conta);
        } catch (ContaInexistenteException e) {
            InputUtil.warn(e.getMessage(), "AVISO");
        } catch (SenhaIncorretaException e) {
            InputUtil.error(e.getMessage(), "ERRO");
        }
    }

    public static void saqueUI(ContaBancaria conta) {
        double saque = InputUtil.inputDouble("Insira o valor do saque");

        try {
            AgenciaService.saque(conta,saque);

            InputUtil.info(String.format("""
                    Saque de R$%.2f realizado com sucesso.
                    Novo Saldo: R$%.2f""",
                    saque, conta.getSaldo()));

        } catch (SaldoInsuficienteException | ValorInvalidoException e) {
            InputUtil.error(e.getMessage(), "ERRO");
        }
    }

    public static void depositarUI(ContaBancaria conta) {
        double deposito = InputUtil.inputDouble("Insira o valor do deposito");

        try {
            AgenciaService.deposito(conta, deposito);
            InputUtil.info(String.format("""
                Deposito de %.2f realizado com sucesso.
                Novo saldo: %.2f
                """, deposito, conta.getSaldo()));
        } catch (SaldoInsuficienteException e) {
            InputUtil.error(e.getMessage(), "ERRO");
        }

    }

    public static void transferirUI() {
        AgenciaService.listarIDs();

        String idOrigem = InputUtil.inputString("Insira o ID da conta de origem");
        String idDestino = InputUtil.inputString("Insira o ID da conta destino");

        try {
            double valor = InputUtil.inputDouble("Insira o valor da transferência");

            var resultado = AgenciaService.transferir(idOrigem, idDestino, valor);
            ContaBancaria origem = resultado.primeiro;
            ContaBancaria destino = resultado.segundo;

            InputUtil.info(String.format("""
                Transferência concluída!
                De: %s
                Para: %s
                Valor: R$%.2f
                """,
                    origem.getTitular().getNome(),
                    destino.getTitular().getNome(),
                    valor));

        } catch (ContaInexistenteException e) {
            InputUtil.warn(e.getMessage(), "AVISO");
        }
    }


    public static void menuSecundarioUI(ContaBancaria conta) {


        // TODO: Ver por que fica Titular: Nome e nao so um deles
        InputUtil.info(String.format("""
                            Bem-vindo(a) ao Banco UFC.
                            Titular: %s
                            ID: %s
                            Saldo Inicial: R$%.2f
                        """, conta.getTitular(), conta.getId(), conta.getSaldo()));

        String input = InputUtil.inputString(
                """
                        Digite
                        1 - Saque
                        2 - Deposito
                        3 - Transferir
                        4 - Listar contas
                        (Ou 'sair' para encerrar):
                        """);

        if (input == null || input.equalsIgnoreCase("sair")) {
            return;
        }

        try {
            switch (input) {
                case "1" -> saqueUI(conta);
                case "2" -> depositarUI(conta);
                case "3" -> transferirUI();
                case "4" -> ContaRepository.listarContas();
            }
        } catch (SaldoInsuficienteException e) {
            InputUtil.warn(
                    "ERRO DE NEGÓCIO: Saque Recusado.\nDetalhes: " + e.getMessage(),
                    "Falha no Saque (Aviso)");

        }catch (IllegalArgumentException e) {
            InputUtil.error(
                    "ERRO DE VALIDAÇÃO: " + e.getMessage(),
                    "Falha no Saque (Erro)");
        }
    }
}
