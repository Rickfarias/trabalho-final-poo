package main.java.com.meuapp.service.pagamento;

import main.java.com.meuapp.model.pagamento.CartaoCredito;
import main.java.com.meuapp.model.pagamento.CartaoDebito;
import main.java.com.meuapp.model.pagamento.Pix;
import main.java.com.meuapp.model.varejo.Loja;
import main.java.com.meuapp.model.varejo.Produto;

import javax.swing.*;

/*
 * TODO: verificar se vale a pena utilizar, melhorando como está ou é melhor começar outra classe do 0
 */

/*
 * TODO: Separar SistemaPag monolítico para diferentes blocos:
 * camada de UI, interação: SistemaPagController
 * tambem separar cada Menu em UIs diferentes para quebrar em funções menores, de mais facil manuntenção...
 * camada de service, validações: SistemaPagService
 */


public class SistemaPag {
    private double saldo;
    private Loja loja;
    private double totalVendido = 0.0;

    public SistemaPag() {
    }

    public SistemaPag( Loja loja) {
        this.loja = loja;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void registrarVenda(double valor) {
        totalVendido += valor;
    }

    public void diminuirSaldo(double valorTotal) {
        if (valorTotal > saldo) {
            JOptionPane.showMessageDialog(null, "ERRO: Saldo insuficiente.");
            return;
        }
        saldo -= valorTotal;
    }

    private Produto escolherProduto() {

        while (true) {

            StringBuilder sb = new StringBuilder("Escolha o produto:\n\n");
            int i = 1;

            for (Produto p : loja.getProdutos()) {
                sb.append(i++)
                        .append(" - ")
                        .append(p.getDescricao())
                        .append(" | R$ ")
                        .append(p.getPreco())
                        .append(" | Estoque: ")
                        .append(p.getQuantidade())
                        .append("\n");
            }

            sb.append("\n0 - Voltar / Cancelar compra\n");

            String entrada = JOptionPane.showInputDialog(sb.toString());

            if (entrada == null) {
                return null;
            }

            int escolha;
            try {
                escolha = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida!");
                continue;
            }

            if (escolha == 0) {
                return null;
            }

            if (escolha >= 1 && escolha <= loja.getProdutos().size()) {
                return loja.getProdutos().get(escolha - 1);
            }

            JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }


    public void sisPagMenu() {

        saldo = Double.parseDouble((JOptionPane.showInputDialog("""
                -------------- SISTEMA DE PAGAMENTO --------------
                              Insira o saldo da conta
                --------------------------------------------------
                """)));

        int menu = -1;

        do {
            Produto p = escolherProduto();

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Encerrando o programa...");
                break;
            }

            int qtd = Integer.parseInt(JOptionPane.showInputDialog(
                    "Quantidade que deseja comprar do produto " + p.getDescricao()
            ));

            double total = p.getPreco() * qtd;

            menu = Integer.parseInt(JOptionPane.showInputDialog("""
                -------------- SISTEMA DE PAGAMENTO --------------
                           Qual a forma de pagamento?

                            1 - PIX
                            2 - Cartão de Débito
                            3 - Cartao de Crédito
                            0 - Sair

                --------------------------------------------------
                """));

            if (qtd > p.getQuantidade()) {
                JOptionPane.showMessageDialog(null, "ERRO: Estoque insuficiente.");
                continue;
            }

            if (total > saldo) {
                JOptionPane.showMessageDialog(null, "ERRO: Saldo insuficiente.");
                continue;
            }
            // Modifiquei o switch case de : para -> e {} pois como sao muitas linhas de codigo, as chaves ajudam ao codigo ficar legivel
            switch (menu) {
                case 1 -> {
                    String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente");
                    Pix px = new Pix(total, cpf);
                    px.processarPgto();
                }
                case 2 -> {
                    String num = JOptionPane.showInputDialog("Número do cartão de débito");
                    CartaoDebito cd = new CartaoDebito(total, num);
                    cd.processarPgto();
                }
                case 3 -> {
                    String numc = JOptionPane.showInputDialog("Número do cartão de crédito");
                    CartaoCredito cc = new CartaoCredito(total, numc);
                    cc.processarPgto();
                }
                case 0 -> {
                    JOptionPane.showMessageDialog(null, "Encerrando o sistema...");
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Valor inválido");
                    continue;
                }
            }
            p.retirarDoEstoque(qtd);
            diminuirSaldo(total);
            registrarVenda(total);

            JOptionPane.showMessageDialog(null,
                    "Compra realizada!\n" +
                            "Novo saldo: R$ " + saldo +
                            "\nNovo estoque do produto: " + p.getQuantidade()
            );
        } while (menu != 0);
    }

    public void gerarRelatorio() {
        StringBuilder sb = new StringBuilder();

        sb.append("========== RELATÓRIO DA LOJA ==========\n\n");
        sb.append("Loja: ").append(loja.getNomeLoja()).append("\n");
        sb.append("Endereço: ").append(loja.getLocalizacao()).append("\n");

        sb.append("\n----------------------------------------\n");
        sb.append("Total vendido: R$ ").append(String.format("%.2f", totalVendido)).append("\n");
        sb.append("----------------------------------------\n\n");

        sb.append("Produtos ainda em estoque:\n\n");

        boolean temEstoque = false;

        for (Produto p : loja.getProdutos()) {
            if (p.getQuantidade() > 0) {
                temEstoque = true;
                sb.append(p.getDescricao()).append(" | ")
                        .append("Preço: R$ ").append(String.format("%.2f", p.getPreco())).append(" | ")
                        .append("Estoque: ").append(p.getQuantidade()).append("\n");
            }
        }

        if (!temEstoque) {
            sb.append("Nenhum produto disponível no momento.\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}