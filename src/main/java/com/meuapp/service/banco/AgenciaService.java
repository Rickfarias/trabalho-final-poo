package main.java.com.meuapp.service.banco;

/*
* TODO: Adicionar regras de negocio de AgenciaBancaria aqui
* */

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.util.Par;

public class AgenciaService {
    private ContaService contaService;
    private ContaRepository contaRepository;

    public AgenciaService(
            ContaService contaService,
            ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }

    public ContaBancaria criarConta(
            String nome,
            String senha,
            String cpf,
            String email,
            String endereco) {
        Pessoa p = new Pessoa(nome, senha, cpf, email, endereco);

        ContaBancaria conta = new ContaBancaria(p);
        ContaRepository.salvar(conta);

        return conta;
    }

    public ContaBancaria criarContaLoja(
            String nomeLoja,
            String senha,
            String cnpj,
            String emailLoja,
            Endereco endereco) {
        Pessoa titularJuridico = new Pessoa(nomeLoja, senha, cnpj, emailLoja, endereco.toString());
        ContaBancaria conta = new ContaBancaria(titularJuridico);
        ContaRepository.salvar(conta);

        return conta;
    }

    public ContaBancaria acessarConta(String id, String senha)
            throws ContaInexistenteException, SenhaIncorretaException {
        ContaBancaria conta = ContaRepository.buscarContaPorID(id);

        if (conta == null) {
            throw new ContaInexistenteException("A conta não foi encontrada!");
        }

        if (!conta.getTitular().getSenha().equals(senha)) {
            throw new SenhaIncorretaException("Senha incorreta");
        }

        return conta;
    }

    public void saque(ContaBancaria conta, double valor)
            throws ContaInexistenteException {
        contaService.sacar(conta, valor);
    }

    public void deposito(ContaBancaria conta, double valor)
            throws ContaInexistenteException {
        contaService.depositar(conta, valor);
    }

    public Par<ContaBancaria, ContaBancaria> transferir
            (String idOrigem, String idDestino, double valor)
            throws ContaInexistenteException {
        ContaBancaria origem = ContaRepository.buscarContaPorID(idOrigem);
        ContaBancaria destino = ContaRepository.buscarContaPorID(idDestino);

        if (origem == null) {
            throw new ContaInexistenteException("Conta de origem não encontrada!");
        }
        if (destino == null) {
            throw new ContaInexistenteException("Conta de destino não encontrada!");
        }

        contaService.transferir(origem, destino, valor);
        return new Par<>(origem, destino);
    }

    public void listarIDs() {
        ContaRepository.listarIDs();
    }
}

