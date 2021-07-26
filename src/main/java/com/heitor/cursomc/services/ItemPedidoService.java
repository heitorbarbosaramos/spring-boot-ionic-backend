package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.ItemPedido;
import com.heitor.cursomc.reposotories.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repo;

    public ItemPedidoService(ItemPedidoRepository repo) {
        this.repo = repo;
    }

    public ItemPedido save(ItemPedido itemPedido){
        itemPedido = repo.save(itemPedido);
        return itemPedido;
    }

    public Set<ItemPedido> save(Set<ItemPedido> itens){
        for(ItemPedido x : itens){
            repo.save(x);
        }
        return itens;
    }
}
