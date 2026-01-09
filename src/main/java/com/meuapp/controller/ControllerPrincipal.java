package main.java.com.meuapp.controller;

import main.java.com.meuapp.repository.ClienteRepository;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.banco.ValidacaoService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.util.InputUtil;

public class ControllerPrincipal {
    AgenciaController agenciaController;
    LojaController lojaController;

    public ControllerPrincipal(AgenciaController agenciaController, LojaController lojaController) {
        this.agenciaController = agenciaController;
        this.lojaController = lojaController;
    }

    public void menuPrincipalUI() {
        int opcao;
        do {
            opcao = InputUtil.inputInt("""
                ------ BEM VINDO ------
                O que deseja fazer?
                
                1 - Acessar menu Banco UFC
                2 - Acessar menu das lojas
                0 - Sair
                """);

            switch (opcao){
                case 1 -> agenciaController.menuPrincipalUI();
                case 2 -> lojaController.menuPrincipalLoja();
                case 0 -> {
                    break;
                }
                default -> InputUtil.info("Opção inválida selecionada");
            }
        } while (opcao != 0);
    }

    public static ControllerPrincipal criarControllerPrincipal() {
        // Repositorios
        ContaRepository repository = new ContaRepository();
        ClienteRepository clienteRepository = new ClienteRepository();
        LojaRepository lojaRepository =  new LojaRepository();

        // Serviços
        ContaService serviceBancario = new ContaService();
        AgenciaService agenciaService = new AgenciaService(serviceBancario, repository);
        ValidacaoService validacaoService = new ValidacaoService();
        ValidacaoLojaService validacaoLojaService = new ValidacaoLojaService();
        LojaService lojaService = new LojaService(serviceBancario, clienteRepository, lojaRepository);

        // Controladores
        LojaController lojaController = new LojaController(agenciaService, validacaoLojaService, lojaService);
        AgenciaController agenciaController = new AgenciaController(agenciaService, validacaoService);
        ControllerPrincipal controllerPrincipal = new ControllerPrincipal(agenciaController, lojaController);

        return new ControllerPrincipal(agenciaController, lojaController);
    }

}
