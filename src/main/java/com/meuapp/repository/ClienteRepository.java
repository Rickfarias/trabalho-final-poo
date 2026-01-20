package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.cliente.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {
    private static List<Cliente> clientes = new ArrayList<>();

    public static void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public Optional<Cliente> buscarPorId(String id) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(id)) // Assumindo que CPF é o ID de login
                .findFirst();
    }

    public Optional<Cliente> buscarPorIdConta(String idConta) {
        return clientes.stream()
                .filter(c -> c.getConta().getId().equals(idConta))
                .findFirst();
    }
}
