package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de AgenciaBancaria aqui
* */

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.repository.ContaRepository;

public class AgenciaService {

    public static ContaBancaria criarConta(String nome, String senha, String cpf, String email, String endereco) {
        Pessoa p = new Pessoa(nome, senha, cpf, email, endereco);

        ContaBancaria conta = new ContaBancaria(p);
        ContaRepository.salvar(conta);

        return conta;
    }

    public static ContaBancaria acessarConta(String id, String senha) throws ContaInexistenteException, SenhaIncorretaException {
        ContaBancaria conta = ContaRepository.buscarContaPorID(id);

        if (conta == null) {
            throw new ContaInexistenteException("A conta não foi encontrada!");
        }

        if (!conta.getTitular().getSenha().equals(senha)) {
            throw new SenhaIncorretaException("Senha incorreta");
        }

        return conta;
    }
}

