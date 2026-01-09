package main.java.com.meuapp.model.loja.enums;

/*
* TODO: CRIAR MAIS CATEGORIAS
*/

public enum Categoria {
    VESTUARIO (1, "Vestuário"),
    ELETRONICOS (2, "Eletrônicos"),
    ALIMENTOS (3, "Alimentos"),
    ELETRODOMESTICOS (4, "Eletrodomésticos"),
    CALCADOS (5, "Calçados"),
    MOVEIS (6, "Móveis"),
    COSMETICOS (7, "Cosméticos"),
    FARMACIA (8, "Farmácia"),
    LIVRARIA (9, "Livraria"),
    PAPELARIA (10, "Papelaria"),
    ESPORTES (11, "Esportes"),
    BRINQUEDOS (12, "Brinquedos"),
    PET_SHOP (13, "Pet Shop"),
    JOALHERIA (14, "Joalheria"),
    AUTOMOTIVO (15, "Automotivo"),
    INFORMATICA (16, "Informática"),
    TELEFONIA (17, "Telefonia"),
    CAMA_MESA_BANHO (18, "Cama, mesa e banho"),
    UTILIDADES_DOMESTICAS(19, "Utilidades domesticas"),
    AUTOPECAS (20, "Autopeças"),
    OUTROS (21, "Outros");

    private final int categoriaNum;
    private final String descricao;

    Categoria(int categoriaNum, String descricao) {
        this.categoriaNum = categoriaNum;
        this.descricao = descricao;
    }

    public int getCategoriaNum() {
        return categoriaNum;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
