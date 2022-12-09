package com.allantoledo.application.views.relatorio;

import com.allantoledo.application.data.database.Relatorio;
import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Consumo;
import com.allantoledo.application.views.MainLayout;
import com.allantoledo.application.views.relatorio.components.ComandaCard;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;

@PageTitle("Relatório")
@Route(value = "relatorio", layout = MainLayout.class)
public class RelatorioView extends VerticalLayout {

    private Relatorio relatorio;

    private DatePicker dataInicialField;
    private DatePicker dataFinalField;

    private Button buscar;

    private Span valorTotal;
    private Span custoTotal;
    private Span lucroTotal;
    private VerticalLayout consumos;
    private HorizontalLayout comandas;

    public RelatorioView() {

        dataInicialField = new DatePicker("Data Inicial");
        dataFinalField = new DatePicker("Data Final (Não incluída)");


        buscar = new Button("Buscar");
        buscar.addClickListener(event -> {
            relatorio = new Relatorio(dataInicialField.getValue(), dataFinalField.getValue());
            BigDecimal valor = relatorio.getSomaDasVendas();
            BigDecimal custo = relatorio.getSomaDosCustos();
            BigDecimal lucro = valor.subtract(custo);

            UI.getCurrent().access(() -> {
                valorTotal.setText("Entrada total de valores: R$ " + valor);
                custoTotal.setText("Custo total: R$ " + custo);
                lucroTotal.setText("Lucro total: R$ " + lucro);
                consumos.removeAll();
                for (Consumo consumo : relatorio.produtosMaisVendido()) {
                    Span quantidade = new Span(consumo.getQuantidade().toString());
                    quantidade.getStyle().set("font-weight", "bold");
                    HorizontalLayout line = new HorizontalLayout(
                            quantidade,
                            new Span(consumo.getProduto().getNome())
                    );
                    line.getStyle().set("background-color", "#1C293A");
                    line.getStyle().set("border-radius", "5px");
                    line.setPadding(true);
                    line.setMargin(true);
                    line.setVerticalComponentAlignment(Alignment.END);
                    consumos.add(line);
                }
                comandas.removeAll();
                for (Comanda comanda : relatorio.getComandas()) {
                    ComandaCard comandaCard = new ComandaCard(comanda);
                    comandas.add(comandaCard);
                }
            });
        });

        valorTotal = new Span("Selecione duas datas para gerar o relátorio.");
        custoTotal = new Span("...");
        lucroTotal = new Span("...");
        consumos = new VerticalLayout();

        comandas = new HorizontalLayout();
        comandas.setWidthFull();
        comandas.setPadding(true);
        comandas.setAlignItems(Alignment.START);
        Scroller scroller = new Scroller(comandas);
        scroller.setWidthFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);

        HorizontalLayout dataLayout = new HorizontalLayout(dataInicialField, dataFinalField, buscar);
        dataLayout.setVerticalComponentAlignment(Alignment.END, buscar);
        add(
                new H3("Relatório entre datas"),
                dataLayout,
                valorTotal,
                custoTotal,
                lucroTotal,
                new H3("Comandas"),
                scroller,
                new H3("Produtos mais vendidos"),
                consumos
        );
        setFlexGrow(0, comandas);
    }
}
