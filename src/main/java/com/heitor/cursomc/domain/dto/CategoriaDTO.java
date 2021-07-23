package com.heitor.cursomc.domain.dto;

import com.heitor.cursomc.domain.Categoria;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(max = 80, min = 5, message = "O tamanho deve conter entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO(){
    }

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
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
}
