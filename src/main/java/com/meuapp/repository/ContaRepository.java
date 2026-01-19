package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ContaRepository {
    private static List<ContaBancaria> contas = new ArrayList<>();

    public static void salvar(ContaBancaria conta) {
        contas.add(conta);
    }

    public static ContaBancaria buscarContaPorID(String id) {
        return contas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Pessoa buscarPessoaPorID(int numeroConta){
        for (ContaBancaria c : contas) {
            if (Objects.equals(c.getId(), String.valueOf(numeroConta))) {
                return c.getTitular();
            }
        }
        return null;
    }

    public static void listarContas() {
        StringBuilder sb = new StringBuilder("Contas disponíveis:\n\n");
        contas.forEach(c -> sb.append(c).append("\n"));
        InputUtil.info(sb.toString());
    }

    public static String gerarId() {
        return String.valueOf(Math.abs(new Random().nextInt(10_000)));
    }

    public static void listarIDs() {
        contas.forEach(c -> InputUtil.info(
                String.format("IDs disponíveis: %s", c.toString())));
    }

}
