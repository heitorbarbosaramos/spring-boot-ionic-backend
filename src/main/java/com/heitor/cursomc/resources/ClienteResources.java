package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.dto.ClienteDTO;
import com.heitor.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResources {

    private final ClienteService serviceCliente;

    @Autowired
    public ClienteResources(ClienteService serviceCliente){
        this.serviceCliente = serviceCliente;
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction) {
        Page<Cliente> lista = serviceCliente.findPage(page,linesPerPage, orderBy, direction);
        Page<ClienteDTO> listaDto = lista.map(x -> new ClienteDTO(x));
        return ResponseEntity.ok(listaDto);
    }

    @RequestMapping(value = "/{idCliente}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable(value = "idCliente") Integer idCliente){
        Cliente cliente = serviceCliente.buscar(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @RequestMapping(value = "/{idCliente}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable(value = "idCliente") Integer idCliente, @Valid @RequestBody ClienteDTO clienteDTO){
       clienteDTO.setId(idCliente);
        Cliente cliente = serviceCliente.update(clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @RequestMapping(value = "/{idCliente}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "idCliente") Integer idCliente){
        serviceCliente.delete(idCliente);
        return ResponseEntity.noContent().build();
    }
}
