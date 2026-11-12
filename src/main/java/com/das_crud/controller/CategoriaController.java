package com.das_crud.controller;

import com.das_crud.dto.CategoriaRequestDto;
import com.das_crud.interfaces.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private static final String FORM = "categorias/form";

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", service.listar());
        return "categorias/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new CategoriaRequestDto());
        return FORM;
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("form") CategoriaRequestDto form,
                         BindingResult result, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.salvar(form);
                ra.addFlashAttribute("sucesso", "Categoria cadastrada com sucesso.");
                return "redirect:/categorias";
            } catch (DataIntegrityViolationException e) {
                result.reject("duplicado", "Já existe um registro com esses dados.");
            }
        }
        return FORM;
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var atual = service.buscar(id);
        var form = new CategoriaRequestDto();
        form.setNome(atual.nome());
        form.setDescricao(atual.descricao());
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        return FORM;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("form") CategoriaRequestDto form,
                            BindingResult result, Model model, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.atualizar(id, form);
                ra.addFlashAttribute("sucesso", "Categoria atualizada com sucesso.");
                return "redirect:/categorias";
            } catch (DataIntegrityViolationException e) {
                result.reject("duplicado", "Já existe um registro com esses dados.");
            }
        }
        model.addAttribute("id", id);
        return FORM;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Categoria excluída com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: existem chamados nesta categoria.");
        }
        return "redirect:/categorias";
    }
}
