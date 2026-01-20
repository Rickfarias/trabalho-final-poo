package main.java.com.meuapp.service.loja;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.exception.SenhaIncorretaException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.banco.Pessoa;
import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.enums.Categoria;
import main.java.com.meuapp.model.loja.Contato;
import main.java.com.meuapp.model.loja.Endereco;
import main.java.com.meuapp.model.loja.enums.StatusLoja;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.produto.Produto;
import main.java.com.meuapp.repository.ClienteRepository;
import main.java.com.meuapp.repository.ContaRepository;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.ContaService;

import java.math.BigDecimal;
import java.util.Optional;

public class LojaService {
    private final ContaService contaService;
    private final ClienteRepository clienteRepository;
    private final LojaRepository lojaRepository;

    public LojaService(ContaService contaService, ClienteRepository clienteRepository, LojaRepository lojaRepository) {
        this.contaService = contaService;
        this.clienteRepository = clienteRepository;
        this.lojaRepository = lojaRepository;
    }

    public Loja cadastrarLoja(
            String nomeLoja,
            String cnpj,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja,
            ContaBancaria contaEmpresarial) {

        Loja loja = new Loja(
                nomeLoja.trim(),
                cnpj,
                endereco,
                categoria,
                contato,
                statusLoja,
                caixaLoja,
                contaEmpresarial
        );

        lojaRepository.salvarLoja(loja);
        return loja;
    }

    private void validarLoja(
            String nomeLoja,
            Endereco endereco,
            Categoria categoria,
            Contato contato,
            StatusLoja statusLoja,
            BigDecimal caixaLoja) {

        validarNomeLoja(nomeLoja);
        validarEndereco(endereco);
        validarCategoria(categoria);
        validarContato(contato);
        validarStatusLoja(statusLoja);
        validarCaixaLoja(caixaLoja);
    }

    public void validarNomeLoja(String nomeLoja) {
        if (nomeLoja == null || nomeLoja.isBlank()) {
            throw new IllegalArgumentException("Nome da loja é obrigatório");
        }
    }

    public void validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }
    }

    public void validarStatusLoja(StatusLoja statusLoja) {
        if (statusLoja == null) {
            throw new IllegalArgumentException("Status da loja é obrigatório");
        }
    }

    public void validarCaixaLoja(BigDecimal caixaLoja) {
        if (caixaLoja == null || caixaLoja.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Caixa da loja inválido");
        }
    }

    public void validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }
    }

    public void validarContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato é obrigatório.");
        }
    }

    public void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }
    }

    public void listarLojas() {
        lojaRepository.listarLojas();
    }

    public Optional<String> buscarNomeDaLojaPeloCNPJ(String cnpj) {
        return lojaRepository.buscarPorCnpj(cnpj).map(Loja::getNomeLoja);
    }

    public Optional<Loja> buscarLojaPorNome(String nomeLoja) {
        return lojaRepository.buscarPorNome(nomeLoja);
    }

    public Optional<Loja> buscarCNPJDaLoja(String cnpj) {
        return lojaRepository.buscarPorCnpj(cnpj);
    }

    public String listarProdutos(String nomeLoja) {
        Optional<String> cnpj = lojaRepository.buscarPorNome(nomeLoja).map(Loja::getCnpj);

        return null;
    }

    public void vincularFornecedor(String nomeLoja, Fornecedor fornecedor) {
        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        loja.adicionarFornecedor(fornecedor);
    }

    public ContaBancaria acessarConta(String id, String senha)
            throws ContaInexistenteException, SenhaIncorretaException {
        ContaBancaria conta = ContaRepository.buscarContaPorID(id);

        if (conta == null) {
            throw new ContaInexistenteException("A conta não foi encontrada!");
        }

        if (!conta.getTitular().getSenha().equals(senha)) {
            throw new SenhaIncorretaException("Senha incorreta");
        }

        return conta;
    }

    public void solicitarDesbloqueio(String cnpjLoja) {
        Optional<Loja> loja = lojaRepository.buscarPorCnpj(cnpjLoja);

        if (loja.isPresent()) {
            Loja loja1 = loja.get();

            if (loja1.getStatusLoja() == StatusLoja.BLOQUEADA) {
                loja1.setStatusLoja(StatusLoja.ATIVA);
                loja1.resetarTentativasFalhadas();

                lojaRepository.salvarLoja(loja1);

            }
            else {
                throw new IllegalArgumentException("A loja não está bloqueada, status da loja" + loja1.getStatusLoja());
            }
        }
        else {
            throw new IllegalArgumentException("Loja com CNPJ " + cnpjLoja + " não encontrada");
        }
    }

    public Cliente buscarClientePorConta(ContaBancaria conta) {
        Pessoa titular = conta.getTitular();

        if (titular instanceof Cliente cliente) {
            return cliente;
        }
        return clienteRepository.listarTodos().stream()
                .filter(c -> c.getConta().getId().equals(conta.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para esta conta."));
    }
}
