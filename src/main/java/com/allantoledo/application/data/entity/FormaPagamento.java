package com.allantoledo.application.data.entity;

public class FormaPagamento {
    private Integer id;
    private String formaPagamento;

    public FormaPagamento(Integer id, String formaPagamento) {
        this.id = id;
        this.formaPagamento = formaPagamento;
    }

    public FormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public FormaPagamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return formaPagamento;
    }

}
