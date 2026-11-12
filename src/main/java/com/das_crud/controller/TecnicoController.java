package com.das_crud.controller;

import com.das_crud.dto.TecnicoRequestDto;
import com.das_crud.interfaces.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {

    private static final String FORM = "tecnicos/form";

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", service.listar());
        return "tecnicos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new TecnicoRequestDto());
        return FORM;
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("form") TecnicoRequestDto form,
                         BindingResult result, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.salvar(form);
                ra.addFlashAttribute("sucesso", "Técnico cadastrado com sucesso.");
                return "redirect:/tecnicos";
            } catch (DataIntegrityViolationException e) {
                result.reject("duplicado", "Já existe um registro com esses dados.");
            }
        }
        return FORM;
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var atual = service.buscar(id);
        var form = new TecnicoRequestDto();
        form.setNome(atual.nome());
        form.setEmail(atual.email());
        form.setEspecialidade(atual.especialidade());
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        return FORM;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("form") TecnicoRequestDto form,
                            BindingResult result, Model model, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.atualizar(id, form);
                ra.addFlashAttribute("sucesso", "Técnico atualizado com sucesso.");
                return "redirect:/tecnicos";
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
            ra.addFlashAttribute("sucesso", "Técnico excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: este técnico possui chamados atribuídos.");
        }
        return "redirect:/tecnicos";
    }
}
