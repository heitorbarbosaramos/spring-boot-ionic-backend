package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService servicePedido;

    @Autowired
    public PedidoResource(PedidoService servicePedido) {
        this.servicePedido = servicePedido;
    }

    @RequestMapping(value = "/{idPedido}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(name = "idPedido") Integer idPedido){
        Pedido obj = servicePedido.buscar(idPedido);
        return ResponseEntity.ok(obj);
    }
}
