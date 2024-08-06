package com.jordana.application.controller;

import com.jordana.application.dao.CategoriaRepository;
import com.jordana.application.dao.ResponsaveisRepository;
import com.jordana.application.dao.TarefaRepository;
import com.jordana.application.model.Tarefas;

import java.time.LocalDate;
import java.util.List;

public class TarefaController {

    private final CategoriaRepository categoriaRepository;
    private final ResponsaveisRepository responsaveisRepository;
    private final TarefaRepository tarefaRepository;

    public TarefaController() {
        try {
            this.categoriaRepository = new CategoriaRepository();
            this.responsaveisRepository = new ResponsaveisRepository();
            this.tarefaRepository = new TarefaRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar o controlador de tarefas", e);
        }
    }

    public List<String> getAllCategorias() {
        return categoriaRepository.getAllDescricoes();
    }

    public List<String> getAllResponsaveis() {
        return responsaveisRepository.getAllNomes();
    }

    public boolean saveTarefa(String categoria, String responsavel, String descricao, String data,
                              String prioridade, String status) {
        int categoriaId = getCategoriaIdByDescricao(categoria);
        int responsavelId = getResponsavelIdByNome(responsavel);
        Tarefas tarefa = new Tarefas(descricao, LocalDate.parse(data), prioridade, status, categoriaId, responsavelId);
        return tarefaRepository.saveTarefa(tarefa);
    }

    public boolean updateTarefa(Tarefas tarefa) {
        return tarefaRepository.updateTarefa(tarefa);
    }

    public List<Tarefas> getAllTarefas() {
        return tarefaRepository.getAllTarefas();
    }

    public boolean deleteTarefa(Tarefas tarefa) {
        return tarefaRepository.deleteTarefa(tarefa);
    }

    public int getCategoriaIdByDescricao(String descricao) {
        return categoriaRepository.getIdByDescricao(descricao);
    }

    public int getResponsavelIdByNome(String nome) {
        return responsaveisRepository.getIdByNome(nome);
    }

    public String getCategoriaDescricaoById(int id) {
        return categoriaRepository.getDescricaoById(id);
    }

    public String getResponsavelNomeById(int id) {
        return responsaveisRepository.getNomeById(id);
    }
}
