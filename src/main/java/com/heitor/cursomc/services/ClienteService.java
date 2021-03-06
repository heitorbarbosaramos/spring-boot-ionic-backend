package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cidade;
import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.Endereco;
import com.heitor.cursomc.domain.dto.ClienteDTO;
import com.heitor.cursomc.domain.dto.ClienteNewDTO;
import com.heitor.cursomc.enums.Perfil;
import com.heitor.cursomc.enums.TipoCliente;
import com.heitor.cursomc.reposotories.ClienteRepository;
import com.heitor.cursomc.security.UserSecuritySpring;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    private final EnderecoService serviceEndereco;

    private final CidadeService serviceCidade;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public ClienteService(ClienteRepository repo, EnderecoService serviceEndereco, CidadeService serviceCidade) {
        this.repo = repo;
        this.serviceEndereco = serviceEndereco;
        this.serviceCidade = serviceCidade;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente save(Cliente cliente){
        cliente = repo.save(cliente);
        return cliente;
    }

    @Transactional
    public Cliente insert(ClienteNewDTO clienteNewDTO){
        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), encoder.encode(clienteNewDTO.getSenha()));
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if(clienteNewDTO.getTelefone2() != null){
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if(clienteNewDTO.getTelefone3() != null){
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }

        cliente = repo.save(cliente);

        Cidade cidade = serviceCidade.findById(clienteNewDTO.getCidadeId());
        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);

        serviceEndereco.save(endereco);
        cliente = repo.save(cliente);
        return cliente;
    }

    public Cliente buscar(Integer idCliente){

        UserSecuritySpring userSecuritySpring = UserService.authenticated();
        if(userSecuritySpring == null || !userSecuritySpring.hasRole(Perfil.ADMIN) && !idCliente.equals(userSecuritySpring.getId())){
            throw new AuthorizationServiceException("Acesso negado");
        }

        Cliente obj = repo.findById(idCliente).orElseThrow(()-> new ObjectNotFoundException("Cliente n??o encontrada", ClienteService.class.getName()));
        return obj;
    }

    public Cliente findByEmail(String email){

        UserSecuritySpring userSecuritySpring = UserService.authenticated();
        System.out.println(userSecuritySpring.getUsername());
        System.out.println(userSecuritySpring.getAuthorities());
        if(userSecuritySpring == null || !userSecuritySpring.hasRole(Perfil.ADMIN) && !email.equals(userSecuritySpring.getUsername())){
            throw new AuthorizationServiceException("Acesso negado");
        }

        Cliente cliente = repo.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Cliente n??o encontrado pelo email: " + email, ClienteService.class.getName());
        }
        return cliente;
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
            throw new DataIntegrityViolationException("N??o ?? possivel excluir cliente que tenha pedidos");
        }
    }
}
