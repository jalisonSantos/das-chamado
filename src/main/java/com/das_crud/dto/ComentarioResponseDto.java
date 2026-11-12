package com.das_crud.dto;

import java.time.LocalDateTime;

public record ComentarioResponseDto(Long id, String autor, String texto, LocalDateTime dataCriacao) {
}
