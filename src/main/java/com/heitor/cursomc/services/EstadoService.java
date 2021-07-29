package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Estado;
import com.heitor.cursomc.reposotories.EstadoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private final EstadoRepository repo;

    @Autowired
    public EstadoService(EstadoRepository repo) {
        this.repo = repo;
    }

    public Estado save(Estado estado){
        estado = repo.save(estado);
        return estado;
    }

    public List<Estado> findAll(){
        List<Estado> estados = repo.findAll();
        return estados;
    }


    public Estado findById(Integer idEstado) {
        Estado estado = repo.findById(idEstado).orElseThrow(()-> new ObjectNotFoundException("Estado nao encontrado", EstadoService.class.getName()));
        return estado;
    }
}
