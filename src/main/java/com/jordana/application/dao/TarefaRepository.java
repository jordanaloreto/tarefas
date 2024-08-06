package com.jordana.application.dao;

import com.jordana.application.model.Tarefas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaRepository {

    private static final Logger logger = LoggerFactory.getLogger(TarefaRepository.class);
    private final Connection connection;

    public TarefaRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean saveTarefa(Tarefas tarefa) {
        String sql = "INSERT INTO Tarefas (categoria_id, responsavel_id, descricao_tarefa, data_tarefa, prioridade, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tarefa.getCategoriaId());
            stmt.setInt(2, tarefa.getResponsavelId());
            stmt.setString(3, tarefa.getDescricao());
            stmt.setDate(4, Date.valueOf(tarefa.getDataTarefa()));
            stmt.setString(5, tarefa.getPrioridade());
            stmt.setString(6, tarefa.getStatus());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Tarefa salva com sucesso: {}", tarefa);
                return true;
            } else {
                logger.warn("Nenhuma tarefa foi inserida: {}", tarefa);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Erro ao salvar a tarefa: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean updateTarefa(Tarefas tarefa) {
        String sql = "UPDATE Tarefas SET categoria_id = ?, responsavel_id = ?, descricao_tarefa = ?, data_tarefa = ?, prioridade = ?, status = ? " +
                     "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tarefa.getCategoriaId());
            stmt.setInt(2, tarefa.getResponsavelId());
            stmt.setString(3, tarefa.getDescricao());
            stmt.setDate(4, Date.valueOf(tarefa.getDataTarefa()));
            stmt.setString(5, tarefa.getPrioridade());
            stmt.setString(6, tarefa.getStatus());
            stmt.setInt(7, tarefa.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Tarefa atualizada com sucesso: {}", tarefa);
                return true;
            } else {
                logger.warn("Nenhuma tarefa foi atualizada: {}", tarefa);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar a tarefa: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteTarefa(Tarefas tarefa) {
        String sql = "DELETE FROM Tarefas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tarefa.getId());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("Tarefa deletada com sucesso: {}", tarefa);
                return true;
            } else {
                logger.warn("Nenhuma tarefa foi deletada: {}", tarefa);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Erro ao deletar a tarefa: {}", e.getMessage(), e);
            return false;
        }
    }

    public List<Tarefas> getAllTarefas() {
        String sql = "SELECT t.id, t.descricao_tarefa, t.data_tarefa, t.prioridade, t.status, t.categoria_id, t.responsavel_id, " +
                     "c.descricao AS categoria_descricao, r.nome AS responsavel_nome " +
                     "FROM Tarefas t " +
                     "JOIN Categoria c ON t.categoria_id = c.id " +
                     "JOIN Responsaveis r ON t.responsavel_id = r.id";
        List<Tarefas> tarefas = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarefas tarefa = new Tarefas(
                    rs.getString("descricao_tarefa"),
                    rs.getDate("data_tarefa").toLocalDate(),
                    rs.getString("prioridade"),
                    rs.getString("status"),
                    rs.getInt("categoria_id"),
                    rs.getInt("responsavel_id")
                );
                tarefa.setId(rs.getInt("id"));
                tarefas.add(tarefa);
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar todas as tarefas: {}", e.getMessage(), e);
        }

        return tarefas;
    }
}
