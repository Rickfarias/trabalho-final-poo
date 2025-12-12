package main.java.com.meuapp.application;

import main.java.com.meuapp.controller.AgenciaController;

public class Main {
    public static void main(String[] args) {
        /*
        Loja loja = new Loja();
        loja.cadastroLoja();

        SistemaPag sp = new SistemaPag(loja);
        sp.sisPagMenu();
        sp.gerarRelatorio();
        */

        AgenciaController controlador = new AgenciaController();
        controlador.menuPrincipalUI();
    }
}