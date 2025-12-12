package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de ContaBancaria aqui
* */

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.exception.ValorInvalidoException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.repository.ContaRepository;

public class ContaService {
    public static void depositar(ContaBancaria conta, double valor) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta não pode ser nula");
        }

        conta.depositar(valor);
    }

    public static void sacar(ContaBancaria conta, double valor) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta não pode ser nula");
        }
        conta.sacar(valor);
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
