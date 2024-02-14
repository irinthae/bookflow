package com.example.application.components.form;

import com.example.application.data.entity.Library;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LibraryForm extends VerticalLayout {

    private final TextField name = new TextField("Name");
    private final TextField address = new TextField("Address");
    private final Binder<Library> binder = new BeanValidationBinder<>( Library.class );

    public LibraryForm() {
    }

    @PostConstruct
    private void init() {
        this.addClassName("page-view");
        this.add(new FormLayout(name, address));

        binder.bindInstanceFields(this);
    }

    public Library getLibrary() {
        return binder.getBean();
    }

    public void setLibrary(Library library) {
        binder.setBean(library);
    }

    public boolean isValid() {
        binder.validate();
        return binder.isValid();
    }

    public void refresh() {
        binder.readBean(new Library());
    }
}
