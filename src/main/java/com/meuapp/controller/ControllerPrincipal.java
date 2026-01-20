package main.java.com.meuapp.controller;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.loja.enums.Categoria;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.*;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.banco.ValidacaoService;
import main.java.com.meuapp.service.loja.FornecedorService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.service.produto.ProdutoService;
import main.java.com.meuapp.service.venda.VendaService;
import main.java.com.meuapp.util.InputUtil;

import java.math.BigDecimal;

public class ControllerPrincipal {
    AgenciaController agenciaController;
    LojaController lojaController;

    public ControllerPrincipal(AgenciaController agenciaController, LojaController lojaController) {
        this.agenciaController = agenciaController;
        this.lojaController = lojaController;
    }

    public void menuPrincipalUI() throws ContaInexistenteException {
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
        // Repositórios
        ContaRepository repository = new ContaRepository();
        ClienteRepository clienteRepository = new ClienteRepository();
        LojaRepository lojaRepository =  new LojaRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        FornecedorRepository fornecedorRepository = new FornecedorRepository();

        // Serviços
        ContaService contaService = new ContaService();
        AgenciaService agenciaService = new AgenciaService(contaService, repository);
        ValidacaoService validacaoService = new ValidacaoService();
        ValidacaoLojaService validacaoLojaService = new ValidacaoLojaService();
        LojaService lojaService = new LojaService(contaService, clienteRepository, lojaRepository);
        VendaService vendaService = new VendaService(lojaRepository, contaService);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        FornecedorService fornecedorService = new FornecedorService(produtoRepository, produtoService, contaService, fornecedorRepository);

        // Controladores
        LojaController lojaController = new LojaController(
                agenciaService,
                validacaoLojaService,
                lojaService,
                contaService,
                vendaService,
                fornecedorService,
                produtoService);

        AgenciaController agenciaController = new AgenciaController(agenciaService, validacaoService);

        // ==================== LOJA PADRÃO ====================
        criarLojaPadrao(agenciaService, lojaService, produtoService);

        return new ControllerPrincipal(agenciaController, lojaController);
    }

    private static void criarLojaPadrao(
            AgenciaService agenciaService,
            LojaService lojaService,
            ProdutoService produtoService
    ) {
        try {
            Endereco endereco = new Endereco(
                    "12345-000",
                    "SP",
                    "São Paulo",
                    "Centro",
                    "Rua Principal",
                    "100"
            );

            Contato contato = new Contato(
                    "(11) 99999-9999",
                    "lojapadrao@email.com"
            );

            ContaBancaria contaLoja = agenciaService.criarContaLoja(
                    "Loja Padrão",
                    "1234",
                    "00000000000100",
                    contato.getEmail(),
                    endereco
            );

            Loja loja = lojaService.cadastrarLoja(
                    "Loja Padrão",
                    "00000000000100",
                    endereco,
                    Categoria.ALIMENTOS,
                    contato,
                    StatusLoja.ATIVA,
                    BigDecimal.valueOf(50000.0), // Saldo maior para poder comprar
                    contaLoja
            );

            Produto produto = new Produto(
                    loja.getCnpj(),
                    "0001",
                    "Produto Inicial",
                    10.0,
                    0
            );
            produtoService.cadastrarNovoProduto(produto);

            InputUtil.info("Loja padrão criada com sucesso!");


        } catch (Exception e) {
            InputUtil.error("Erro ao criar loja padrão: " + e.getMessage(), "ERRO");
        }
    }

    private static void criarFornecedorPadrao(
            AgenciaService agenciaService,
            FornecedorRepository fornecedorRepository
    ) {
        try {
            Endereco enderecoFornecedor = new Endereco(
                    "54321-000",
                    "RJ",
                    "Rio de Janeiro",
                    "Zona Norte",
                    "Av. Brasil",
                    "500"
            );

            Contato contatoFornecedor = new Contato(
                    "(21) 98888-7777",
                    "fornecedor@email.com"
            );

            ContaBancaria contaFornecedor = agenciaService.criarContaLoja(
                    "Fornecedor Teste LTDA",
                    "senha123",
                    "12.345.678/0001-90",
                    contatoFornecedor.getEmail(),
                    enderecoFornecedor
            );

            Fornecedor fornecedorPadrao = new Fornecedor(
                    "Fornecedor Teste LTDA",
                    "12.345.678/0001-90",
                    Categoria.OUTROS,
                    contatoFornecedor,
                    contaFornecedor
            );

            fornecedorRepository.salvar(fornecedorPadrao);

            InputUtil.info("Fornecedor padrão criado com sucesso!");

        } catch (Exception e) {
            InputUtil.error("Erro ao criar fornecedor padrão: " + e.getMessage(), "ERRO");
        }
    }
}