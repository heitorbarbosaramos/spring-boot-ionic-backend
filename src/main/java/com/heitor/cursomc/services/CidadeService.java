package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cidade;
import com.heitor.cursomc.domain.Estado;
import com.heitor.cursomc.domain.dto.CidadeDTO;
import com.heitor.cursomc.reposotories.CidadeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService implements Serializable {

    private final CidadeRepository repo;

    private final EstadoService serviceEstado;

    @Autowired
    public CidadeService(CidadeRepository repo, EstadoService serviceEstado) {
        this.repo = repo;
        this.serviceEstado = serviceEstado;
    }

    public Cidade save(Cidade cidade){
        cidade = repo.save(cidade);
        return cidade;
    }

    public Cidade findById(Integer idCidade){
        Cidade cidade = repo.findById(idCidade).orElseThrow(()-> new ObjectNotFoundException("Não foi possivel encontrar a cidade pelo ID: " + idCidade, ClienteService.class.getName()));
        return cidade;
    }

    public List<CidadeDTO> findByEstado(Integer idEstado){
        Estado estado = serviceEstado.findById(idEstado);
        List<CidadeDTO> cidades = estado.getCidades().stream().map(x-> new CidadeDTO(x)).collect(Collectors.toList());
        Collections.sort(cidades);
        return cidades;
    }
}
