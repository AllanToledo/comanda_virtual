package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Mesa;
import com.vaadin.flow.component.html.Pre;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaRepository implements Repository<Comanda> {
    @Override
    public void save(Comanda comanda) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO comanda (id_mesa, data_abertura) VALUES (?, ?)");
            statement.setInt(1, comanda.getMesa().getId());
            statement.setTimestamp(2, Timestamp.valueOf(comanda.getAbertura()));
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Comanda comanda) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE comanda SET data_fechamento = ?, valor_total = ? WHERE id_comanda = ?");
            statement.setTimestamp(1, Timestamp.valueOf(comanda.getFechamento()));
            statement.setBigDecimal(2, comanda.getValor());
            statement.setInt(3, comanda.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Comanda object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM comanda WHERE id_comanda = ?");
            statement.setInt(1, object.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Comanda get(int id) {
        Comanda comanda = null;
        try {
            Connection connection = DefaultConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM comanda WHERE id_comanda = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Mesa mesa = null;
                PreparedStatement statementMesa = connection.prepareStatement(
                        "SELECT * FROM mesa WHERE id_mesa = ?");
                statementMesa.setInt(1, resultSet.getInt("id_mesa"));
                ResultSet resultSetMesa = statementMesa.executeQuery();
                if (resultSetMesa.next()) {
                    mesa = new Mesa(
                            resultSetMesa.getInt("id_mesa"),
                            resultSetMesa.getString("nome"));
                }

                Timestamp fechamento = resultSet.getTimestamp("data_fechamento");

                comanda = new Comanda(
                        resultSet.getInt("id_comanda"),
                        mesa,
                        resultSet.getTimestamp("data_abertura").toLocalDateTime(),
                        fechamento != null ? fechamento.toLocalDateTime() : null,
                        resultSet.getBigDecimal("valor_total"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comanda;
    }

    @Override
    public List<Comanda> getAll() {
        return new ArrayList<>();
    }
}
