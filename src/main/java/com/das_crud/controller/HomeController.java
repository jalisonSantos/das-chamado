package com.das_crud.controller;

import com.das_crud.dto.ChamadoResponseDto;
import com.das_crud.interfaces.CategoriaService;
import com.das_crud.interfaces.ChamadoService;
import com.das_crud.interfaces.ClienteService;
import com.das_crud.interfaces.TecnicoService;
import com.das_crud.model.StatusChamado;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ChamadoService chamadoService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;
    private final CategoriaService categoriaService;

    public HomeController(ChamadoService chamadoService, ClienteService clienteService,
                          TecnicoService tecnicoService, CategoriaService categoriaService) {
        this.chamadoService = chamadoService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ChamadoResponseDto> todos = chamadoService.listar(null);
        model.addAttribute("statusList", StatusChamado.values());
        model.addAttribute("porStatus", chamadoService.contarPorStatus());
        model.addAttribute("recentes", todos.stream().limit(5).toList());
        model.addAttribute("totalChamados", todos.size());
        model.addAttribute("totalClientes", clienteService.listar().size());
        model.addAttribute("totalTecnicos", tecnicoService.listar().size());
        model.addAttribute("totalCategorias", categoriaService.listar().size());
        return "index";
    }
}
