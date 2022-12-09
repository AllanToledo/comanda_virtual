package com.allantoledo.application.data.entity;

import java.math.BigDecimal;


public class Produto {

    private Integer id;
    private CategoriaProduto categoriaProduto;
    private String nome;
    private BigDecimal valor;
    private BigDecimal custo;


    public Produto(
            Integer idProduto,
            CategoriaProduto categoriaProduto,
            String nome,
            BigDecimal valor,
            BigDecimal custo
    ) {
        this.id = idProduto;
        this.categoriaProduto = categoriaProduto;
        this.nome = nome;
        this.valor = valor;
        this.custo = custo;
    }

    public Produto(
            CategoriaProduto categoriaProduto,
            String nome,
            BigDecimal valor,
            BigDecimal custo
    ) {
        this.categoriaProduto = categoriaProduto;
        this.nome = nome;
        this.valor = valor;
        this.custo = custo;
    }

    public Produto(int id_produto, String nome) {
        this.id = id_produto;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public Integer getId() {
        return id;
    }

    public void setIdProduto(Integer idProduto) {
        this.id = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = new BigDecimal(valor);
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = new BigDecimal(custo);
    }

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
