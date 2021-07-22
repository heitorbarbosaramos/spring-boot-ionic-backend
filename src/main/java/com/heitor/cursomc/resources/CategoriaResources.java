package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
