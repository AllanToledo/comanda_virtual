package com.allantoledo.application.data.database;

import com.allantoledo.application.data.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Relatorio {

    private LocalDate startDate;
    private LocalDate endDate;

    public Relatorio(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BigDecimal getSomaDasVendas() {
        BigDecimal sum = BigDecimal.ZERO;
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT SUM(valor * quantidade)::DECIMAL(9, 2) FROM comanda c LEFT JOIN consome c2 on c.id_comanda = c2.id_comanda " +
                    "WHERE data_fechamento IS NOT NULL AND (data_abertura BETWEEN ? AND ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(endDate.atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getBigDecimal(1);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    public BigDecimal getSomaDosCustos() {
        BigDecimal sum = BigDecimal.ZERO;
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT SUM(custo * quantidade)::DECIMAL(9, 2) FROM comanda c LEFT JOIN consome c2 on c.id_comanda = c2.id_comanda " +
                    "WHERE data_fechamento IS NOT NULL and data_abertura BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(endDate.atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getBigDecimal(1);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    public List<Consumo> produtosMaisVendido() {
        List<Consumo> consumos = new ArrayList<>();
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT p.id_produto, p.nome, p.custo, p.valor, SUM(c2.quantidade) as q FROM comanda c LEFT JOIN consome c2 on c.id_comanda = c2.id_comanda " +
                    "LEFT JOIN produto p on c2.id_produto = p.id_produto " +
                    "WHERE data_fechamento IS NOT NULL and data_abertura BETWEEN ? AND ? " +
                    "GROUP BY p.id_produto " +
                    "ORDER BY q DESC LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(endDate.atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Consumo consumo = new Consumo(
                        resultSet.getInt("id_produto"),
                        null,
                        new Produto(
                                resultSet.getInt("id_produto"),
                                null,
                                resultSet.getString("nome"),
                                resultSet.getBigDecimal("valor"),
                                resultSet.getBigDecimal("custo")
                        ),
                        resultSet.getInt("q"),
                        resultSet.getBigDecimal("valor"),
                        resultSet.getBigDecimal("custo")
                );
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consumos;
    }

    public List<Comanda> getComandas() {
        List<Comanda> comandas = new ArrayList<>();
        Repository<Consumo> consumoRepository;
        Repository<Pagamento> pagamentoRepository;
        try {
            Connection connection = DefaultConnection.getConnection();
            String sql = "SELECT * FROM comanda LEFT JOIN mesa m on comanda.id_mesa = m.id_mesa " +
                    "WHERE data_fechamento IS NOT NULL and data_abertura BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(endDate.atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comanda comanda = new Comanda(
                        resultSet.getInt("id_comanda"),
                        new Mesa(
                                resultSet.getInt("id_mesa"),
                                resultSet.getString("nome")
                        ),
                        resultSet.getTimestamp("data_abertura").toLocalDateTime(),
                        resultSet.getTimestamp("data_fechamento").toLocalDateTime(),
                        resultSet.getBigDecimal("valor_total")
                );
                consumoRepository = new ConsumoRepository(comanda);
                pagamentoRepository = new PagamentoRepository(comanda);
                comanda.setConsumos(consumoRepository.getAll());
                comanda.setPagamentos(pagamentoRepository.getAll());
                comandas.add(comanda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comandas;
    }
}
