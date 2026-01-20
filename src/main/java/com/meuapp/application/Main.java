package main.java.com.meuapp.application;

import main.java.com.meuapp.controller.ControllerPrincipal;
import main.java.com.meuapp.exception.ContaInexistenteException;

import static main.java.com.meuapp.controller.ControllerPrincipal.criarControllerPrincipal;

public class Main {
    public static void main(String[] args) throws ContaInexistenteException {
        ControllerPrincipal controllerPrincipal = criarControllerPrincipal();
        controllerPrincipal.menuPrincipalUI();
    }

    /*
    * TODO: Resolver as exceções quebrando o codigo sempre
    * TODO: Fazer menu do cliente
    * TODO: Revisar a logica de login na Opcao 4 de algum menu
    * TODO: Ajeitar o problema entre caixaLoja e saldo da conta da Loja
    */
}