package main.java.com.meuapp.model.loja.enums;

public enum StatusLoja {
    ATIVA (1),
    INATIVA (2),
    PENDENTE (3),
    BLOQUEADA (4);

    private final int statusLoja;

    StatusLoja(int statusLoja) {
        this.statusLoja = statusLoja;
    }

    public int getStatusLoja() {
        return statusLoja;
    }
}
