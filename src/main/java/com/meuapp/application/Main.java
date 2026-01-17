package main.java.com.meuapp.application;

import main.java.com.meuapp.controller.ControllerPrincipal;

import static main.java.com.meuapp.controller.ControllerPrincipal.criarControllerPrincipal;

public class Main {
    public static void main(String[] args) {
        ControllerPrincipal controllerPrincipal = criarControllerPrincipal();
        controllerPrincipal.menuPrincipalUI();
    }
}