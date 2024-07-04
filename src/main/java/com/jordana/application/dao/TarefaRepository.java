package com.jordana.application.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarefaRepository {

    private static final Logger logger = LoggerFactory.getLogger(TarefaRepository.class);
    private final Connection connection;

    public TarefaRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean saveTarefa(String categoria, String responsavel, String descricao, String data,
                              String prioridade, String status) {
        String sql = "INSERT INTO Tarefas (categoria_id, responsavel_id, descricao_tarefa, data_tarefa, prioridade, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int categoriaId = getCategoriaIdByDescricao(categoria);
            int responsavelId = getResponsavelIdByNome(responsavel);
            Date sqlDate = Date.valueOf(data);

            stmt.setInt(1, categoriaId);
            stmt.setInt(2, responsavelId);
            stmt.setString(3, descricao);
            stmt.setDate(4, sqlDate);
            stmt.setString(5, prioridade);
            stmt.setString(6, status);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Tarefa salva com sucesso: {}, {}, {}, {}, {}, {}", categoria, responsavel, descricao, data, prioridade, status);
                return true;
            } else {
                logger.warn("Nenhuma tarefa foi inserida: {}, {}, {}, {}, {}, {}", categoria, responsavel, descricao, data, prioridade, status);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Erro ao salvar a tarefa: {}", e.getMessage(), e);
            return false;
        }
    }

    private int getCategoriaIdByDescricao(String descricao) throws SQLException {
        String sql = "SELECT id FROM Categoria WHERE descricao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
            throw new SQLException("Categoria não encontrada: " + descricao);
        }
    }

    private int getResponsavelIdByNome(String nome) throws SQLException {
        String sql = "SELECT id FROM Responsaveis WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
            throw new SQLException("Responsável não encontrado: " + nome);
        }
    }
}
