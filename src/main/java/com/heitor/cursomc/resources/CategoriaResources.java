package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.domain.dto.CategoriaDTO;
import com.heitor.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> insert(@Valid @RequestBody CategoriaDTO categoriaDto){
        Categoria categoria = serviceCategoria.insert(serviceCategoria.fromDto(categoriaDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{idCategoria}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable(value = "idCategoria") Integer idCategoria, @RequestBody Categoria categoria){
        categoria.setId(idCategoria);
        categoria = serviceCategoria.update(categoria);
        return ResponseEntity.ok(categoria);
    }

    @RequestMapping(value = "/{idCategoria}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "idCategoria") Integer idCategoria){
       serviceCategoria.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Categoria> lista = serviceCategoria.findAll();
        List<CategoriaDTO> listaDTO = lista.stream().map(x-> new CategoriaDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<?> findAllPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Categoria> lista = serviceCategoria.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listaDTO = lista.map(x-> new CategoriaDTO(x));
        return ResponseEntity.ok(listaDTO);
    }
}
