package com.allantoledo.application.views.pagamento;

import com.allantoledo.application.data.database.FormaPagamentoRepository;
import com.allantoledo.application.data.database.Repository;
import com.allantoledo.application.data.entity.FormaPagamento;
import com.allantoledo.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tipo Pagamento")
@Route(value = "tipopagamento", layout = MainLayout.class)
public class FormaPagamentoView extends VerticalLayout {

    private IntegerField idField;
    private TextField formaPagamentoField;

    private Button saveButton;
    private Button cancelButton;

    private Repository<FormaPagamento> formaPagamentoRepository = new FormaPagamentoRepository();

    private Grid<FormaPagamento> grid = new Grid<>(FormaPagamento.class, false);

    public FormaPagamentoView() {
        idField = new IntegerField("Id");
        idField.setReadOnly(true);
        formaPagamentoField = new TextField("Forma Pagamento");

        saveButton = new Button("Salvar");
        saveButton.addClickListener(event -> {
            FormaPagamento formaPagamento = new FormaPagamento(
                    formaPagamentoField.getValue()
            );
            formaPagamento.setId(idField.getValue());
            if (formaPagamento.getId() == null) {
                formaPagamentoRepository.save(formaPagamento);
            } else {
                formaPagamentoRepository.update(formaPagamento);
            }
            cancelButton.click();
            grid.setItems(formaPagamentoRepository.getAll());
        });

        cancelButton = new Button("Cancelar");
        cancelButton.addClickListener(event -> {
            idField.clear();
            formaPagamentoField.clear();
        });

        grid.addColumn(FormaPagamento::getFormaPagamento).setHeader("Forma Pagamento");
        grid.addComponentColumn(item -> {
            Button editButton = new Button("Editar");
            editButton.addClickListener(event -> {
                idField.setValue(item.getId());
                formaPagamentoField.setValue(item.getFormaPagamento());
            });
            return editButton;
        });
        grid.addComponentColumn(item -> {
            Button deleteButton = new Button("Excluir");
            deleteButton.addClickListener(event -> {
                formaPagamentoRepository.delete(item);
                grid.setItems(formaPagamentoRepository.getAll());
            });
            return deleteButton;
        });

        grid.setItems(formaPagamentoRepository.getAll());
        HorizontalLayout linha = new HorizontalLayout(saveButton, cancelButton);

        add(idField, formaPagamentoField, linha, grid);
    }

}
