package com.jordana.application.model;

import java.time.LocalDate;

public class Tarefas {

    private String descricao;
    private LocalDate dataTarefa;
    private String prioridade;
    private String status;
    private int categoriaId;
    private int responsavelId;

    public Tarefas(String descricao, LocalDate dataTarefa, String prioridade, String status, int categoriaId, int responsavelId) {
        this.descricao = descricao;
        this.dataTarefa = dataTarefa;
        this.prioridade = prioridade;
        this.status = status;
        this.categoriaId = categoriaId;
        this.responsavelId = responsavelId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataTarefa() {
        return dataTarefa;
    }

    public void setDataTarefa(LocalDate dataTarefa) {
        this.dataTarefa = dataTarefa;
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

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(int responsavelId) {
        this.responsavelId = responsavelId;
    }
}
