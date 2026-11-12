package com.das_crud.controller;

import com.das_crud.dto.ClienteRequestDto;
import com.das_crud.interfaces.ClienteService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private static final String FORM = "clientes/form";

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", service.listar());
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new ClienteRequestDto());
        return FORM;
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("form") ClienteRequestDto form,
                         BindingResult result, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.salvar(form);
                ra.addFlashAttribute("sucesso", "Cliente cadastrado com sucesso.");
                return "redirect:/clientes";
            } catch (DataIntegrityViolationException e) {
                result.reject("duplicado", "Já existe um registro com esses dados.");
            }
        }
        return FORM;
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var atual = service.buscar(id);
        var form = new ClienteRequestDto();
        form.setNome(atual.nome());
        form.setEmail(atual.email());
        form.setTelefone(atual.telefone());
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        return FORM;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("form") ClienteRequestDto form,
                            BindingResult result, Model model, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            try {
                service.atualizar(id, form);
                ra.addFlashAttribute("sucesso", "Cliente atualizado com sucesso.");
                return "redirect:/clientes";
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
            ra.addFlashAttribute("sucesso", "Cliente excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro", "Não é possível excluir: este cliente possui chamados.");
        }
        return "redirect:/clientes";
    }
}
