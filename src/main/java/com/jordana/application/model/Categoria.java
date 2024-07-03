package com.jordana.application.model;

import java.io.Serializable;

public class Categoria implements Serializable {

    private int id;
    private String descricao;

    // Construtores
    public Categoria() {}

    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
