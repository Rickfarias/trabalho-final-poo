package main.java.com.meuapp.model.banco;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.exception.ValorInvalidoException;
import main.java.com.meuapp.repository.ContaRepository;

import java.util.Random;

/*
 *  TODO:
 *      Deixar todas as regras em ContaService, transformar conta bancaria em POJO (Plain Old Java Object)
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

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        if (valor > saldo) {
            throw new SaldoInsuficienteException(String.format("Saldo de R$%.2f é insuficiente para o saque de R$%.2f.", this.saldo, valor));
        }

        this.saldo -= valor;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        this.saldo += valor;
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
