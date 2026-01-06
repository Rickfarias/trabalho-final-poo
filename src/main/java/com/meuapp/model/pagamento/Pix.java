package main.java.com.meuapp.model.pagamento;

import javax.swing.*;

public class Pix extends Pagamento {
    private String id;

    public Pix(double valor, String id) {
        super(valor);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void processarPgto() {
        JOptionPane.showMessageDialog(null, "Pagamento de R$ " + getValor() +
                " foi realizado com a identificacao do CPF: " + id);
        
    }
}
