package com.heitor.cursomc.reposotories;

import com.heitor.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Cidade, Integer> {
}
