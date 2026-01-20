package main.java.com.meuapp.model.venda;

import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;

import java.time.LocalDateTime;

public class Venda {
    private Cliente cliente;
    private Loja loja;
    private Fornecedor fornecedor;
    private double valorTotal;
    private double valorFornecedor;
    private LocalDateTime data;

    public Venda(Cliente cliente, Loja loja, double valorTotal) {
        this.cliente = cliente;
        this.loja = loja;
        this.valorTotal = valorTotal;
        this.valorFornecedor = valorTotal * 0.3;
        this.data = LocalDateTime.now();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorFornecedor() {
        return valorFornecedor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Loja getLoja() {
        return loja;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
