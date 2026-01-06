package main.java.com.meuapp.repository;

import main.java.com.meuapp.model.varejo.Loja;

import java.util.ArrayList;

public class LojaRepository {
    private static ArrayList<Loja> lojas = new ArrayList<>();

    public static void salvarLoja(Loja loja) {
        lojas.add(loja);
    }
}
