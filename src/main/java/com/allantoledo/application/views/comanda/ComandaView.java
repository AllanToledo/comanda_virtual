package com.allantoledo.application.views.comanda;

import com.allantoledo.application.components.spacing.Space;
import com.allantoledo.application.data.database.*;
import com.allantoledo.application.data.entity.*;
import com.allantoledo.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Route(value = "comanda", layout = MainLayout.class)
public class ComandaView extends HorizontalLayout implements HasUrlParameter<String>, HasDynamicTitle {

    private Comanda comanda;

    private Repository<Comanda> comandaRepository = new ComandaRepository();
    private Repository<Produto> produtoRepository = new ProdutoRepository();
    private Repository<FormaPagamento> formaPagamentoRepository = new FormaPagamentoRepository();
    private Repository<Consumo> consumoRepository;
    private Repository<Pagamento> pagamentoRepository;
    private Grid<Consumo> gridConsumos = new Grid<>(Consumo.class, false);
    private Grid<Pagamento> gridPagamentos = new Grid<>(Pagamento.class, false);

    private H4 valorRestante = new H4();
    private VerticalLayout colunaConsumo = new VerticalLayout();
    private VerticalLayout colunaCategorias = new VerticalLayout();
    private VerticalLayout colunaProdutos = new VerticalLayout();

    public ComandaView() {

    }

    private String title = "";

    @Override
    public String getPageTitle() {
        return title;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null) {
            add(new H3("URL faltando paramêtros."), new Button("Voltar", e -> UI.getCurrent().navigate("/mesas")));
            return;
        }
        comanda = comandaRepository.get(Integer.parseInt(parameter));
        if (comanda == null) {
            add(new H3("Comanda não encontrada."), new Button("Voltar", e -> UI.getCurrent().navigate("/mesas")));
            return;
        }

