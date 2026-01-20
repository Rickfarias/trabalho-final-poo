package main.java.com.meuapp.service.venda;

import main.java.com.meuapp.model.loja.Fornecedor;
import main.java.com.meuapp.model.loja.Loja;
import main.java.com.meuapp.model.produto.Produto;

import java.util.ArrayList;
import java.util.List;

public class CompraFornecedor {
    private String numeroNotaFiscal;
    private List<ItemCompra> itens = new ArrayList<>();
    private boolean finalizada = false;
    private Loja loja;
    private Fornecedor fornecedor;

    public CompraFornecedor(String numeroNotaFiscal, Loja loja, Fornecedor fornecedor) {
        if (numeroNotaFiscal == null || numeroNotaFiscal.isBlank()) {
            throw new IllegalArgumentException("Nota fiscal inválida");
        }
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.loja = loja;
        this.fornecedor = fornecedor;
    }

    public double calcularTotalNota() {
        return itens.stream()
                .mapToDouble(item -> item.getProduto().getPrecoCusto() * item.getQuantidade())
                .sum();
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (finalizada) {
            throw new IllegalStateException("Compra já finalizada");
        }
        itens.add(new ItemCompra(produto, quantidade));
    }

    public List<ItemCompra> getItens() { return itens; }
    public Loja getLoja() { return loja; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public boolean isFinalizada() { return finalizada; }

    public void finalizar() { this.finalizada = true; }


}
