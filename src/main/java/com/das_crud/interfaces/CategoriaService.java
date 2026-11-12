package com.das_crud.interfaces;

import com.das_crud.dto.CategoriaRequestDto;
import com.das_crud.dto.CategoriaResponseDto;
import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDto> listar();

    CategoriaResponseDto buscar(Long id);

    CategoriaResponseDto salvar(CategoriaRequestDto dto);

    CategoriaResponseDto atualizar(Long id, CategoriaRequestDto dto);

    void excluir(Long id);
}
