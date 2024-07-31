package com.jordana.application.dao;

import com.jordana.application.model.Responsaveis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ResponsaveisRepository {
    private Connection connection;

    public ResponsaveisRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean save(Responsaveis responsavel) {
        String sql = "INSERT INTO Responsaveis (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, responsavel.getNome());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     public List<String> getAllNomes() {
        List<String> nomes = new ArrayList<>();
        String sql = "SELECT nome FROM Responsaveis";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                nomes.add(result.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nomes;
    }

    public List<Responsaveis> getAll() {
        List<Responsaveis> responsaveis = new ArrayList<>();
        String sql = "SELECT * FROM Responsaveis";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Responsaveis responsavel = new Responsaveis();
                responsavel.setId(result.getInt("id"));
                responsavel.setNome(result.getString("nome"));
                responsaveis.add(responsavel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsaveis;
    }

    public boolean update(Responsaveis responsavel) {
        String sql = "UPDATE Responsaveis SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, responsavel.getNome());
            stmt.setInt(2, responsavel.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Responsaveis responsavel) {
        String sql = "DELETE FROM Responsaveis WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, responsavel.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


