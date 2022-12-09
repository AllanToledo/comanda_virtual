package com.allantoledo.application.views.mesa;

import com.allantoledo.application.data.database.MesaRepository;
import com.allantoledo.application.data.database.Repository;
import com.allantoledo.application.data.entity.Mesa;
import com.allantoledo.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Mesa")
@Route(value = "mesa", layout = MainLayout.class)
public class MesaView extends VerticalLayout {

    private IntegerField idField;
    private TextField nomeField;

    private Repository<Mesa> mesaRepository = new MesaRepository();

    private Grid<Mesa> grid = new Grid<>(Mesa.class, false);

    private Button saveButton;
    private Button cancelButton;

    public MesaView() {
        idField = new IntegerField("Id");
        idField.setReadOnly(true);
        nomeField = new TextField("Nome");

        grid.addColumn(Mesa::getNome).setHeader("Nome");
        grid.addComponentColumn(mesa -> {
            Button button = new Button("Editar");
            button.addClickListener(event -> {
                idField.setValue(mesa.getId());
                nomeField.setValue(mesa.getNome());
            });
            return button;
        });
        grid.addComponentColumn(mesa -> {
            Button button = new Button("Excluir");
            button.addClickListener(event -> {
                mesaRepository.delete(mesa);
                grid.setItems(mesaRepository.getAll());
            });
            return button;
        });

        saveButton = new Button("Salvar");
        saveButton.addClickListener(event -> {
            Mesa mesa = new Mesa(
                    nomeField.getValue()
            );
            mesa.setId(idField.getValue());
            if (mesa.getId() == null) {
                mesaRepository.save(mesa);
            } else {
                mesaRepository.update(mesa);
            }
            grid.setItems(mesaRepository.getAll());
            cancelButton.click();
        });

        cancelButton = new Button("Cancelar");
        cancelButton.addClickListener(event -> {
            idField.clear();
            nomeField.clear();
        });

        grid.setItems(mesaRepository.getAll());
        HorizontalLayout linha = new HorizontalLayout();
        linha.add(saveButton, cancelButton);
        add(idField, nomeField, linha, grid);
    }
}
