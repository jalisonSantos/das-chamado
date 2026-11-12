package com.das_crud.dto;

import jakarta.validation.constraints.*;

public class CategoriaRequestDto {

    @NotBlank(message = "Campo obrigatório")
    @Size(max = 80, message = "Máximo de 80 caracteres")
    private String nome;

    @Size(max = 255, message = "Máximo de 255 caracteres")
    private String descricao;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}
