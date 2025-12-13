package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de AgenciaBancaria aqui
* */

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.util.Par;

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
    
    public static void saque(ContaBancaria conta, double valor) throws ContaInexistenteException {
        ContaService.sacar(conta, valor);
    }

    public static void deposito(ContaBancaria conta, double valor) throws ContaInexistenteException {
        ContaService.depositar(conta, valor);
    }

    public static Par<ContaBancaria, ContaBancaria> transferir(String idOrigem, String idDestino, double valor) throws ContaInexistenteException {
        ContaBancaria origem = ContaRepository.buscarContaPorID(idOrigem);
        ContaBancaria destino = ContaRepository.buscarContaPorID(idDestino);

        if (origem == null) {
            throw new ContaInexistenteException("Conta de origem não encontrada!");
        }
        if (destino == null) {
            throw new ContaInexistenteException("Conta de destino não encontrada!");
        }

        ContaService.transferir(origem, destino, valor);
        return new Par<>(origem, destino);
    }

    public static void listarIDs() {
        ContaRepository.listarIDs();
    }
}

