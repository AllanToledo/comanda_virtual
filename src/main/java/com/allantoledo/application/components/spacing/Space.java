package com.allantoledo.application.components.spacing;

import com.vaadin.flow.component.Html;

public class Space extends Html {
    public Space(String size) {
        super("<div style=\"font-size:" + size + ";\">&nbsp;<div\\>");
    }
}
