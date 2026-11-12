package com.das_crud.controller;

import com.das_crud.dto.ComentarioRequestDto;
import com.das_crud.interfaces.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ComentarioController {

    private final ComentarioService service;

    public ComentarioController(ComentarioService service) {
        this.service = service;
    }

    @PostMapping("/chamados/{chamadoId}/comentarios")
    public String adicionar(@PathVariable Long chamadoId,
                            @Valid @ModelAttribute("comentario") ComentarioRequestDto form,
                            BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("erro", "Informe o autor e o texto do comentário.");
        } else {
            form.setChamadoId(chamadoId);
            service.adicionar(form);
            ra.addFlashAttribute("sucesso", "Comentário adicionado.");
        }
        return "redirect:/chamados/" + chamadoId;
    }

    @PostMapping("/comentarios/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        Long chamadoId = service.excluir(id);
        ra.addFlashAttribute("sucesso", "Comentário removido.");
        return "redirect:/chamados/" + chamadoId;
    }
}
