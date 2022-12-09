package com.allantoledo.application.views.relatorio.components;

import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Consumo;
import com.allantoledo.application.data.entity.Pagamento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ComandaCard extends VerticalLayout {

    public Comanda comanda;

    private Grid<Consumo> gridConsumos = new Grid<>(Consumo.class, false);
    private Grid<Pagamento> gridPagamentos = new Grid<>(Pagamento.class, false);

    public ComandaCard(Comanda comanda) {
        this.comanda = comanda;
        getStyle().set("background-color", "#1C293A");
        getStyle().set("border-radius", "10px");
        setPadding(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        setSizeUndefined();
        setHeight("auto");
        setWidth("25em");
        // Configuração do grid de consumos
        gridConsumos.addColumn(Consumo::getProduto)
                .setHeader("Produto");
        gridConsumos.addColumn(Consumo::getValor)
                .setHeader("Valor")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0)
                .setWidth("5em");
        gridConsumos.addColumn(Consumo::getQuantidade)
                .setHeader("Qtd")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0)
                .setWidth("5em");
        gridConsumos.addColumn(consumo -> consumo.getValor()
                        .multiply(BigDecimal.valueOf(consumo.getQuantidade())).setScale(2, RoundingMode.UP)
                )
                .setHeader("Total")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER)
                .setWidth("6em");
        gridConsumos.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridConsumos.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        gridConsumos.getStyle().set("background-color", "#1C293A");
        gridConsumos.setAllRowsVisible(true);


        //Linha que separa
        Div line = new Div();
        line.getStyle().set("border", "2px dashed #233348");
        line.setWidthFull();

        //Pagamentos
        gridPagamentos.addColumn(p -> p.getFormaPagamento().getFormaPagamento())
                .setHeader("Forma de Pagamento");
        gridPagamentos.addColumn(Pagamento::getValor)
                .setHeader("Valor").setFlexGrow(0).setWidth("6em").setTextAlign(ColumnTextAlign.END);
        gridPagamentos.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridPagamentos.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        gridPagamentos.getStyle().set("background-color", "#1C293A");
        gridPagamentos.setAllRowsVisible(true);

        gridConsumos.setItems(comanda.getConsumos());
        gridPagamentos.setItems(comanda.getPagamentos());

        String data = comanda.getAbertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String duracao = ChronoUnit.HOURS.between(comanda.getAbertura(), comanda.getFechamento()) + "h " +
                ChronoUnit.MINUTES.between(comanda.getAbertura(), comanda.getFechamento()) % 60 + "m";

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.add(new H3("Comanda " + comanda.getId()));
        header.add(new H4("Mesa " + comanda.getMesa().getNome()));
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        footer.add(new H4("Total"));
        footer.add(new H4(comanda.getValor().toPlainString()));
        add(
                header,
                new Span(data + " " + duracao),
                gridConsumos,
                line,
                new H3("Pagamentos"),
                gridPagamentos,
                footer
        );
    }
}
