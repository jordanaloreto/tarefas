package com.jordana.application.dao;

import com.jordana.application.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {
    private Connection connection;

    public CategoriaRepository() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public boolean save(Categoria categoria) {
        String sql = "INSERT INTO Categoria (descricao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getDescricao());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllDescricoes() {
        List<String> descricoes = new ArrayList<>();
        String sql = "SELECT descricao FROM Categoria";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                descricoes.add(result.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descricoes;
    }
}
