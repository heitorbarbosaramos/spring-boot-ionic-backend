package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    private final ClienteService serviceCliente;
    private final BCryptPasswordEncoder encoder;
    private final EmailService serviceEmail;
    private Random rand = new Random();

    @Autowired
    public AuthService(ClienteService serviceCliente, BCryptPasswordEncoder encoder, EmailService serviceEmail) {
        this.serviceCliente = serviceCliente;
        this.encoder = encoder;
        this.serviceEmail = serviceEmail;
    }

    public void sendNewPassword(String email){
        Cliente cliente = serviceCliente.findByEmail(email);
        String newPass = newPassword();
        cliente.setSenha(encoder.encode(newPass));
        serviceCliente.save(cliente);
        serviceEmail.sendNewPasswordEmail(cliente, newPass);

    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i<10 ; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { //gerar um digito
            return (char) (rand.nextInt(10) + 48);
        }else if(opt == 1){ //gerar uma letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }else{ // gerar uma letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }

    }
}
