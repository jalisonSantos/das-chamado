package com.das_crud.controller;

import com.das_crud.dto.ChamadoRequestDto;
import com.das_crud.dto.ChamadoResponseDto;
import com.das_crud.dto.ComentarioRequestDto;
import com.das_crud.interfaces.CategoriaService;
import com.das_crud.interfaces.ChamadoService;
import com.das_crud.interfaces.ClienteService;
import com.das_crud.interfaces.TecnicoService;
import com.das_crud.model.Prioridade;
import com.das_crud.model.StatusChamado;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

    private static final String FORM = "chamados/form";

    private final ChamadoService service;
    private final CategoriaService categoriaService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public ChamadoController(ChamadoService service, CategoriaService categoriaService,
                             ClienteService clienteService, TecnicoService tecnicoService) {
        this.service = service;
        this.categoriaService = categoriaService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) StatusChamado status, Model model) {
        model.addAttribute("itens", service.listar(status));
        model.addAttribute("filtro", status);
        model.addAttribute("statusList", StatusChamado.values());
        return "chamados/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("chamado", service.buscar(id));
        model.addAttribute("statusList", StatusChamado.values());
        if (!model.containsAttribute("comentario")) {
            model.addAttribute("comentario", new ComentarioRequestDto());
        }
        return "chamados/detalhe";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new ChamadoRequestDto());
        carregarOpcoes(model);
        return FORM;
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("form") ChamadoRequestDto form,
                         BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            carregarOpcoes(model);
            return FORM;
        }
        ChamadoResponseDto criado = service.salvar(form);
        ra.addFlashAttribute("sucesso", "Chamado #" + criado.id() + " aberto com sucesso.");
        return "redirect:/chamados/" + criado.id();
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        ChamadoResponseDto atual = service.buscar(id);
        ChamadoRequestDto form = new ChamadoRequestDto();
        form.setTitulo(atual.titulo());
        form.setDescricao(atual.descricao());
        form.setPrioridade(atual.prioridade());
        form.setStatus(atual.status());
        form.setCategoriaId(atual.categoriaId());
        form.setClienteId(atual.clienteId());
        form.setTecnicoId(atual.tecnicoId());
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        carregarOpcoes(model);
        return FORM;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("form") ChamadoRequestDto form,
                            BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            carregarOpcoes(model);
            return FORM;
        }
        service.atualizar(id, form);
        ra.addFlashAttribute("sucesso", "Chamado atualizado com sucesso.");
        return "redirect:/chamados/" + id;
    }

    @PostMapping("/{id}/status")
    public String alterarStatus(@PathVariable Long id, @RequestParam StatusChamado status, RedirectAttributes ra) {
        service.alterarStatus(id, status);
        ra.addFlashAttribute("sucesso", "Status alterado para " + status.getRotulo() + ".");
        return "redirect:/chamados/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id);
        ra.addFlashAttribute("sucesso", "Chamado excluído com sucesso.");
        return "redirect:/chamados";
    }

    private void carregarOpcoes(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("tecnicos", tecnicoService.listar());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("statusList", StatusChamado.values());
    }
}
