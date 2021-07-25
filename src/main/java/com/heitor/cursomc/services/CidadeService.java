package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cidade;
import com.heitor.cursomc.reposotories.CidadeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CidadeService implements Serializable {

    private final CidadeRepository repo;

    @Autowired
    public CidadeService(CidadeRepository repo) {
        this.repo = repo;
    }

    public Cidade save(Cidade cidade){
        cidade = repo.save(cidade);
        return cidade;
    }

    public Cidade findById(Integer idCidade){
        Cidade cidade = repo.findById(idCidade).orElseThrow(()-> new ObjectNotFoundException("Não foi possivel encontrar a cidade pelo ID: " + idCidade, ClienteService.class.getName()));
        return cidade;
    }
}
