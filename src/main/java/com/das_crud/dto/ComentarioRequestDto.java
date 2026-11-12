package com.das_crud.dto;

import jakarta.validation.constraints.*;

public class ComentarioRequestDto {

    // definido pelo controller a partir da URL
    private Long chamadoId;

    @NotBlank(message = "Informe o autor")
    @Size(max = 80, message = "Máximo de 80 caracteres")
    private String autor;

    @NotBlank(message = "Escreva o comentário")
    @Size(max = 2000, message = "Máximo de 2000 caracteres")
    private String texto;

    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

}
