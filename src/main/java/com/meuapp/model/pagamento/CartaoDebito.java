package main.java.com.meuapp.model.pagamento;

import javax.swing.*;

public class CartaoDebito extends Pagamento {
    private String numCartao;

    public CartaoDebito(double valor, String numCartao) {
        super(valor);
        this.numCartao = numCartao;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    @Override
    public void processarPgto() {
        JOptionPane.showMessageDialog(null, "Pagamento de R$" + getValor() + " foi realizado com o " +
                "Cartão de Débito - " + numCartao);
    }

    @Override
    public void exibirValor() {
        super.exibirValor();
    }
}
