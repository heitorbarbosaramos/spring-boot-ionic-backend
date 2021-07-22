package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.reposotories.ClienteRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    @Autowired
    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente buscar(Integer idCliente){
        Cliente obj = repo.findById(idCliente).orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrada", ClienteService.class.getName()));
        return obj;
    }
}
