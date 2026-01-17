package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.varejo.Loja;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;
import java.util.Optional;

public class LojaRepository {
    private static ArrayList<Loja> lojas = new ArrayList<>();

    public void salvarLoja(Loja loja) {
        lojas.add(loja);
    }

    public void listarLojas() {
        StringBuilder sb = new StringBuilder("Contas disponíveis:\n\n");
        lojas.forEach(l -> sb.append(l).append("\n"));
        InputUtil.info(sb.toString());
    }

    public Optional<Loja> buscarPorCnpj(String cnpj) {
        return lojas.stream()
                .filter(l -> l.getCnpj().equals(cnpj))
                .findFirst();
    }

    public Optional<Loja> buscarPorNome(String nomeLoja) {
        return lojas.stream()
                .filter(l -> l.getNomeLoja().equals(nomeLoja))
                .findFirst();
    }
}
