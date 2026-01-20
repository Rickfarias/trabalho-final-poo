    package main.java.com.meuapp.model.produto;

    public class Produto {
        private String idLoja;
        private String idProduto;
        private String nomeProduto;
        private double precoCusto;
        private double precoVenda;
        private int quantidade;

        public Produto(String idLoja, String idProduto, String nomeProduto, double precoCusto, int quantidade) {
            this.idLoja = idLoja;
            this.idProduto = idProduto;
            this.nomeProduto = nomeProduto;
            this.quantidade = quantidade;
            atualizarPrecoCusto(precoCusto);
        }

        public String getIdLoja() {
            return idLoja;
        }

        public void setIdLoja(String idLoja) {
            this.idLoja = idLoja;
        }

        public String getIdProduto() {
            return idProduto;
        }

        public void setIdProduto(String idProduto) {
            this.idProduto = idProduto;
        }

        public String getNomeProduto() {
            return nomeProduto;
        }

        public void setNomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
        }

        public double getPrecoCusto() {
            return precoCusto;
        }

        public void setPrecoCusto(double precoCusto) {
            this.precoCusto = precoCusto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getPrecoVenda() {
            return precoVenda;
        }

        public void adicionarQuantidade(int quantidade) {
            this.quantidade += quantidade;
        }

        public void removerQuantidade(int qtd) {
            if (qtd <= 0) {
                throw new IllegalArgumentException("Quantidade inválida");
            }
            if (this.quantidade < qtd) {
                throw new IllegalStateException("Estoque insuficiente");
            }
            this.quantidade -= qtd;
        }

        public void atualizarPrecoCusto(double novoCusto) {
            if (novoCusto < 0) throw new IllegalArgumentException("Preço de custo inválido");
            this.precoCusto = novoCusto;
            this.precoVenda = novoCusto * 1.30;
        }

        @Override
        public String toString() {
            return String.format("""
                   Produto:
                   id: %s,
                   nome: %s,
                   preço: %.2f,
                   quantidade: %d
                   """, idProduto, nomeProduto, precoCusto, quantidade);
        }
    }
