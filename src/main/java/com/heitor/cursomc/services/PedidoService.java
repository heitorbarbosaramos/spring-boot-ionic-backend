package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.reposotories.PedidoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository repo;

    @Autowired
    public PedidoService(PedidoRepository repo) {
        this.repo = repo;
    }

    public Pedido buscar(Integer idPedido){
        Pedido obj = repo.findById(idPedido).orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado pelo id" + idPedido, PedidoService.class.getName()));
        return obj;
    }
}
