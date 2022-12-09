package com.allantoledo.application.views.inicio;

import com.allantoledo.application.data.database.MesaRepository;
import com.allantoledo.application.data.entity.Mesa;
import com.allantoledo.application.views.MainLayout;
import com.allantoledo.application.views.inicio.components.MesaComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Mesas")
@Route(value = "mesas", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MesasView extends VerticalLayout {

    private MesaRepository mesaRepository = new MesaRepository();

    public MesasView() {

        HorizontalLayout line = null;
        int i = 0;
        for (Mesa mesa : mesaRepository.getAll()) {
            i++;
            if (i % 5 == 1) {
                line = new HorizontalLayout();
                line.setWidthFull();
                add(line);
            }
            MesaComponent mesaComponent = new MesaComponent(mesa);
            line.add(mesaComponent);
            line.setFlexGrow(1, mesaComponent);

        }
        setHorizontalComponentAlignment(Alignment.STRETCH);
        setSizeFull();
    }

}
