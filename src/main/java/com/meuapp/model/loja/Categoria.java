package main.java.com.meuapp.model.loja;

/*
* TODO: CRIAR MAIS CATEGORIAS
*/

public enum Categoria {
    VESTUARIO (1),
    ELETRONICOS (2),
    ALIMENTOS (3),
    ELETRODOMESTICOS (4);

    private final int categoriaNum;

    Categoria(int categoriaNum) {
        this.categoriaNum = categoriaNum;
    }

    public int getCategoriaNum() {
        return categoriaNum;
    }
}
