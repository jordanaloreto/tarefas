package com.jordana.application.dao;

import com.jordana.application.model.Responsaveis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
