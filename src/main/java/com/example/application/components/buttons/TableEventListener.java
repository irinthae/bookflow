package com.example.application.components.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;

public interface TableEventListener<T> {
    void onTableEvent(ClickEvent<Button> clickEvent, T entity);
}
