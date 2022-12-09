package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.FormaPagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoRepository implements Repository<FormaPagamento> {
    @Override
    public void save(FormaPagamento object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "INSERT INTO forma_pagamento (tipo) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getFormaPagamento());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FormaPagamento object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "UPDATE forma_pagamento SET tipo = ? WHERE id_forma_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, object.getFormaPagamento());
            statement.setInt(2, object.getId());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(FormaPagamento object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "DELETE FROM forma_pagamento WHERE id_forma_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FormaPagamento get(int id) {
        FormaPagamento formaPagamento = new FormaPagamento();
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT * FROM forma_pagamento WHERE id_forma_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                formaPagamento.setId(resultSet.getInt("id_forma_pagamento"));
                formaPagamento.setFormaPagamento(resultSet.getString("tipo"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return formaPagamento;
    }

    @Override
    public List<FormaPagamento> getAll() {
        List<FormaPagamento> formasPagamento = new ArrayList<>();
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT * FROM forma_pagamento";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_forma_pagamento");
                String descricao = resultSet.getString("tipo");
                formasPagamento.add(new FormaPagamento(id, descricao));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return formasPagamento;
    }
}
