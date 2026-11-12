package com.das_crud.repository;

import com.das_crud.model.Chamado;
import com.das_crud.model.StatusChamado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findAllByOrderByDataAberturaDesc();

    List<Chamado> findByStatusOrderByDataAberturaDesc(StatusChamado status);

    long countByStatus(StatusChamado status);
}
