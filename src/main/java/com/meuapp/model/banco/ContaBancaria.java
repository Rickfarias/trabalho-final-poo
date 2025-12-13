package main.java.com.meuapp.model.banco;

import main.java.com.meuapp.repository.ContaRepository;


/*
 *  TODO:
 *      Deixar todas as regras em ContaService, transformar ContaBancaria em POJO (Plain Old Java Object)
 *
 */


public class ContaBancaria {
    private String id;
    private Pessoa titular;
    private double saldo = 0.0;

    private static int contadorConta = 0;

    public ContaBancaria(Pessoa titular, double saldoInicial) {
        this.id = ContaRepository.gerarId();
        this.titular = titular;
        this.saldo = saldoInicial;

        contadorConta++;
    }

    public ContaBancaria(Pessoa titular) {
        this.id = ContaRepository.gerarId();
        this.titular = titular;

        contadorConta++;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(
                """
                Conta Bancaria:
                ID: %s,
                Titular: %s,
                Agência: Banco UFC,
                Saldo: %.2f
                """,
                id, titular.getNome(), saldo);
    }

}
