package com.das_crud.interfaces;

import com.das_crud.dto.ClienteRequestDto;
import com.das_crud.dto.ClienteResponseDto;
import java.util.List;

public interface ClienteService {

    List<ClienteResponseDto> listar();

    ClienteResponseDto buscar(Long id);

    ClienteResponseDto salvar(ClienteRequestDto dto);

    ClienteResponseDto atualizar(Long id, ClienteRequestDto dto);

    void excluir(Long id);
}
