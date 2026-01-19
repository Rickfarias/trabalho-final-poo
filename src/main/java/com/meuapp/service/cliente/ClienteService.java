package main.java.com.meuapp.service.cliente;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.repository.ClienteRepository;
import main.java.com.meuapp.service.banco.ContaService;

public class ClienteService {
    private ClienteRepository clienteRepository;
    private ContaService contaService;

    public ClienteService(ClienteRepository repository) {
        this.clienteRepository = repository;
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.salvar(cliente);
    }

    public void realizarDeposito(String cpf, double valor) throws ContaInexistenteException {
        Cliente cliente = clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontado"));

        contaService.depositar(cliente.getConta(), valor);
    }

    public void realizarSaque(String cpf, double valor) throws ContaInexistenteException {
        Cliente cliente = clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        contaService.sacar(cliente.getConta(), valor);
    }

    public double consultarSaldo(String cpf) {
        return clienteRepository.buscarPorCpf(cpf)
                .map(cliente -> cliente.getConta().getSaldo())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}
