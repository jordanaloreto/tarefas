package com.jordana.application.model;

import java.util.Date;

public class Tarefas {

    private int id;
    private Date dataTarefa;
    private String descricaoTarefa;
    private String observacao;
    private String prioridade;
    private String status;
    private Categoria categoria;
    private Responsaveis responsavel;

    // Construtores
    public Tarefas() {}

    public Tarefas(int id, Date dataTarefa, String descricaoTarefa, String observacao, String prioridade, String status, Categoria categoria, Responsaveis responsavel) {
        this.id = id;
        this.dataTarefa = dataTarefa;
        this.descricaoTarefa = descricaoTarefa;
        this.observacao = observacao;
        this.prioridade = prioridade;
        this.status = status;
        this.categoria = categoria;
        this.responsavel = responsavel;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataTarefa() {
        return dataTarefa;
    }

    public void setDataTarefa(Date dataTarefa) {
        this.dataTarefa = dataTarefa;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Responsaveis getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsaveis responsavel) {
        this.responsavel = responsavel;
    }
}
