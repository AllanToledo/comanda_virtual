package com.allantoledo.application.views.inicio.components;

import com.allantoledo.application.data.database.ComandaRepository;
import com.allantoledo.application.data.database.MesaRepository;
import com.allantoledo.application.data.database.Repository;
import com.allantoledo.application.data.entity.Comanda;
import com.allantoledo.application.data.entity.Mesa;
import com.allantoledo.application.views.comanda.ComandaView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;

public class MesaComponent extends VerticalLayout {

    private Mesa mesa;
    private Button criarComanda;

    private Repository<Comanda> comandaRepository = new ComandaRepository();

    private Repository<Mesa> mesaRespository = new MesaRepository();

    public MesaComponent(Mesa mesa) {
        this.mesa = mesa;
        H2 h2 = new H2("Mesa " + mesa.getNome());
        criarComanda = new Button("Criar Comanda");
        criarComanda.addClickListener(e -> {
            Mesa atualizada = mesaRespository.get(mesa.getId());
            mesa.setComanda(atualizada.getComanda());

            if (mesa.isOcupada()) {
                criarComanda.getUI().ifPresent(ui ->
                        ui.navigate(ComandaView.class, Integer.toString(mesa.getComanda().getId()))
                );
                return;
            }

            mesa.setComanda(new Comanda(
                    mesa,
                    LocalDateTime.now(),
                    null,
                    null
            ));

            comandaRepository.save(mesa.getComanda());
            configureCard();
        });
        criarComanda.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        initialConfig();
        add(h2, criarComanda);
    }

    private void initialConfig() {
        setMinHeight("10em");
        setMinWidth("10em");
        setPadding(false);
        setMargin(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("border-radius", "10px");
        configureCard();
    }

    private void configureCard() {
        getStyle().set("background-color", mesa.isOcupada() ? "#18B47D" : "#1C293A");
        criarComanda.setText(mesa.isOcupada() ? "Abrir Comanda" : "Criar Comanda");
    }
}
