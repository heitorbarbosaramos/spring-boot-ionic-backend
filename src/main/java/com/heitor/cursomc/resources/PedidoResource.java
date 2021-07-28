package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.domain.dto.CategoriaDTO;
import com.heitor.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService servicePedido;

    @Autowired
    public PedidoResource(PedidoService servicePedido) {
        this.servicePedido = servicePedido;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@Valid @RequestBody Pedido pedido){
        pedido = servicePedido.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{idPedido}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(name = "idPedido") Integer idPedido){
        Pedido obj = servicePedido.buscar(idPedido);
        return ResponseEntity.ok(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPagem,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ){
        Page<Pedido> list = servicePedido.findPage(page, linesPerPagem, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
