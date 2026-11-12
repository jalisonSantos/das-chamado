package com.das_crud.interfaces;

import com.das_crud.dto.ComentarioRequestDto;
import com.das_crud.dto.ComentarioResponseDto;

public interface ComentarioService {

    ComentarioResponseDto adicionar(ComentarioRequestDto dto);

    /** Remove o comentário e devolve o id do chamado ao qual pertencia. */
    Long excluir(Long id);
}
