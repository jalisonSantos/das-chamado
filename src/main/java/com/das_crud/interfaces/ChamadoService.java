package com.das_crud.interfaces;

import com.das_crud.dto.ChamadoRequestDto;
import com.das_crud.dto.ChamadoResponseDto;
import com.das_crud.model.StatusChamado;
import java.util.List;
import java.util.Map;

public interface ChamadoService {

    /** Lista os chamados (mais recentes primeiro); {@code status} nulo = todos. */
    List<ChamadoResponseDto> listar(StatusChamado status);

    ChamadoResponseDto buscar(Long id);

    ChamadoResponseDto salvar(ChamadoRequestDto dto);

    ChamadoResponseDto atualizar(Long id, ChamadoRequestDto dto);

    void alterarStatus(Long id, StatusChamado status);

    void excluir(Long id);

    Map<StatusChamado, Long> contarPorStatus();
}
