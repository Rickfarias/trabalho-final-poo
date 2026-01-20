package main.java.com.meuapp.service.venda;

import main.java.com.meuapp.model.produto.Produto;

import java.util.ArrayList;
import java.util.List;

public class CompraFornecedor {
    private String numeroNotaFiscal;
    private List<ItemCompra> itens = new ArrayList<>();
    private boolean finalizada = false;

    public CompraFornecedor(String numeroNotaFiscal) {
        if (numeroNotaFiscal == null || numeroNotaFiscal.isBlank()) {
            throw new IllegalArgumentException("Nota fiscal inválida");
        }
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (finalizada) {
            throw new IllegalStateException("Compra já finalizada");
        }
        itens.add(new ItemCompra(produto, quantidade));
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public void finalizar() {
        this.finalizada = true;
    }

    public boolean isFinalizada() {
        return finalizada;
    }
}
