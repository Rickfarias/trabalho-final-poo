package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de ContaBancaria aqui
* */

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.exception.ValorInvalidoException;
import main.java.com.meuapp.model.banco.ContaBancaria;

public class ContaService {
    public static void depositar(ContaBancaria conta, double valor) throws ContaInexistenteException {
        if (conta == null) {
            throw new ContaInexistenteException("A conta não pode ser nula");
        }

        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        conta.setSaldo(conta.getSaldo() + valor);
    }

    public static void sacar(ContaBancaria conta, double valor) throws ContaInexistenteException {
        if (conta == null) {
            throw new ContaInexistenteException("A conta não pode ser nula");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        if (valor > conta.getSaldo()) {
            throw new SaldoInsuficienteException(String.format("Saldo de R$%.2f é insuficiente para o saque de R$%.2f.", conta.getSaldo(), valor));
        }
        conta.setSaldo(conta.getSaldo() - valor);
    }

    public static void transferir(ContaBancaria origem, ContaBancaria destino, double valor) throws ContaInexistenteException{
        if (origem == null) {
            throw new ContaInexistenteException("Origem não existe");
        }
        if (destino == null) {
            throw new ContaInexistenteException("Destino não existe");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor inválido");
        }

        if (origem.getSaldo() < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);
    }
}
