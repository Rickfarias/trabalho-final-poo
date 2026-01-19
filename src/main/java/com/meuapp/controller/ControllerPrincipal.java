package main.java.com.meuapp.controller;

import main.java.com.meuapp.repository.ClienteRepository;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.banco.ValidacaoService;
import main.java.com.meuapp.service.loja.FornecedorService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.service.venda.VendaService;
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
                
                1 - Módulo Agência Bancária
                2 - Módulo Loja de Varejo
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
        // Model


        // Repositorios
        ContaRepository repository = new ContaRepository();
        ClienteRepository clienteRepository = new ClienteRepository();
        LojaRepository lojaRepository =  new LojaRepository();

        // Serviços
        ContaService contaService = new ContaService();
        AgenciaService agenciaService = new AgenciaService(contaService, repository);
        ValidacaoService validacaoService = new ValidacaoService();
        ValidacaoLojaService validacaoLojaService = new ValidacaoLojaService();
        LojaService lojaService = new LojaService(contaService, clienteRepository, lojaRepository);
        VendaService vendaService = new VendaService(lojaRepository, contaService);
        FornecedorService fornecedorService = new FornecedorService();

        // Controladores
        LojaController lojaController = new LojaController(
                agenciaService,
                validacaoLojaService,
                lojaService,
                contaService,
                vendaService,
                fornecedorService);

        AgenciaController agenciaController = new AgenciaController(agenciaService, validacaoService);

        return new ControllerPrincipal(agenciaController, lojaController);
    }

}
