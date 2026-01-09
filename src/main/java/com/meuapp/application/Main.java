package main.java.com.meuapp.application;

import main.java.com.meuapp.controller.AgenciaController;
import main.java.com.meuapp.controller.LojaController;

public class Main {
    public static void main(String[] args) {
        LojaController lojaController = new LojaController();
        
        /*
        Loja loja = new Loja();
        loja.cadastroLoja();

        SistemaPag sp = new SistemaPag(loja);
        sp.sisPagMenu();
        sp.gerarRelatorio();
        */
        
        /*AgenciaController.menuPrincipalUI();*/

        lojaController.menuCadastroLoja();
    }
}