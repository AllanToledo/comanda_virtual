package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaRepository implements Repository<Mesa> {

    @Override
    public void save(Mesa mesa) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "INSERT INTO mesa (nome) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mesa.getNome());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Mesa mesa) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE mesa SET nome = ? WHERE id_mesa = ?");
            statement.setString(1, mesa.getNome());
            statement.setInt(2, mesa.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Mesa mesa) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM mesa WHERE id_mesa = ?");
            statement.setInt(1, mesa.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mesa get(int id) {
        Mesa mesa = null;
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM mesa WHERE id_mesa = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mesa = new Mesa(
                        resultSet.getInt("id_mesa"),
                        resultSet.getString("nome"));
                PreparedStatement statementComanda = connection.prepareStatement(
                        "SELECT * FROM comanda WHERE id_mesa = ? and data_fechamento is null");
                statementComanda.setInt(1, id);
                ResultSet resultSetComanda = statementComanda.executeQuery();
                if (resultSetComanda.next()) {
                    Comanda comanda = new Comanda(resultSetComanda.getInt("id_comanda"));
                    mesa.setComanda(comanda);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesa;
    }

    @Override
    public List<Mesa> getAll() {
        List<Mesa> mesas = new ArrayList<>();
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM mesa ORDER BY LENGTH(nome), nome");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mesa mesa = new Mesa(
                        resultSet.getInt("id_mesa"),
                        resultSet.getString("nome")
                );
                PreparedStatement statementComanda = connection.prepareStatement(
                        "SELECT * FROM comanda WHERE id_mesa = ? and data_fechamento is null");
                statementComanda.setInt(1, mesa.getId());
                ResultSet resultSetComanda = statementComanda.executeQuery();
                if (resultSetComanda.next()) {
                    mesa.setComanda(new Comanda(
                            resultSetComanda.getInt("id_comanda")
                    ));
                }
                mesas.add(mesa);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mesas;
    }
}
