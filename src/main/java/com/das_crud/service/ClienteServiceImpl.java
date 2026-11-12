package com.das_crud.service;

import com.das_crud.dto.ClienteRequestDto;
import com.das_crud.dto.ClienteResponseDto;
import com.das_crud.exception.RecursoNaoEncontradoException;
import com.das_crud.interfaces.ClienteService;
import com.das_crud.model.Cliente;
import com.das_crud.repository.ClienteRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDto> listar() {
        return repository.findAll(Sort.by("nome")).stream().map(this::paraDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDto buscar(Long id) {
        return paraDto(obter(id));
    }

    @Override
    public ClienteResponseDto salvar(ClienteRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(new Cliente(), dto)));
    }

    @Override
    public ClienteResponseDto atualizar(Long id, ClienteRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(obter(id), dto)));
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
        repository.flush();
    }

    private Cliente obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
    }

    private Cliente preencher(Cliente e, ClienteRequestDto dto) {
        e.setNome(dto.getNome());
        e.setEmail(dto.getEmail());
        e.setTelefone(dto.getTelefone());
        return e;
    }

    private ClienteResponseDto paraDto(Cliente e) {
        return new ClienteResponseDto(e.getId(), e.getNome(), e.getEmail(), e.getTelefone());
    }
}
