package com.jordana.application.controller;

import com.jordana.application.model.Responsaveis;
import com.jordana.application.dao.ResponsaveisRepository;

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
}
