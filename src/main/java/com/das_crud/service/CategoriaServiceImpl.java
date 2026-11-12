package com.das_crud.service;

import com.das_crud.dto.CategoriaRequestDto;
import com.das_crud.dto.CategoriaResponseDto;
import com.das_crud.exception.RecursoNaoEncontradoException;
import com.das_crud.interfaces.CategoriaService;
import com.das_crud.model.Categoria;
import com.das_crud.repository.CategoriaRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaServiceImpl(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> listar() {
        return repository.findAll(Sort.by("nome")).stream().map(this::paraDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDto buscar(Long id) {
        return paraDto(obter(id));
    }

    @Override
    public CategoriaResponseDto salvar(CategoriaRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(new Categoria(), dto)));
    }

    @Override
    public CategoriaResponseDto atualizar(Long id, CategoriaRequestDto dto) {
        return paraDto(repository.saveAndFlush(preencher(obter(id), dto)));
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
        repository.flush();
    }

    private Categoria obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrado: " + id));
    }

    private Categoria preencher(Categoria e, CategoriaRequestDto dto) {
        e.setNome(dto.getNome());
        e.setDescricao(dto.getDescricao());
        return e;
    }

    private CategoriaResponseDto paraDto(Categoria e) {
        return new CategoriaResponseDto(e.getId(), e.getNome(), e.getDescricao());
    }
}
