package main.java.com.meuapp.model.banco;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;

/*
* TODO: AgenciaBancaria monolítico para diferentes blocos:
* camada de UI, interação: AgenciaController (e mudar o nome de AgenciaBancaria p/ AgenciaController ou AgenciaBancariaController
* tambem separar cada Menu em UIs diferentes para quebrar em funções menores, de mais facil manuntenção...
* camada de service, validações: AgenciaService
*/


public class AgenciaBancaria {
    public static ArrayList<ContaBancaria> contas = new ArrayList<>();

    public void entrar() {
        String menu = """
               Bem-vindo(a) ao Banco UFC!
               1 - Criar conta
               2 - Acessar conta
               0 - Sair
               """;

        String input;

        while (true) {
            ContaBancaria conta;
            int opcao = InputUtil.inputInt(menu);

            switch (opcao) {
                case 1 -> AgenciaService.criarConta();
                case 2 -> {
                    String idLogin = InputUtil.inputString("Digite o ID da conta");
                    String senhaLogin = InputUtil.inputString("Digite a senha");
                    conta = ContaRepository.buscarContaPorID(idLogin);

                    if (conta == null) {
                        InputUtil.info("ID não encontrado!");
                        break;
                    }

                    if (!conta.getTitular().getSenha().equals(senhaLogin)) {
                        InputUtil.info( "ID ou senha incorretos!");
                        break;
                    }

                    InputUtil.info(String.format("""
                            Bem-vindo(a) ao Banco UFC.
                            Titular: %s
                            ID: %s
                            Saldo Inicial: R$%.2f
                        """, conta.getTitular(), conta.getId(), conta.getSaldo()));


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

                                ContaBancaria origem = ContaRepository.buscarContaPorID(idOrigem);
                                ContaBancaria destino = ContaRepository.buscarContaPorID(idDestino);

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