package main.java.com.meuapp.service.venda;

import main.java.com.meuapp.exception.ContaInexistenteException;
import main.java.com.meuapp.model.banco.ContaBancaria;
import main.java.com.meuapp.model.cliente.Cliente;
import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.venda.Venda;
import main.java.com.meuapp.repository.LojaRepository;
import main.java.com.meuapp.service.banco.ContaService;

import java.util.List;

public class VendaService {
    LojaRepository lojaRepository;
    ContaService contaService;

    public VendaService(LojaRepository lojaRepository, ContaService contaService) {
        this.lojaRepository = lojaRepository;
        this.contaService = contaService;
    }

    public void realizarVenda(
            String nomeLoja,
            String nomeFornecedor,
            Cliente cliente,
            double valor) throws ContaInexistenteException {

        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        Fornecedor fornecedor = loja.buscarFornecedorPorNome(nomeFornecedor);

        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor não encontrado");
        }

        ContaBancaria contaCliente = cliente.getConta();
        ContaBancaria contaLoja = loja.getContaEmpresarial();
        ContaBancaria contaFornecedor = fornecedor.getContaEmpresarialFornecedor();

        contaService.transferir(contaCliente, contaLoja, valor);

        double valorFornecedor = valor * 0.3;
        contaService.transferir(contaLoja, contaFornecedor, valorFornecedor);

        Venda venda = new Venda(cliente, loja, fornecedor, valor);
        loja.registrarVenda(venda);
    }

    public List<String> listarNomesFornecedores(String nomeLoja) {
        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        return loja.getFornecedores().stream()
                .map(Fornecedor::getNomeFornecedor)
                .toList();
    }

    public List<Venda> historicoVendas(String nomeLoja) {
        Loja loja = lojaRepository.buscarPorNome(nomeLoja)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada"));

        return loja.getHistoricoVendas();
    }

    public double calcularValorVenda(double valorCompra) {
        if (valorCompra <= 0) {
            throw new IllegalArgumentException("Valor de compra inválido");
        }

        return valorCompra * 1.3;
    }



}
