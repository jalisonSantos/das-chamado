package com.das_crud.dto;

import com.das_crud.model.Prioridade;
import com.das_crud.model.StatusChamado;
import jakarta.validation.constraints.*;

public class ChamadoRequestDto {

    @NotBlank(message = "Informe o título")
    @Size(max = 150, message = "Máximo de 150 caracteres")
    private String titulo;

    @NotBlank(message = "Descreva o problema")
    @Size(max = 2000, message = "Máximo de 2000 caracteres")
    private String descricao;

    @NotNull(message = "Selecione a prioridade")
    private Prioridade prioridade = Prioridade.MEDIA;

    @NotNull(message = "Selecione o status")
    private StatusChamado status = StatusChamado.ABERTO;

    @NotNull(message = "Selecione a categoria")
    private Long categoriaId;

    @NotNull(message = "Selecione o cliente")
    private Long clienteId;

    // opcional
    private Long tecnicoId;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }

}
