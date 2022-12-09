package com.allantoledo.application.data.entity;

import java.math.BigDecimal;

public class Consumo {
    private int id;
    private Comanda comanda;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal custo;

    public Consumo(int id, Comanda comanda, Produto produto, Integer quantidade, BigDecimal valor, BigDecimal custo) {
        this.id = id;
        this.comanda = comanda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.custo = custo;
    }

    public Consumo(Comanda comanda, Produto produto, Integer quantidade, BigDecimal valor, BigDecimal custo) {
        this.comanda = comanda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.custo = custo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void addQuantidade() {
        this.quantidade++;
    }

    public void subQuantidade() {
        this.quantidade--;
        if (this.quantidade < 0) {
            this.quantidade = 0;
        }
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }
}
