package com.allantoledo.application.data.entity;

public class Mesa {

    private Integer id;

    private String nome;
    private Comanda comanda = null;

    public Mesa(String nome) {
        this.nome = nome;
    }
    public Mesa(int id, String numero) {
        this.id = id;
        this.nome = numero;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public String getNome() {
        return nome;
    }

    public boolean isOcupada() {
        return comanda != null;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
