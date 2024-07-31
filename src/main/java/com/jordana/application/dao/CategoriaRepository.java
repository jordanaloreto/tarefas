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

    public List<Categoria> getAll() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(result.getInt("id"));
                categoria.setDescricao(result.getString("descricao"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean update(Categoria categoria) {
        String sql = "UPDATE Categoria SET descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getDescricao());
            stmt.setInt(2, categoria.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Categoria categoria) {
        if (isCategoriaInUse(categoria)) {
            return false; // Não exclui se a categoria está em uso
        }
    
        String sql = "DELETE FROM Categoria WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    private boolean isCategoriaInUse(Categoria categoria) {
        String sql = "SELECT COUNT(*) FROM Tarefa WHERE categoria_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                System.out.println("Categoria ID: " + categoria.getId() + " está associada a " + count + " tarefas.");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se houver um erro na consulta ou não houver resultado, retorne false, permitindo a exclusão.
        return false;
    }
    

}
