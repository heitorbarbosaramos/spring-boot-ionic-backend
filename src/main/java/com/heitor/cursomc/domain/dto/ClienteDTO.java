package com.heitor.cursomc.domain.dto;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {

    private Integer id;
    @NotEmpty(message = "preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "nome deve conter de 4 e 120 caracteres")
    private String nome;
    @Email(message = "email invalido")
    private String email;

    public ClienteDTO(){
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