        consumoRepository = new ConsumoRepository(comanda);
        pagamentoRepository = new PagamentoRepository(comanda);
        title = "Mesa " + comanda.getMesa().getNome();
        setSizeFull();
        configColunaConsumo();
        configLojaMenu();
        colunaCategorias.setWidth("auto");
        add(colunaConsumo, colunaCategorias, colunaProdutos);
        setFlexGrow(1, colunaConsumo);
        setFlexGrow(0, colunaCategorias);
        setFlexGrow(1, colunaProdutos);

    }

    private void configLojaMenu() {
        colunaCategorias.add(new H3("Categorias"));
        for (CategoriaProduto categoriaProduto : new CategoriaProdutoRepository().getAll()) {
            Button button = new Button(categoriaProduto.getCategoria());
            button.addClickListener(e -> {
                //Passando um parametro a busca dos produtos será feita com base na categoria.
                produtoRepository = new ProdutoRepository(categoriaProduto);
                configProdutos();
            });
            button.setWidthFull();
            colunaCategorias.add(button);
        }
    }

    private void configProdutos() {
        colunaProdutos.removeAll();
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);

        for (Produto produto : produtoRepository.getAll()) {
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.add(
                    new H4(produto.getNome()),
                    new H5("R$ " + produto.getValor().setScale(2, RoundingMode.HALF_UP)),
                    new Button("Adicionar", e -> {
                        Consumo consumo = null;
                        //Verifica se o produto já foi adicionado a comanda.
                        for (Consumo c : comanda.getConsumos()) {
                            if (Objects.equals(c.getProduto().getId(), produto.getId())) {
                                consumo = c;
                                consumo.addQuantidade();
                                break;
                            }
                        }
                        //Se o produto não foi adicionado a comanda, cria um consumo.
                        if (consumo == null) {
                            consumo = new Consumo(
                                    comanda, produto, 1, produto.getValor(), produto.getCusto());
                            consumoRepository.save(consumo);
                        } else {
                            //Se o produto já foi adicionado a comanda, atualiza o consumo.
                            consumoRepository.update(consumo);
                        }
                        atualizarComanda();
                    })
            );
            verticalLayout.getStyle().set("background-color", "#1C293A");
            verticalLayout.getStyle().set("border-radius", "10px");
            verticalLayout.setPadding(true);
            verticalLayout.setMargin(true);
            verticalLayout.setAlignItems(Alignment.CENTER);
            verticalLayout.setSizeUndefined();
            flexLayout.setFlexGrow(0, verticalLayout);
            flexLayout.setFlexShrink(0, verticalLayout);
            flexLayout.add(verticalLayout);
        }
        flexLayout.setWidthFull();
        flexLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        colunaProdutos.add(
                flexLayout
        );
    }

    private void configColunaConsumo() {
        colunaConsumo.removeAll();
        VerticalLayout card = new VerticalLayout();
        card.getStyle().set("background-color", "#1C293A");
        card.getStyle().set("border-radius", "10px");
        card.setPadding(true);
        card.setHorizontalComponentAlignment(Alignment.CENTER);
        card.setJustifyContentMode(JustifyContentMode.START);


        // Configuração do grid de consumos
        gridConsumos.addColumn(Consumo::getProduto)
                .setHeader("Produto")
                .setAutoWidth(true);
        gridConsumos.addColumn(Consumo::getValor)
                .setHeader("Valor")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0)
                .setWidth("5em");
        gridConsumos.addComponentColumn(consumo -> {
                    Button button = new Button("-");
                    button.addClickListener(e -> {
                        //Verifica se remover o produto não irá negativar o valor da comanda
                        if (consumo.getValor().compareTo(comanda.getRestanteAPagar()) > 0) {
                            Notification.show("Não é possível remover um produto que já foi pago.");
                            return;
                        }
                        consumo.subQuantidade();
                        //Se a quantidade for menor ou igual a 0, remove o produto da comanda
                        if (consumo.getQuantidade() == 0f) {
                            consumoRepository.delete(consumo);
                        } else {
                            consumoRepository.update(consumo);
                        }
                        atualizarComanda();
                    });
                    button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                    return button;
                })
                .setHeader(" ")
                .setAutoWidth(true)
                .setFlexGrow(0);
        gridConsumos.addColumn(Consumo::getQuantidade)
                .setHeader("Qtd")
                .setTextAlign(ColumnTextAlign.CENTER)
                .setFlexGrow(0)
                .setWidth("5em");
        gridConsumos.addComponentColumn(consumo -> {
                    Button button = new Button("+");
                    button.addClickListener(e -> {
                        consumo.addQuantidade();
                        consumoRepository.update(consumo);
                        atualizarComanda();
                    });
                    button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                    return button;
                })
                .setHeader(" ")
                .setAutoWidth(true)
                .setFlexGrow(0);
        gridConsumos.addColumn(consumo -> {
                    return consumo.getValor().multiply(BigDecimal.valueOf(consumo.getQuantidade())).setScale(2, RoundingMode.UP);
                })
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
                .setHeader("Forma de Pagamento").setAutoWidth(true);
        gridPagamentos.addColumn(Pagamento::getValor)
                .setHeader("Valor").setWidth("6em").setTextAlign(ColumnTextAlign.END);
        gridPagamentos.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridPagamentos.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        gridPagamentos.getStyle().set("background-color", "#1C293A");
        gridPagamentos.setAllRowsVisible(true);

        //Total
        HorizontalLayout restante = new HorizontalLayout();
        restante.setWidthFull();
        restante.add(new H3("Restante a pagar"), valorRestante);
        restante.setJustifyContentMode(JustifyContentMode.BETWEEN);

        atualizarComanda();

        card.add(new H3("Comanda"),
                gridConsumos,
                line,
                new H3("Pagamentos"),
                gridPagamentos,
                restante);

        //Card para adiconar pagamento
        VerticalLayout cardPagamento = new VerticalLayout();
        cardPagamento.getStyle().set("background-color", "#1C293A");
        cardPagamento.getStyle().set("border-radius", "10px");
        cardPagamento.setPadding(true);
        cardPagamento.setHorizontalComponentAlignment(Alignment.CENTER);
        cardPagamento.setJustifyContentMode(JustifyContentMode.START);
        cardPagamento.add(new H3("Adicionar Pagamento"));

        //Forma de pagamento
        ComboBox<FormaPagamento> formaPagamento = new ComboBox<>("Forma de Pagamento");
        formaPagamento.setItems(formaPagamentoRepository.getAll());

        //Entrada de valor
        BigDecimalField valor = new BigDecimalField("Valor");
        Div prefix = new Div();
        prefix.setText("R$ ");
        valor.setPrefixComponent(prefix);

        //Layuot para o valor e forma de pagamento
        HorizontalLayout linhaFormaPagamento = new HorizontalLayout();
        linhaFormaPagamento.setWidthFull();
        linhaFormaPagamento.add(formaPagamento, valor);

        //Textfield para entrada de CPF
        TextField cpfField = new TextField("CPF");
        cpfField.setId("cpfField");
        //Executa um script que adiciona a formatação ao CPF enquanto digita;
        UI.getCurrent().getPage().executeJs(
                "document.getElementById('cpfField').addEventListener('input', function (e) {\n" +
                        "        //get value and mask for CPF\n" +
                        "        var field = document.getElementById('cpfField');\n" +
                        "        var value = field.value;\n" +
                        "        console.log(value);\n" +
                        "        var regex = /(\\d{1,3})(\\d{0,3})(\\d{0,3})(\\d{0,2})/g;\n" +
                        "        var regexRemoveDots = /(([.\\-])($|-|\\.))/g;\n" +
                        "        var masked = value.replace(/[^0-9]/g, '')\n" +
                        "        masked = masked.substring(0, 11);\n" +
                        "        masked = masked.replace(regex, '$1.$2.$3-$4');\n" +
                        "        masked = masked.replace(regexRemoveDots, '');\n" +
                        "        console.log(masked);\n" +
                        "        field.value = masked;\n" +
                        "    });"
        );
        cpfField.setPlaceholder("000.000.000-00");
        cardPagamento.add(linhaFormaPagamento, cpfField);

        //Botão para adicionar pagamento
        Button adicionarPagamento = new Button("Adicionar Pagamento");
        adicionarPagamento.setWidthFull();
        adicionarPagamento.addClickListener(e -> {
            if (formaPagamento.getValue() == null) {
                Notification.show("Forma de pagamento inválida");
                formaPagamento.setErrorMessage("Forma de pagamento inválida");
                formaPagamento.focus();
                return;
            }
            BigDecimal valorPagamento = valor.getValue();
            if (valorPagamento == null) {
                Notification.show("Digite um valor");
                valor.setErrorMessage("Digite um valor");
                valor.focus();
                return;
            }
            //Arredonda o valor para duas casas decimais, importante para não adicionar um pagamento zerado
            valorPagamento = valorPagamento.setScale(2, RoundingMode.DOWN);
            if (valorPagamento.compareTo(BigDecimal.ZERO) <= 0) {
                Notification.show("Valor inválido");
                valor.setErrorMessage("Valor inválido");
                valor.focus();
                return;
            }

            //Verifica se o valor do pagamento é maior que o valor restante,
            // impede que o pagamento seja maior que o valor da comanda
            if (valor.getValue().compareTo(comanda.getRestanteAPagar()) > 0) {
                Notification.show("Valor maior que o restante");
                valor.setErrorMessage("Valor maior que o restante");
                valor.focus();
                return;
            }

            Pagamento pagamento = new Pagamento(
                    valor.getValue(),
                    formaPagamento.getValue(),
                    LocalDateTime.now(),
                    comanda
            );

            //Como CPF é opcional, verifica se foi digitado
            // e caso tenha sido digitado, verifica se é válido
            if (cpfField.getValue() != null && !cpfField.getValue().isEmpty()) {
                //Amazerna apenas os digitos do CPF
                String cpf = cpfField.getValue().replaceAll("[^0-9]", "");
                if (Pagamento.validarCPF(cpf)) {
                    pagamento.setCPFCliente(cpf);
                } else {
                    Notification.show("CPF inválido");
                    cpfField.setErrorMessage("CPF inválido");
                    cpfField.focus();
                    return;
                }
            }
            //Limpa os campos e adiciona o pagamento
            cpfField.clear();
            formaPagamento.clear();
            valor.clear();
            pagamentoRepository.save(pagamento);
            atualizarComanda();
        });

        //Fechamento da comanda
        Button fecharComanda = new Button("Fechar Comanda");
        fecharComanda.setWidthFull();
        fecharComanda.addClickListener(e -> {
            atualizarComanda();
            if (comanda.getRestanteAPagar().compareTo(BigDecimal.ZERO) > 0) {
                Notification.show("Ainda há valores a serem pagos");
                return;
            }
            //Caso não tenha adicionado nenhum produto, ela não é salva, apenas excluída
            if (comanda.getConsumos().isEmpty()) {
                Notification.show("Comanda excluída com sucesso");
                comandaRepository.delete(comanda);
            } else {
                Notification.show("Comanda fechada com sucesso");
                comanda.setFechamento(LocalDateTime.now());
                comandaRepository.update(comanda);
            }
            UI.getCurrent().navigate("/mesas");
        });
        fecharComanda.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Space space = new Space("5rem");
        colunaConsumo.setMargin(true);
        colunaConsumo.add(
                new H3("Mesa " + comanda.getMesa().getNome()),
                card,
                cardPagamento,
                adicionarPagamento,
                fecharComanda,
                space
        );
    }

    private void atualizarComanda() {
        comanda.setConsumos(consumoRepository.getAll());
        gridConsumos.setItems(comanda.getConsumos());
        comanda.setPagamentos(pagamentoRepository.getAll());
        gridPagamentos.setItems(comanda.getPagamentos());
        atualizarCustoRestante();
    }

    private void atualizarCustoRestante() {
        UI ui = UI.getCurrent();
        String valor = "R$ " + comanda.getRestanteAPagar().setScale(2, RoundingMode.UP);
        ui.access(() -> {
            valorRestante.setText(valor);
        });
    }

}