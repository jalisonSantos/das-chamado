package com.das_crud.config;

import com.das_crud.model.*;
import com.das_crud.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Popula o banco H2 (em memória) com dados de exemplo para facilitar o teste das telas. */
@Configuration
public class DadosIniciais {

    @Bean
    CommandLineRunner carregarDados(CategoriaRepository categorias, ClienteRepository clientes,
                                    TecnicoRepository tecnicos, ChamadoRepository chamados,
                                    ComentarioRepository comentarios) {
        return args -> {
            if (categorias.count() > 0) {
                return;
            }
            Categoria rede = categoria(categorias, "Rede", "Problemas de conectividade");
            Categoria software = categoria(categorias, "Software", "Instalação e erros de aplicativos");
            categoria(categorias, "Hardware", "Equipamentos e periféricos");

            Cliente maria = cliente(clientes, "Jalison Santos", "jalison123@gmail.com", "(47) 99999-0001");
            Cliente joao = cliente(clientes, "Lucas Costa", "lucas123@gmail.com", "(47) 99999-0002");

            Tecnico ana = tecnico(tecnicos, "Felipe nocce", "felipe123@gmail.com", "Redes");
            tecnico(tecnicos, "Victor", "victor@gmail.com", "Sistemas");

            Chamado c1 = chamado(chamados, "Sem acesso à internet", "O computador não conecta na rede da empresa.",
                    StatusChamado.EM_ANDAMENTO, Prioridade.ALTA, rede, maria, ana);
            chamado(chamados, "Erro ao abrir o sistema", "O sistema fecha sozinho logo após o login.",
                    StatusChamado.ABERTO, Prioridade.MEDIA, software, joao, null);

            Comentario m = new Comentario();
            m.setAutor("Ana Costa");
            m.setTexto("Verifiquei o cabo e o switch, continuo investigando.");
            m.setChamado(c1);
            comentarios.save(m);
        };
    }

    private Categoria categoria(CategoriaRepository r, String nome, String descricao) {
        Categoria c = new Categoria();
        c.setNome(nome);
        c.setDescricao(descricao);
        return r.save(c);
    }

    private Cliente cliente(ClienteRepository r, String nome, String email, String telefone) {
        Cliente c = new Cliente();
        c.setNome(nome);
        c.setEmail(email);
        c.setTelefone(telefone);
        return r.save(c);
    }

    private Tecnico tecnico(TecnicoRepository r, String nome, String email, String especialidade) {
        Tecnico t = new Tecnico();
        t.setNome(nome);
        t.setEmail(email);
        t.setEspecialidade(especialidade);
        return r.save(t);
    }

    private Chamado chamado(ChamadoRepository r, String titulo, String descricao, StatusChamado status,
                            Prioridade prioridade, Categoria categoria, Cliente cliente, Tecnico tecnico) {
        Chamado c = new Chamado();
        c.setTitulo(titulo);
        c.setDescricao(descricao);
        c.setStatus(status);
        c.setPrioridade(prioridade);
        c.setCategoria(categoria);
        c.setCliente(cliente);
        c.setTecnico(tecnico);
        return r.save(c);
    }
}
