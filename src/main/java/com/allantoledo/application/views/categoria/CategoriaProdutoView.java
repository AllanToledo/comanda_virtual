package com.allantoledo.application.views.categoria;

import com.allantoledo.application.data.database.CategoriaProdutoRepository;
import com.allantoledo.application.data.database.Repository;
import com.allantoledo.application.data.entity.CategoriaProduto;
import com.allantoledo.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "categoria" ,layout = MainLayout.class)
public class CategoriaProdutoView extends VerticalLayout {

    private IntegerField idField;
    private TextField categoriaField;
    private Button saveButton;
    private Button cancelButton;

    private Repository<CategoriaProduto> repository = new CategoriaProdutoRepository();

    private Grid<CategoriaProduto> grid = new Grid<>(CategoriaProduto.class, false);

    public CategoriaProdutoView() {
        idField = new IntegerField("Id");
        idField.setReadOnly(true);

        categoriaField = new TextField("Categoria");

        saveButton = new Button("Salvar");
        cancelButton = new Button("Cancelar");

        VerticalLayout form = new VerticalLayout();

        saveButton.addClickListener(e -> {
            CategoriaProduto categoriaProduto = new CategoriaProduto(categoriaField.getValue());
            if (idField.getValue() != null) {
                categoriaProduto.setId(idField.getValue());
                repository.update(categoriaProduto);
            } else {
                repository.save(categoriaProduto);
            }
            grid.setItems(repository.getAll());
            cancelButton.click();
        });

        cancelButton.addClickListener(e -> {
            idField.clear();
            categoriaField.clear();
        });

        grid.addColumn(CategoriaProduto::getId).setHeader("Id");
        grid.addColumn(CategoriaProduto::getCategoria).setHeader("Categoria");
        grid.addComponentColumn(item -> new Button("Remover", e -> {
            repository.delete(item);
            grid.setItems(repository.getAll());
        }));

        grid.addComponentColumn(item -> new Button("Editar", e -> {
            idField.setValue(item.getId());
            categoriaField.setValue(item.getCategoria());
        }));

        grid.setItems(repository.getAll());
        form.add(idField, categoriaField, new HorizontalLayout(saveButton, cancelButton));
        add(form, grid);
    }
}
