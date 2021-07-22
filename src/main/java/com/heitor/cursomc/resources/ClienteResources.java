package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResources {

    private final ClienteService serviceCliente;

    @Autowired
    public ClienteResources(ClienteService serviceCliente){
        this.serviceCliente = serviceCliente;
    }

    @RequestMapping(value = "/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(value = "idCliente") Integer idCategoria){
        Cliente cliente = serviceCliente.buscar(idCategoria);
        return ResponseEntity.ok(cliente);
    }
}
