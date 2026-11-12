package com.das_crud.service;

import com.das_crud.dto.ComentarioRequestDto;
import com.das_crud.dto.ComentarioResponseDto;
import com.das_crud.exception.RecursoNaoEncontradoException;
import com.das_crud.interfaces.ComentarioService;
import com.das_crud.model.Chamado;
import com.das_crud.model.Comentario;
import com.das_crud.repository.ChamadoRepository;
import com.das_crud.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository repository;
    private final ChamadoRepository chamadoRepository;

    public ComentarioServiceImpl(ComentarioRepository repository, ChamadoRepository chamadoRepository) {
        this.repository = repository;
        this.chamadoRepository = chamadoRepository;
    }

    @Override
    public ComentarioResponseDto adicionar(ComentarioRequestDto dto) {
        Chamado chamado = chamadoRepository.findById(dto.getChamadoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado: " + dto.getChamadoId()));
        Comentario comentario = new Comentario();
        comentario.setAutor(dto.getAutor());
        comentario.setTexto(dto.getTexto());
        comentario.setChamado(chamado);
        comentario = repository.saveAndFlush(comentario);
        return new ComentarioResponseDto(comentario.getId(), comentario.getAutor(),
                comentario.getTexto(), comentario.getDataCriacao());
    }

    @Override
    public Long excluir(Long id) {
        Comentario comentario = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comentário não encontrado: " + id));
        Chamado chamado = comentario.getChamado();
        chamado.getComentarios().remove(comentario); // orphanRemoval apaga o registro
        chamadoRepository.flush();
        return chamado.getId();
    }
}
