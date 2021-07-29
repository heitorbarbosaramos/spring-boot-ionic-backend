package com.heitor.cursomc.domain.dto;

import com.heitor.cursomc.domain.Cidade;

import java.io.Serializable;

public class CidadeDTO implements Serializable, Comparable<CidadeDTO> {

    private Integer id;
    private String nome;

    public CidadeDTO(){
    }

    public CidadeDTO(Cidade cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
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

    @Override
    public int compareTo(CidadeDTO outro) {
        return nome.compareTo(outro.getNome());
    }
}
