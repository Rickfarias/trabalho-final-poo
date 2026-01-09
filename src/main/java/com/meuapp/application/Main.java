package main.java.com.meuapp.application;

import main.java.com.meuapp.controller.AgenciaController;
import main.java.com.meuapp.controller.ControllerPrincipal;
import main.java.com.meuapp.controller.LojaController;
import main.java.com.meuapp.repository.ClienteRepository;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.banco.ValidacaoService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;

import static main.java.com.meuapp.controller.ControllerPrincipal.criarControllerPrincipal;

public class Main {
    public static void main(String[] args) {
        ControllerPrincipal controllerPrincipal = criarControllerPrincipal();
        controllerPrincipal.menuPrincipalUI();

        /*
        Loja loja = new Loja();
        loja.cadastroLoja();

        SistemaPag sp = new SistemaPag(loja);
        sp.sisPagMenu();
        sp.gerarRelatorio();
        */
    }
}