package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.varejo.Loja;
import main.java.com.meuapp.util.InputUtil;

import java.util.ArrayList;

public class LojaRepository {
    private static ArrayList<Loja> lojas = new ArrayList<>();

    public static void salvarLoja(Loja loja) {
        lojas.add(loja);
    }

    public static void listarLojas() {
        StringBuilder sb = new StringBuilder("Contas disponíveis:\n\n");
        lojas.forEach(l -> sb.append(l).append("\n"));
        InputUtil.info(sb.toString());
    }
}
