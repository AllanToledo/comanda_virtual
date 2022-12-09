package com.allantoledo.application.data.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class Comanda {

    private int id;
    private Mesa mesa;
    private LocalDateTime abertura;
    private LocalDateTime fechamento;
    private List<Consumo> consumos;
    private BigDecimal valor;
    private List<Pagamento> pagamentos;
    private BigDecimal valorPago;

    public Comanda(int id, Mesa mesa, LocalDateTime abertura, LocalDateTime fechamento, BigDecimal valor) {
        this.id = id;
        this.mesa = mesa;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.valor = valor;
    }

    public Comanda(Mesa mesa, LocalDateTime abertura, LocalDateTime fechamento, BigDecimal valor) {
        this.mesa = mesa;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.valor = valor;
    }

    public Comanda(int id) {
        this.id = id;
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumo> consumos) {
        valor = null;
        this.consumos = consumos;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        valorPago = null;
        this.pagamentos = pagamentos;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public BigDecimal getValor() {
        if (valor == null) {
            valor = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            for (Consumo consumo : consumos) {
                valor = valor.add(consumo.getValor().multiply(BigDecimal.valueOf(consumo.getQuantidade())));
            }
        }
        return valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAbertura() {
        return abertura;
    }

    public LocalDateTime getFechamento() {
        return fechamento;
    }

    public BigDecimal getRestanteAPagar() {
        return getValor().subtract(getValorPago());
    }

    private BigDecimal getValorPago() {
        if (valorPago == null) {
            valorPago = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            for (Pagamento pagamento : pagamentos) {
                valorPago = valorPago.add(pagamento.getValor());
            }
        }
        return valorPago;
    }

    public void setFechamento(LocalDateTime now) {
        fechamento = now;
    }
}
