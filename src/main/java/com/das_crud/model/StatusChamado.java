package com.das_crud.model;

public enum StatusChamado {
    ABERTO("Aberto", "primary"),
    EM_ANDAMENTO("Em andamento", "warning"),
    RESOLVIDO("Resolvido", "success"),
    FECHADO("Fechado", "secondary");

    private final String rotulo;
    private final String cor;

    StatusChamado(String rotulo, String cor) {
        this.rotulo = rotulo;
        this.cor = cor;
    }

    public String getRotulo() { return rotulo; }

    /** Cor do badge do Bootstrap (text-bg-*). */
    public String getCor() { return cor; }
}
