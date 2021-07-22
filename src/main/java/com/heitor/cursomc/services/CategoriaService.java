package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.exceptions.NotFound;
import com.heitor.cursomc.reposotories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    @Autowired
    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public Categoria buscar(Integer idCategoria){
        Categoria obj = repo.findById(idCategoria).orElseThrow(()-> new NotFound("Categoria não existe"));
        return obj;
    }
}
