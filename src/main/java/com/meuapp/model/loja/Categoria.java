package main.java.com.meuapp.model.loja;

/*
* TODO: CRIAR MAIS CATEGORIAS
*/

public enum Categoria {
    VESTUARIO (1),
    ELETRONICOS (2),
    ALIMENTOS (3),
    ELETRODOMESTICOS (4),
    CALCADOS (5),
    MOVEIS (6),
    COSMETICOS (7),
    FARMACIA (8),
    LIVRARIA (9),
    PAPELARIA (10),
    ESPORTES (11),
    BRINQUEDOS (12),
    PET_SHOP (13),
    JOALHERIA (14),
    AUTOMOTIVO (15),
    INFORMATICA (16),
    TELEFONIA (17),
    CAMA_MESA_BANHO (18),
    UTILIDADES_DOMESTICAS(19),
    OUTROS (20);

    private final int categoriaNum;

    Categoria(int categoriaNum) {
        this.categoriaNum = categoriaNum;
    }

    public int getCategoriaNum() {
        return categoriaNum;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "categoriaNum=" + categoriaNum +
                '}';
    }
}
