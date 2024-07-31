package com.jordana.application.controller;

import com.jordana.application.model.Responsaveis;
import com.jordana.application.dao.ResponsaveisRepository;

import java.util.List;

public class ResponsaveisController {

    private final ResponsaveisRepository responsaveisRepository;

    public ResponsaveisController() {
        try {
            this.responsaveisRepository = new ResponsaveisRepository();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean saveResponsavel(Responsaveis responsavel) {
        try {
            return responsaveisRepository.save(responsavel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Responsaveis> getAllResponsaveis() {
        try {
            return responsaveisRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateResponsavel(Responsaveis responsavel) {
        try {
            return responsaveisRepository.update(responsavel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteResponsavel(Responsaveis responsavel) {
        try {
            return responsaveisRepository.delete(responsavel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
