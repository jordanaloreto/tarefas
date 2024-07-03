package com.jordana.application.dao;

import com.jordana.application.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarefaRepository {

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

            stmt.setInt(1, categoriaId);
            stmt.setInt(2, responsavelId);
            stmt.setString(3, descricao);
            stmt.setString(4, data);
            stmt.setString(5, prioridade);
            stmt.setString(6, status);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
