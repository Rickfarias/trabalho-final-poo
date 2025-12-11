package main.java.com.meuapp.model.banco;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;
import java.util.Objects;

/*
* TODO: AgenciaBancaria monolítico para diferentes blocos:
* camada de UI, interação: AgenciaController (e mudar o nome de AgenciaBancaria p/ AgenciaController ou AgenciaBancariaController
* tambem separar cada Menu em UIs diferentes para quebrar em funções menores, de mais facil manuntenção...
* camada de service, validações: AgenciaService
*/


public class AgenciaBancaria {
    public static ArrayList<ContaBancaria> contas = new ArrayList<>();

    public static void criarConta() {
        String nome = InputUtil.inputString("Insira o seu nome completo");
        String senha = InputUtil.inputString("Insira a senha da conta");
        senha = confirmarSenha(senha);
        String cpf = InputUtil.inputString("Insira o seu cpf");
        String email = InputUtil.inputString("Insira o seu email");
        String endereco = InputUtil.inputString("Insira o seu endereço");

        Pessoa p = new Pessoa(nome, senha, cpf, email, endereco);

        ContaBancaria novaConta = new ContaBancaria(p);
        contas.add(novaConta);

        InputUtil.info("Conta criada com sucesso!\nSeu ID é: " + novaConta.getId());
    }

    public static ContaBancaria buscarConta(String id) {
        return contas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static String confirmarSenha(String senha) {
        while (true) {
            String confirmar = InputUtil.inputString("Confirme a senha:");

            if (senha.equals(confirmar)) {
                return senha;
            }

            InputUtil.info("Senhas não coincidem, tente novamente.");
            senha = InputUtil.inputString("Digite a senha novamente:");
        }
    }

    public static ContaBancaria encontrarConta(int numeroConta) {
        if (!contas.isEmpty()) {
            for (ContaBancaria c : contas) {
                if (Objects.equals(c.getId(), String.valueOf(numeroConta))) {
                    return c;
                }
            }
        }
        return null;
    }

    public static Pessoa encontrarPessoa(int numeroConta){
        for (ContaBancaria c : contas) {
            if (Objects.equals(c.getId(), String.valueOf(numeroConta))) {
                return c.getTitular();
                }
            }
        return null;
    }

    public void entrar() {
        String menu = """
               Bem-vindo(a) ao Banco UFC!
               1 - Criar conta
               2 - Acessar conta
               0 - Sair
               """;

        String input;

        // Loop principal para permitir múltiplas tentativas de saque
        while (true) {
            ContaBancaria conta;
            int opcao = InputUtil.inputInt(menu);

            switch (opcao) {
                case 1 -> {
                   criarConta();
                }
                case 2 -> {
                    String idLogin = InputUtil.inputString("Digite o ID da conta");
                    String senhaLogin = InputUtil.inputString("Digite a senha");
                    conta = buscarConta(idLogin);

                    if (conta == null) {
                        InputUtil.info("ID não encontrado!");
                        break;
                    }

                    if (!conta.getTitular().getSenha().equals(senhaLogin)) {
                        InputUtil.info( "ID ou senha incorretos!");
                        break;
                    }

                    InputUtil.info(
                            String.format("""
                                                    Bem-vindo(a) ao Banco UFC.
                                                    Titular: %s
                                                    ID: %s
                                                    Saldo Inicial: R$%.2f
                                                    """,
                                    conta.getTitular(), conta.getId(), conta.getSaldo()));


                    input = InputUtil.inputString(
                            """
                                    Digite
                                    1 - Saque
                                    2 - Deposito
                                    3 - Transferir
                                    4 - Listar contas
                                    (Ou 'sair' para encerrar):
                                    """);

                    if (input == null || input.equalsIgnoreCase("sair")) {
                        break; // Encerra o loop e o programa
                    }

                    try {
                        switch (input) {
                            case "1" -> {
                                double saque = InputUtil.inputDouble("Insira o valor do saque");

                                // Tenta realizar a operação (pode lançar SaldoInsuficienteException ou IllegalArgumentException)
                                conta.sacar(saque);

                                // Exibe o sucesso (só é executado se nenhuma exceção ocorrer)
                                InputUtil.info(String.format("""
                                                Saque de R$%.2f realizado com sucesso.
                                                Novo Saldo: R$%.2f""",
                                                saque, conta.getSaldo()));
                            }
                            case "2" -> {
                                double deposito = InputUtil.inputDouble("Insira o valor do deposito");

                                conta.depositar(deposito);

                                InputUtil.info(String.format("""
                                                Deposito de %.2f realizado com sucesso.
                                                Novo saldo: %.2f
                                                """, deposito, conta.getSaldo()));
                            }
                            case "3" -> {
                                contas.forEach(c -> InputUtil.info(
                                        String.format("IDs disponíveis: %s", c.toString())));

                                String idOrigem = InputUtil.inputString("Insira o ID da conta de origem");
                                String idDestino = InputUtil.inputString("Insira o ID da conta destino");

                                ContaBancaria origem = buscarConta(idOrigem);
                                ContaBancaria destino = buscarConta(idDestino);

                                if (origem == null) {
                                    throw new ContaInexistenteException("Conta de origem não encontrada!");
                                }
                                if (destino == null) {
                                    throw new ContaInexistenteException("Conta de destino não encontrada!");
                                }

                                double valor = InputUtil.inputDouble("Insira o valor que deseja transferir");

                                origem.transferir(destino, valor);

                                InputUtil.info(String.format("""
                                       Transferência concluída!
                                       De: %s
                                       Para: %s
                                       Valor: %.2f
                                       """, origem.getTitular(), destino.getTitular(), valor));
                            }
                            case "4" -> {
                                StringBuilder sb = new StringBuilder("Contas disponíveis:\n\n");
                                contas.forEach(c -> sb.append(c).append("\n"));
                                InputUtil.info(sb.toString());
                            }
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
                    catch (ContaInexistenteException e) {
                        InputUtil.error(
                                "ERRO DE VALIDAÇÃO: " + e.getMessage(),
                                "Conta Inexistente");
                    }
                }
                case 0 -> {
                    InputUtil.info("Simulação encerrada. Obrigado por usar o sistema!");
                    return;
                }
            }
        }
    }
}