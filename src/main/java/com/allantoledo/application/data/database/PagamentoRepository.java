package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.FormaPagamento;
import com.allantoledo.application.data.entity.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepository implements Repository<Pagamento> {

    private Comanda comanda;
    private Repository<FormaPagamento> formaPagamentoRepository = new FormaPagamentoRepository();

    public PagamentoRepository(Comanda comanda) {
        this.comanda = comanda;
    }

    @Override
    public void save(Pagamento pagamento) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "INSERT INTO pagamento ( id_comanda, id_forma_pagamento, valor, data, cpf_cliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, comanda.getId());
            statement.setInt(2, pagamento.getFormaPagamento().getId());
            statement.setBigDecimal(3, pagamento.getValor());
            statement.setTimestamp(4, Timestamp.valueOf(pagamento.getDataPagamento()));
            statement.setString(5, pagamento.getCPFCliente());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pagamento object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "UPDATE pagamento SET id_forma_pagamento = ?, valor = ?, data = ?, cpf_cliente = ? WHERE id_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getFormaPagamento().getId());
            statement.setBigDecimal(2, object.getValor());
            statement.setTimestamp(3, Timestamp.valueOf(object.getDataPagamento()));
            statement.setString(4, object.getCPFCliente());
            statement.setInt(5, object.getId());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Pagamento object) {
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "DELETE FROM pagamento WHERE id_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, object.getId());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pagamento get(int id) {
        Pagamento pagamento = null;
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT * FROM pagamento WHERE id_pagamento = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pagamento = new Pagamento(
                        resultSet.getInt("id_pagamento"),
                        resultSet.getBigDecimal("valor"),
                        formaPagamentoRepository.get(resultSet.getInt("id_forma_pagamento")),
                        resultSet.getString("cpf_cliente"),
                        resultSet.getTimestamp("data").toLocalDateTime(),
                        comanda
                );
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pagamento;
    }

    @Override
    public List<Pagamento> getAll() {
        List<Pagamento> pagamentos = new ArrayList<>();
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT * FROM pagamento WHERE id_comanda = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, comanda.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Pagamento pagamento = new Pagamento(
                        resultSet.getInt("id_pagamento"),
                        resultSet.getBigDecimal("valor"),
                        formaPagamentoRepository.get(resultSet.getInt("id_forma_pagamento")),
                        resultSet.getString("cpf_cliente"),
                        resultSet.getTimestamp("data").toLocalDateTime(),
                        comanda
                );
                pagamentos.add(pagamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pagamentos;
    }
}
