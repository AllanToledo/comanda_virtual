package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Consumo;
import com.allantoledo.application.data.entity.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumoRepository implements Repository<Consumo> {

    private Comanda comanda;

    public ConsumoRepository(Comanda comanda) {
        this.comanda = comanda;
    }

    @Override
    public void save(Consumo consumo) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO consome(id_comanda, id_produto, quantidade, valor, custo) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, comanda.getId());
            statement.setInt(2, consumo.getProduto().getId());
            statement.setInt(3, consumo.getQuantidade());
            statement.setBigDecimal(4, consumo.getValor());
            statement.setBigDecimal(5, consumo.getCusto());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Consumo object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE consome SET quantidade = ?, valor = ? WHERE id_consome = ?");
            statement.setInt(1, object.getQuantidade());
            statement.setBigDecimal(2, object.getValor());
            statement.setInt(3, object.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Consumo object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM consome WHERE id_consome = ?");
            statement.setInt(1, object.getId());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Consumo get(int id) {
        Consumo consumo = null;
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.*, p.nome FROM consome as c LEFT JOIN produto p on c.id_produto = p.id_produto WHERE c.id_consome = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                consumo = new Consumo(
                        id,
                        comanda,
                        new Produto(
                                resultSet.getInt("id_produto"),
                                resultSet.getString("nome")),
                        resultSet.getInt("quantidade"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getBigDecimal("custo"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consumo;
    }

    @Override
    public List<Consumo> getAll() {
        List<Consumo> consumos = new ArrayList<>();
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.*, p.nome FROM consome as c LEFT JOIN produto as p on c.id_produto = p.id_produto " +
                            "WHERE c.id_comanda = ? ORDER BY c.id_consome");
            statement.setInt(1, comanda.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Consumo consumo = new Consumo(
                        resultSet.getInt("id_consome"),
                        comanda,
                        new Produto(
                                resultSet.getInt("id_produto"),
                                resultSet.getString("nome")),
                        resultSet.getInt("quantidade"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getBigDecimal("custo"));
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consumos;
    }


}
