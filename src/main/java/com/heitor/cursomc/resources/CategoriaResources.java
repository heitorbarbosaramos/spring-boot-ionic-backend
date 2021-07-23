package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

    private final CategoriaService serviceCategoria;

    @Autowired
    public CategoriaResources(CategoriaService serviceCategoria){
        this.serviceCategoria = serviceCategoria;
    }

    @RequestMapping(value = "/{idCategoria}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(value = "idCategoria") Integer idCategoria){
        Categoria categoria = serviceCategoria.buscar(idCategoria);
        return ResponseEntity.ok(categoria);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody Categoria categoria){
        categoria = serviceCategoria.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{idCategoria}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable(value = "idCategoria") Integer idCategoria, @RequestBody Categoria categoria){
        categoria.setId(idCategoria);
        categoria = serviceCategoria.update(categoria);
        return ResponseEntity.ok(categoria);
    }
}
