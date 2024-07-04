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
}
