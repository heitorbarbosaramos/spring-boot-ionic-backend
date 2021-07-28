package com.heitor.cursomc.security;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.reposotories.ClienteRepository;
import com.heitor.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDataisServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository serviceCliente;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cliente = serviceCliente.findByEmail(email);

        if(cliente == null){throw new UsernameNotFoundException(email);}
        UserSecuritySpring userSecuritySpring = new UserSecuritySpring(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfil());
        return userSecuritySpring;
    }
}
