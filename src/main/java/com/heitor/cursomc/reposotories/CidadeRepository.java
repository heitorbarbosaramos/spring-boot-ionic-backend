package com.heitor.cursomc.reposotories;

import com.heitor.cursomc.domain.Cidade;
import com.heitor.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("select c from Cidade as c where c.estado = :estado")
    List<Cidade> findByEstadoOrderByNome(@Param(value = "estado") Estado estado);

}
