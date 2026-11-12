package com.das_crud.service;

import com.das_crud.dto.ChamadoRequestDto;
import com.das_crud.dto.ChamadoResponseDto;
import com.das_crud.dto.ComentarioResponseDto;
import com.das_crud.exception.RecursoNaoEncontradoException;
import com.das_crud.interfaces.ChamadoService;
import com.das_crud.model.Chamado;
import com.das_crud.model.StatusChamado;
import com.das_crud.repository.CategoriaRepository;
import com.das_crud.repository.ChamadoRepository;
import com.das_crud.repository.ClienteRepository;
import com.das_crud.repository.TecnicoRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChamadoServiceImpl implements ChamadoService {

    private final ChamadoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;

    public ChamadoServiceImpl(ChamadoRepository repository, CategoriaRepository categoriaRepository,
                              ClienteRepository clienteRepository, TecnicoRepository tecnicoRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChamadoResponseDto> listar(StatusChamado status) {
        List<Chamado> chamados = status == null
                ? repository.findAllByOrderByDataAberturaDesc()
                : repository.findByStatusOrderByDataAberturaDesc(status);
        return chamados.stream().map(this::paraDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ChamadoResponseDto buscar(Long id) {
        return paraDto(obter(id));
    }

    @Override
    public ChamadoResponseDto salvar(ChamadoRequestDto dto) {
        Chamado chamado = new Chamado();
        preencher(chamado, dto);
        return paraDto(repository.saveAndFlush(chamado));
    }

    @Override
    public ChamadoResponseDto atualizar(Long id, ChamadoRequestDto dto) {
        Chamado chamado = obter(id);
        preencher(chamado, dto);
        return paraDto(repository.saveAndFlush(chamado));
    }

    @Override
    public void alterarStatus(Long id, StatusChamado status) {
        obter(id).setStatus(status);
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<StatusChamado, Long> contarPorStatus() {
        Map<StatusChamado, Long> totais = new EnumMap<>(StatusChamado.class);
        for (StatusChamado s : StatusChamado.values()) {
            totais.put(s, repository.countByStatus(s));
        }
        return totais;
    }

    private Chamado obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado: " + id));
    }

    private void preencher(Chamado c, ChamadoRequestDto dto) {
        c.setTitulo(dto.getTitulo());
        c.setDescricao(dto.getDescricao());
        c.setPrioridade(dto.getPrioridade());
        c.setStatus(dto.getStatus());
        c.setCategoria(categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada")));
        c.setCliente(clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado")));
        c.setTecnico(dto.getTecnicoId() == null ? null : tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Técnico não encontrado")));
    }

    private ChamadoResponseDto paraDto(Chamado c) {
        List<ComentarioResponseDto> comentarios = c.getComentarios().stream()
                .map(m -> new ComentarioResponseDto(m.getId(), m.getAutor(), m.getTexto(), m.getDataCriacao()))
                .toList();
        return new ChamadoResponseDto(
                c.getId(), c.getTitulo(), c.getDescricao(), c.getStatus(), c.getPrioridade(), c.getDataAbertura(),
                c.getCategoria().getId(), c.getCategoria().getNome(),
                c.getCliente().getId(), c.getCliente().getNome(),
                c.getTecnico() == null ? null : c.getTecnico().getId(),
                c.getTecnico() == null ? null : c.getTecnico().getNome(),
                comentarios);
    }
}
