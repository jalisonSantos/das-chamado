package com.das_crud.model;

public enum Prioridade {
    BAIXA("Baixa", "success"),
    MEDIA("Média", "warning"),
    ALTA("Alta", "danger");

    private final String rotulo;
    private final String cor;

    Prioridade(String rotulo, String cor) {
        this.rotulo = rotulo;
        this.cor = cor;
    }

    public String getRotulo() { return rotulo; }

    /** Cor do badge do Bootstrap (text-bg-*). */
    public String getCor() { return cor; }
}
