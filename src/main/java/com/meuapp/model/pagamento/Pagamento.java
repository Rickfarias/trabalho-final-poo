package main.java.com.meuapp.model.pagamento;

import javax.swing.*;

public abstract class Pagamento {
    private double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public abstract void processarPgto();

    public void exibirValor() {
        JOptionPane.showMessageDialog(null, "Valor do pagamento: R$ " + this.valor);
    }
}
