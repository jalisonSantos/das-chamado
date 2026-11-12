package com.das_crud.interfaces;

import com.das_crud.dto.TecnicoRequestDto;
import com.das_crud.dto.TecnicoResponseDto;
import java.util.List;

public interface TecnicoService {

    List<TecnicoResponseDto> listar();

    TecnicoResponseDto buscar(Long id);

    TecnicoResponseDto salvar(TecnicoRequestDto dto);

    TecnicoResponseDto atualizar(Long id, TecnicoRequestDto dto);

    void excluir(Long id);
}
