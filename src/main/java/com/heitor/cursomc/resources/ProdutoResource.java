package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.domain.Produto;
import com.heitor.cursomc.domain.dto.CategoriaDTO;
import com.heitor.cursomc.domain.dto.ProdutoDTO;
import com.heitor.cursomc.resources.utils.URL;
import com.heitor.cursomc.services.PedidoService;
import com.heitor.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    private final ProdutoService serviceProduto;

    @Autowired
    public ProdutoResource(ProdutoService serviceProduto) {
        this.serviceProduto = serviceProduto;
    }

    @RequestMapping(value = "/{idProduto}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(name = "idProduto") Integer idProduto){
        Produto obj = serviceProduto.buscar(idProduto);
        return ResponseEntity.ok(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){

        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecode = URL.decodeParam(nome);
        Page<Produto> lista = serviceProduto.search(nomeDecode, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listaDTO = lista.map(x-> new ProdutoDTO(x));
        return ResponseEntity.ok(listaDTO);
    }
}
