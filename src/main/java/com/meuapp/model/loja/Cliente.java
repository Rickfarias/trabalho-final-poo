package main.java.com.meuapp.model.loja;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;

public class Cliente extends Pessoa {
    private ContaBancaria conta;

    public Cliente(
            String nome,
            String senha,
            String cpf,
            String email,
            String endereco,
            ContaBancaria conta)
            throws ContaInexistenteException {
        super(nome, senha, cpf, email, endereco);
        if (conta == null) {
            throw new ContaInexistenteException("Um cliente deve possuir uma conta bancária vinculada");
        }
        this.conta = conta;
    }

    public ContaBancaria getConta() {
        return conta;
    }

}
