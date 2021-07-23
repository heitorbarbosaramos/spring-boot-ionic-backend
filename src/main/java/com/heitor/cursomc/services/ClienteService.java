package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.dto.ClienteDTO;
import com.heitor.cursomc.reposotories.ClienteRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    @Autowired
    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente buscar(Integer idCliente){
        Cliente obj = repo.findById(idCliente).orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrada", ClienteService.class.getName()));
        return obj;
    }

    public Cliente update(ClienteDTO clienteDTO){
        Cliente cliente = buscar(clienteDTO.getId());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        repo.save(cliente);
        return cliente;
    }

    public void delete(Integer idCliente){
        Cliente cliente = buscar(idCliente);
        try {
            repo.delete(cliente);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é possivel excluir cliente que tenha pedidos");
        }
    }
}
