package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.CategoriaProduto;
import com.allantoledo.application.data.entity.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements Repository<Produto> {

    private CategoriaProduto categoriaProduto;

    public ProdutoRepository(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public ProdutoRepository() {
    }

    @Override
    public void save(Produto produto) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO produto(id_categoria, valor, descricao, custo, nome) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, produto.getCategoriaProduto().getId());
            statement.setBigDecimal(2, produto.getValor());
            statement.setBigDecimal(4, produto.getCusto());
            statement.setString(5, produto.getNome());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Produto object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE produto SET id_categoria = ?, valor = ?, descricao = ?, custo = ?, nome = ? WHERE id_produto = ?");
            statement.setInt(1, object.getCategoriaProduto().getId());
            statement.setBigDecimal(2, object.getValor());
            statement.setBigDecimal(4, object.getCusto());
            statement.setString(5, object.getNome());
            statement.setInt(6, object.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Produto object) {
        try {
            Connection con = DefaultConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM produto WHERE id_produto = ?");
            statement.setInt(1, object.getId());
            statement.execute();
            statement.close();
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Produto get(int id) {
        Produto produto = null;
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM produto WHERE id_produto = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                produto = new Produto(
                        resultSet.getInt("id_produto"),
                        new CategoriaProdutoRepository().get(resultSet.getInt("id_categoria")),
                        resultSet.getString("nome"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getBigDecimal("custo")
                );
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return produto;
    }

    public List<Produto> getAllByCategory() {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM produto WHERE id_categoria = ?");
            statement.setInt(1, categoriaProduto.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {
                Produto produto = new Produto(
                        resultSet.getInt("id_produto"),
                        categoriaProduto,
                        resultSet.getString("nome"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getBigDecimal("custo")
                );
                produtos.add(produto);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return produtos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Produto> getAll() {
        if (categoriaProduto != null)
            return getAllByCategory();

        List<Produto> produtos = new ArrayList<>();
        Repository<CategoriaProduto> categoriaProdutoRepository = new CategoriaProdutoRepository();
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM produto");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Produto produto = new Produto(
                        result.getInt("id_produto"),
                        categoriaProdutoRepository.get(result.getInt("id_categoria")),
                        result.getString("nome"),
                        result.getBigDecimal("valor"),
                        result.getBigDecimal("custo")
                );
                produtos.add(produto);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }
}
