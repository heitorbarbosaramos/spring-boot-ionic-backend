package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Endereco;
import com.heitor.cursomc.reposotories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class EnderecoService implements Serializable {

    private final EnderecoRepository repo;

    @Autowired
    public EnderecoService(EnderecoRepository repo) {
        this.repo = repo;
    }

    public Endereco save(Endereco endereco){
        endereco = repo.save(endereco);
        return endereco;
    }
}
