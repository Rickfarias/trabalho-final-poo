package main.java.com.meuapp.controller;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.LojaBloqueadaException;
import main.java.com.meuapp.exception.SaldoInsuficienteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.model.venda.Venda;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.model.loja.enums.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.service.banco.AgenciaService;
import main.java.com.meuapp.service.banco.ContaService;
import main.java.com.meuapp.service.loja.FornecedorService;
import main.java.com.meuapp.service.loja.LojaService;
import main.java.com.meuapp.service.loja.ValidacaoLojaService;
import main.java.com.meuapp.service.produto.ProdutoService;
import main.java.com.meuapp.service.venda.VendaService;
import main.java.com.meuapp.util.EnumUtil;
import main.java.com.meuapp.util.InputUtil;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LojaController {
    AgenciaService agenciaService;
    ValidacaoLojaService validacaoLojaService;
    LojaService lojaService;
    ContaService contaService;
    VendaService vendaService;
    FornecedorService fornecedorService;
    ProdutoService produtoService;

    public LojaController(
            AgenciaService agenciaService,
            ValidacaoLojaService validacaoLojaService,
            LojaService lojaService,
            ContaService contaService,
            VendaService vendaService,
            FornecedorService fornecedorService,
            ProdutoService produtoService) {
        this.agenciaService = agenciaService;
        this.validacaoLojaService = validacaoLojaService;
        this.lojaService = lojaService;
        this.contaService = contaService;
        this.vendaService = vendaService;
        this.fornecedorService = fornecedorService;
        this.produtoService = produtoService;
    }

    public void menuPrincipalLoja() throws ContaInexistenteException {
        String opcoes = """
                    1 - Cadastrar loja
                    2 - Listar lojas
                    3 - Acessar loja
                    0 - Voltar
                    """;

        while (true) {
            int escolha = InputUtil.inputInt(opcoes);

            if (escolha == 0) {
                return;
            }

            switch (escolha) {
                case 1 -> menuCadastroLoja();
                case 2 -> lojaService.listarLojas();
                case 3 -> menuAcessarLoja();
                default -> InputUtil.info("Opção inválida.");
            }
        }
    }

    public void menuAcessarLoja() throws ContaInexistenteException {
        String opcoes = """
                1 - Entrar como cliente
                2 - Entrar como administrador
                0 - Voltar
                """;

        while (true) {
            int opcao = InputUtil.inputInt(opcoes);

            if (opcao == 0) {
                return;
            }

            switch (opcao) {
                case 1 -> menuClienteUI();
                case 2 -> menuAdministradorUI();
                default -> {
                    InputUtil.warn("Valor inválido. Tente novamente.", "AVISO");
                }
            }
        }
    }

    public void menuCadastroLoja() {

        String nomeLoja = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o nome da loja"),
                lojaService::validarNomeLoja
        );

        String cnpj = InputUtil.inputValidado(
                () -> InputUtil.inputString("Insira o CNPJ"),
                validacaoLojaService::validarCNPJ
        );

        Endereco end = InputUtil.inputValidado(
                () -> {
                    String cep = validacaoLojaService.validarCEP(
                            InputUtil.inputString("Insira o CEP")
                    );
                    String estado = InputUtil.inputString("Insira o estado");
                    String cidade = InputUtil.inputString("Insira a cidade");
                    String bairro = InputUtil.inputString("Insira o bairro");
                    String rua = InputUtil.inputString("Insira a rua");
                    String numero = InputUtil.inputValidado(
                            () -> InputUtil.inputString("Insira o número (ou s/n)"),
                            validacaoLojaService::validarNumeroCasa
                    );

                    return new Endereco(cep, estado, cidade, bairro, rua, numero);
                },
                lojaService::validarEndereco
        );

        Categoria categoria = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Categorias",
                        "Selecione a categoria:",
                        Categoria.values()
                ),
                lojaService::validarCategoria
        );

        Contato contato = InputUtil.inputValidado(
                () -> {
                    String telefone = InputUtil.inputString("Insira o telefone");
                    String email = InputUtil.inputString("Insira o email");

                    telefone = validacaoLojaService.validarTelefone(telefone);
                    email = validacaoLojaService.validarEmail(email);

                    return new Contato(telefone, email);
                },
                lojaService::validarContato
        );

        StatusLoja statusLoja = InputUtil.inputValidado(
                () -> EnumUtil.selecionarEnum(
                        "Status",
                        "Selecione o status:",
                        StatusLoja.values()
                ),
                lojaService::validarStatusLoja
        );

        String senhaContaLoja = InputUtil.inputString(
                "Crie uma senha para sua loja");

        BigDecimal capitalInicial = BigDecimal.valueOf(1000.0);
        BigDecimal caixaLoja = capitalInicial;
        ContaBancaria contaEmpresarial = agenciaService.criarContaLoja(
                nomeLoja,
                senhaContaLoja,
                cnpj,
                contato.getEmail(),
                end
        );

        lojaService.cadastrarLoja(
                nomeLoja,
                cnpj,
                end,
                categoria,
                contato,
                statusLoja,
                caixaLoja,
                contaEmpresarial
        );

        InputUtil.info("Loja cadastrada com sucesso! ID da Conta Bancária: " + contaEmpresarial.getId());
    }

    public void menuRealizarCompra(Cliente clienteLogado) {
        String nomeLoja = InputUtil.inputString("Digite o nome da loja:");

        Optional<Loja> lojaOpt = lojaService.buscarLojaPorNome(nomeLoja);
        if (lojaOpt.isEmpty()) {
            InputUtil.warn("Loja não encontrada.", "AVISO");
            return;
        }
        Loja loja = lojaOpt.get();

        Map<String, Produto> estoqueMap = loja.getEstoqueList();

        if (estoqueMap.isEmpty()) {
            InputUtil.warn("Esta loja não possui produtos em estoque.", "AVISO");
            return;
        }

        String[] opcoesProdutos = estoqueMap.values().stream()
                .filter(p -> p.getQuantidade() > 0)
                .map(p -> p.getIdProduto() + " - " + p.getNomeProduto() +
                        " (R$ " + String.format("%.2f", p.getPrecoVenda()) +
                        " | Disponível: " + p.getQuantidade() + ")")
                .toArray(String[]::new);

        if (opcoesProdutos.length == 0) {
            InputUtil.warn("Não há produtos com estoque disponível no momento.", "AVISO");
            return;
        }

        String produtoSelecionado = (String) JOptionPane.showInputDialog(
                null, "Selecione o produto:", "Estoque da Loja",
                JOptionPane.QUESTION_MESSAGE, null, opcoesProdutos, opcoesProdutos[0]
        );

        if (produtoSelecionado == null) return;

        String idProduto = produtoSelecionado.split(" - ")[0];
        int qtdDesejada = InputUtil.inputInt("Insira a quantidade:");

        try {
            vendaService.realizarVenda(
                    loja.getCnpj(),
                    clienteLogado,
                    idProduto,
                    qtdDesejada
            );
            InputUtil.info("Compra realizada com sucesso!");
        } catch (Exception e) {
            InputUtil.error(e.getMessage(), "FALHA NA VENDA");
        }
    }


    public void menuClienteUI() {
        ContaBancaria contaCliente = null;

        while (contaCliente == null) {
            String idLogin = InputUtil.inputString("Digite o id do cliente");
            String senhaLogin = InputUtil.inputString("Digite a senha");

            try {
                contaCliente = lojaService.acessarConta(idLogin, senhaLogin);
                Cliente cliente = lojaService.buscarClientePorConta(contaCliente);

                executarMenuInternoCliente(cliente);
            } catch (ContaInexistenteException e) {
                InputUtil.warn(e.getMessage(), "AVISO");
            } catch (SenhaIncorretaException e) {
                InputUtil.error(e.getMessage(), "ERRO");
            }

            if (contaCliente == null) {
                String tentarNovamente = InputUtil.inputString("Deseja tentar novamente? (s/n): ").toLowerCase();
                if (tentarNovamente.equals("n") || tentarNovamente.equals("não")) {
                    return;
                }
            }
        }
    }

    private void executarMenuInternoCliente(Cliente cliente) {
        String opcoes = """
            1 - Listar produtos
            2 - Comprar produtos
            0 - Voltar
            """;
        while (true) {
            int escolha = InputUtil.inputInt(opcoes);
            if (escolha == 0) return;

            switch (escolha) {
                case 1 -> produtoService.listarEstoque();
                case 2 -> menuRealizarCompra(cliente);
            }
        }
    }

    public void menuAdministradorUI() throws ContaInexistenteException {
        ContaBancaria conta = null;

        while (conta == null) {
            String idLogin = InputUtil.inputString("Digite o ID da conta da loja");
            String senhaLogin = InputUtil.inputString("Digite a senha");

            try {
                conta = lojaService.acessarConta(idLogin, senhaLogin);
            } catch (ContaInexistenteException e) {
                InputUtil.warn(e.getMessage(), "AVISO");
            } catch (SenhaIncorretaException e) {
                InputUtil.error(e.getMessage(), "ERRO");
            }

            if (conta == null) {
                String tentarNovamente = InputUtil.inputString("Deseja tentar novamente? (s/n): ").toLowerCase();
                if (tentarNovamente.equals("n") || tentarNovamente.equals("não")) {
                    return;
                }
            }
        }

            String opcoes = """
                    1 - Cadastrar produto
                    2 - Editar produto
                    3 - Excluir produto
                    4 - Comprar do fornecedor
                    5 - Histórico de vendas
                    0 - Voltar
                    """;

            while (true) {
                int menu = InputUtil.inputInt(opcoes);

                if (menu == 0) {
                    return;
                }

                switch (menu) {
                    case 1 -> menuCadastrarProdutoUI();
                    case 2 -> menuEditarProdutoUI();
                    case 3 -> menuExcluirProdutoUI();
                    case 4 -> menuComprarDoFornecedorUI();
                    case 5 -> historicoVendasUI();
                    default -> InputUtil.warn("Opção inválida.", "AVISO");
                }
            }
    }

    public void historicoVendasUI() {
        String nomeLoja = InputUtil.inputString("Digite o nome da loja:");
        List<Venda> vendas = vendaService.historicoVendas(nomeLoja);

        if (vendas.isEmpty()) {
            InputUtil.info("Nenhuma venda registrada.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== HISTÓRICO DE VENDAS ===\n");
        for (Venda v : vendas) {
            sb.append("Cliente: ").append(v.getCliente().getNome())
                    .append(" | Fornecedor: ").append(v.getFornecedor().getNomeFornecedor())
                    .append(" | Valor: R$ ").append(v.getValorTotal())
                    .append(" | Data: ").append(v.getData())
                    .append("\n");
        }

        InputUtil.info(sb.toString());
    }

    private void menuComprarDoFornecedorUI() throws ContaInexistenteException {
        String numeroNotaFiscal = InputUtil.inputString(
                "Insira o número da nota fiscal:"
        );

        Loja minhaLoja = null;
        while (minhaLoja == null) {
            String cnpjLoja = InputUtil.inputString("Digite o CNPJ da loja:");
            minhaLoja = lojaService.buscarCNPJDaLoja(cnpjLoja).orElse(null);

            if (minhaLoja == null) {
                InputUtil.warn("Fornecedor não encontrado. Tente novamente.", "AVISO");
            }

        }

        List<Fornecedor> fornecedores = fornecedorService.listarTodos();

        if (fornecedores.isEmpty()) {
            InputUtil.warn("Nenhum fornecedor cadastrado.", "AVISO");
            return;
        }

        String[] opcoesFornecedores = fornecedores.stream()
                .map(f -> f.getContaEmpresarialFornecedor().getId() + " - " + f.getNomeFornecedor())
                .toArray(String[]::new);

        String fornecedorSelecionado = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o fornecedor:",
                "Fornecedores Disponíveis",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesFornecedores,
                opcoesFornecedores[0]
        );

        if (fornecedorSelecionado == null) {
            InputUtil.info("Operação cancelada.");
            return;
        }

        String idFornecedor = fornecedorSelecionado.split(" - ")[0];

        Fornecedor fornecedor = fornecedorService.buscarPorId(idFornecedor)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        fornecedorService.iniciarCompra(numeroNotaFiscal, minhaLoja, fornecedor);

        boolean continuar = true;

        while (continuar) {
            List<Produto> produtosFornecedor = produtoService.buscarPorIdLoja(fornecedor.getCnpjFornecedor());

            if (produtosFornecedor.isEmpty()) {
                InputUtil.warn("Este fornecedor não possui produtos cadastrados.", "AVISO");
                break;
            }

            String[] opcoesProdutos = produtosFornecedor.stream()
                    .map(p -> p.getIdProduto() + " - " + p.getNomeProduto() +
                            " (R$ " + String.format("%.2f", p.getPrecoCusto()) +
                            " | Estoque: " + p.getQuantidade() + ")")
                    .toArray(String[]::new);

            String produtoSelecionado = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecione o produto:",
                    "Produtos Disponíveis",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoesProdutos,
                    opcoesProdutos[0]
            );

            if (produtoSelecionado == null) {
                InputUtil.info("Seleção de produto cancelada.");
                break;
            }

            String idProduto = produtoSelecionado.split(" - ")[0];

            int qtdProduto = InputUtil.inputInt(
                    "Insira a quantidade:"
            );

            fornecedorService.adicionarItem(numeroNotaFiscal, idProduto, qtdProduto);

            continuar = InputUtil.inputBoolean(
                    "Deseja adicionar outro produto? (sim/não)"
            );
        }

        boolean compraFinalizada = false;
        while (!compraFinalizada) {
            try {
                fornecedorService.finalizarCompra(numeroNotaFiscal);
                InputUtil.info("Compra do fornecedor finalizada com sucesso!");
                compraFinalizada = true;
            } catch (SaldoInsuficienteException e) {
                InputUtil.error(e.getMessage(), "SALDO INSUFICIENTE");

                String tentarNovamente = InputUtil.inputString(
                        "A compra ficou como PENDENTE. Deseja tentar finalizar novamente? (s/n):"
                ).toLowerCase();

                if (tentarNovamente.equals("n") || tentarNovamente.equals("não")) {
                    InputUtil.warn("Compra cancelada. Status: PENDENTE", "AVISO");
                    compraFinalizada = true;
                }
            } catch (LojaBloqueadaException e) {
                InputUtil.error(e.getMessage(), "LOJA BLOQUEADA");

                String opcao = InputUtil.inputString(
                        "A loja atingiu o limite de erros. Deseja solicitar desbloqueio ao suporte? (s/n): "
                ).toLowerCase();

                if (opcao.equals("s")) {
                    try {
                        lojaService.solicitarDesbloqueio(minhaLoja.getCnpj());
                        InputUtil.info("Loja desbloqueada com sucesso! Tente finalizar a compra novamente.");
                    } catch (Exception ex) {
                        InputUtil.error("Falha ao desbloquear: " + ex.getMessage(), "ERRO");
                    }
                }
                else {
                    compraFinalizada = true;
                }
            }
        }
    }

    private void menuCadastrarProdutoUI() {
        String cnpj = InputUtil.inputString("Digite o CNPJ da loja que possui o produto");
        if (lojaService.buscarCNPJDaLoja(cnpj).isEmpty()) {
            InputUtil.error("Erro: Loja não encontrada!", "ERRO");
            return;
        }

        String idProduto = InputUtil.inputValidado(
                () -> InputUtil.inputString("Digite o código do produto (4 dígitos):"),
                id -> {
                    if (id.length() != 4 || !id.matches("\\d+"))
                        throw new IllegalArgumentException("O ID deve ter exatamente 4 números.");
                }
        );

        String nomeProduto = InputUtil.inputString("Digite o nome do produto");
        double preco = InputUtil.inputDouble("Digite o preço do produto");
        int qtd = InputUtil.inputInt("Digite a quantidade do produto no estoque");

        Produto novoProduto = new Produto(cnpj, idProduto, nomeProduto, preco, qtd);
        produtoService.cadastrarNovoProduto(novoProduto);

        Optional<String> nomeLojaOptional = lojaService.buscarNomeDaLojaPeloCNPJ(cnpj);
        String nomeLoja = nomeLojaOptional.orElse("Loja não encontrada");

        InputUtil.info(
                "Produto '" + nomeProduto + "' cadastrado com sucesso para a loja " + nomeLoja
        );

    }

    private void menuEditarProdutoUI() {

        Produto produto = null;
        while (produto == null) {
            String idProduto = InputUtil.inputString(
                    "Digite o código do produto que deseja atualizar");
            produto = produtoService.buscarPorId(idProduto)
                        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            if (produto == null) {
                InputUtil.warn("Produto não encontrado. Tente novamente", "AVISO");
            }
        }

        InputUtil.info(String.format("""
                Produto encontradp:
                ID: %s,
                Nome: %s,
                Preço: %.2f
                Quantidade em estoque: %d.
                """, produto.getIdProduto(),
                produto.getNomeProduto(),
                produto.getPrecoCusto(),
                produto.getQuantidade()));

        // so sera possivel atualizar nome e preco, visto que para atualizar qtd é necessario comprar mais produtos
        String opcoes = """
                O que deseja editar?
                1 - Nome
                2 - Preco
                0 - Voltar
                """;

        while (true) {
            int escolha = InputUtil.inputInt(opcoes);

            if (escolha == 0) {
                return;
            }

            switch (escolha) {
                case 1 -> {
                    String novoNome = InputUtil.inputString("Digite o novo nome:");
                    produto.setNomeProduto(novoNome);
                    produtoService.atualizarProduto(produto);
                    InputUtil.info("Nome atualizado com sucesso!");
                    return;
                }

                case 2 -> {
                    double novoPreco = InputUtil.inputDouble("Digite o novo preço:");
                    produto.setPrecoCusto(novoPreco);
                    produtoService.atualizarProduto(produto);
                    InputUtil.info("Preço atualizado com sucesso!");
                    return;
                }
                default -> InputUtil.warn("Opção inválida.", "AVISO");
            }
        }
    }

    private void menuExcluirProdutoUI() {
        String idProduto = InputUtil.inputString(
                "Digite o código do produto que deseja excluir:"
        );

        Produto produto;
        try {
            produto = produtoService.buscarPorId(idProduto)
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        } catch (IllegalArgumentException e) {
            InputUtil.warn(e.getMessage(), "AVISO");
            return;
        }

        InputUtil.info(String.format("""
            Produto encontrado:
            ID: %s
            Nome: %s
            Preço de venda: %.2f
            Quantidade em estoque: %d
            """,
                produto.getIdProduto(),
                produto.getNomeProduto(),
                produto.getPrecoVenda(),
                produto.getQuantidade()
        ));

        if (produto.getQuantidade() > 0) {
            InputUtil.warn(
                    "Não é possível excluir um produto com estoque disponível.",
                    "OPERAÇÃO BLOQUEADA"
            );
            return;
        }

        boolean confirmar = InputUtil.inputBoolean(
                "Tem certeza que deseja excluir este produto? (sim/não)"
        );

        if (!confirmar) {
            InputUtil.info("Exclusão cancelada.");
            return;
        }

        produtoService.excluirProduto(idProduto);
        InputUtil.info("Produto excluído com sucesso!");
    }
}