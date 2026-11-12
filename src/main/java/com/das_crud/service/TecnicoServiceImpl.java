package com.das_crud.service;

import com.das_crud.dto.TecnicoRequestDto;
import com.das_crud.dto.TecnicoResponseDto;
import com.das_crud.exception.RecursoNaoEncontradoException;
import com.das_crud.interfaces.TecnicoService;
import com.das_crud.model.Tecnico;
import com.das_crud.repository.TecnicoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository repository;

    public TecnicoServiceImpl(TecnicoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TecnicoResponseDto> listar() {
        return repository.findAll(Sort.by("nome")).stream().map(this::paraDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TecnicoResponseDto buscar(Long id) {
        return paraDto(obter(id));
    }

    @Override
    public TecnicoResponseDto salvar(TecnicoRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(new Tecnico(), dto)));
    }

    @Override
    public TecnicoResponseDto atualizar(Long id, TecnicoRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(obter(id), dto)));
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
        repository.flush();
    }

    private Tecnico obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tecnico não encontrado: " + id));
    }

    private Tecnico preencher(Tecnico e, TecnicoRequestDto dto) {
        e.setNome(dto.getNome());
        e.setEmail(dto.getEmail());
        e.setEspecialidade(dto.getEspecialidade());
        return e;
    }

    private TecnicoResponseDto paraDto(Tecnico e) {
        return new TecnicoResponseDto(e.getId(), e.getNome(), e.getEmail(), e.getEspecialidade());
    }
}
