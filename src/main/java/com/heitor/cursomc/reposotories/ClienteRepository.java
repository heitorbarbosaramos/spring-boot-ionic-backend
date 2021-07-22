package com.heitor.cursomc.reposotories;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
