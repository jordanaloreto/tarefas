package com.jordana.application.controller;

import com.jordana.application.dao.CategoriaRepository;
import com.jordana.application.dao.ResponsaveisRepository;
import com.jordana.application.dao.TarefaRepository;

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

    public boolean saveTarefa(String categoria, String responsavel, String tarefa, String data,
                              String prioridade, String status) {
        return tarefaRepository.saveTarefa(categoria, responsavel, tarefa, data, prioridade, status);
    }
}
