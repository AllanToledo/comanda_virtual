package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.CategoriaProduto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaProdutoRepository implements Repository<CategoriaProduto> {
    @Override
    public void save(CategoriaProduto categoriaProduto) {
        try {
            Connection con = DefaultConnection.getConnection();
            String sql = "INSERT INTO categoria_produto(categoria) VALUES (?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, categoriaProduto.getCategoria());
            stmt.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CategoriaProduto object) {
        try {
            Connection con = DefaultConnection.getConnection();
            String sql = "UPDATE categoria_produto SET categoria = ? WHERE id_categoria = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, object.getCategoria());
            stmt.setInt(2, object.getId());
            stmt.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(CategoriaProduto object) {
        try {
            Connection con = DefaultConnection.getConnection();
            String sql = "DELETE FROM categoria_produto WHERE id_categoria = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, object.getId());
            stmt.execute();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoriaProduto get(int id) {
        CategoriaProduto categoriaProduto;
        try {
            Connection con = DefaultConnection.getConnection();
            String sql = "SELECT * FROM categoria_produto WHERE id_categoria = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                categoriaProduto = new CategoriaProduto(rs.getString("categoria"));
                categoriaProduto.setId(rs.getInt("id_categoria"));
            } else {
                categoriaProduto = null;
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoriaProduto;
    }

    @Override
    public List<CategoriaProduto> getAll() {
        List<CategoriaProduto> categorias = new ArrayList();
        Connection con;
        try {
            con = DefaultConnection.getConnection();
            String sql = "SELECT * FROM categoria_produto";
            ResultSet result = con.createStatement().executeQuery(sql);
            while (result.next()) {
                CategoriaProduto categoria = new CategoriaProduto(result.getString("categoria"));
                categoria.setId(result.getInt("id_categoria"));
                categorias.add(categoria);
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorias;
    }
}
