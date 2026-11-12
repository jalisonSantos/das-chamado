package com.das_crud.dto;

import com.das_crud.model.Prioridade;
import com.das_crud.model.StatusChamado;
import java.time.LocalDateTime;
import java.util.List;

public record ChamadoResponseDto(
        Long id,
        String titulo,
        String descricao,
        StatusChamado status,
        Prioridade prioridade,
        LocalDateTime dataAbertura,
        Long categoriaId,
        String categoriaNome,
        Long clienteId,
        String clienteNome,
        Long tecnicoId,
        String tecnicoNome,
        List<ComentarioResponseDto> comentarios) {
}
