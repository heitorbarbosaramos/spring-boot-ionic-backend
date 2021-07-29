package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Cidade;
import com.heitor.cursomc.domain.Estado;
import com.heitor.cursomc.domain.dto.CidadeDTO;
import com.heitor.cursomc.services.CidadeService;
import com.heitor.cursomc.services.EstadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResources {

    private final EstadoService serviceEstado;

    private final CidadeService serviceCidade;

    public EstadoResources(EstadoService serviceEstado, CidadeService serviceCidade) {
        this.serviceEstado = serviceEstado;
        this.serviceCidade = serviceCidade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllEstados(){
        List<Estado> estados = serviceEstado.findAll();
        return ResponseEntity.ok().body(estados);
    }

    @RequestMapping(value = "/{estado_id}/cidades", method = RequestMethod.GET)
    public ResponseEntity<?> findAllCidadeByEstado(@PathVariable(value = "estado_id") Integer estado_id){
        List<CidadeDTO> cidades = serviceCidade.findByEstado(estado_id);
        return ResponseEntity.ok().body(cidades);
    }
}
