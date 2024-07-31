package com.jordana.application.controller;

import com.jordana.application.model.Categoria;
import com.jordana.application.dao.CategoriaRepository;

import java.util.List;

public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController() {
        try {
            this.categoriaRepository = new CategoriaRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveCategoria(Categoria categoria) {
        try {
            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Categoria> getAllCategorias() {
        try {
            return categoriaRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCategoria(Categoria categoria) {
        try {
            return categoriaRepository.update(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategoria(Categoria categoria) {
        try {
            return categoriaRepository.delete(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
