package com.example.application.components.grid;

import com.example.application.components.UIFactory;
import com.example.application.data.entity.Library;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.function.Consumer;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LibraryGrid extends Grid<Library> {

    public LibraryGrid() {
        super(Library.class, false);
    }

    @PostConstruct
    private void initUI() {
        this.setColumns("name", "address");
        this.setMaxHeight("250px");
        this.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    public void addActionColumns(Consumer<Library> onEdit, Consumer<Library> onDelete)  {
        UIFactory.gridActionColumn(this, library -> {
            Button edit = UIFactory.gridBtn(LineAwesomeIcon.EDIT.create(), event -> onEdit.accept(library), ButtonVariant.LUMO_SUCCESS);
            Button delete = UIFactory.gridBtn(LineAwesomeIcon.TRASH_ALT.create(), event -> onDelete.accept(library), ButtonVariant.LUMO_ERROR);
            return UIFactory.gridColumnAction(edit, delete);
        });
    }

    public void addSelectionListener(Consumer<Library> onCompanySelect) {
        this.asSingleSelect().addValueChangeListener(event -> onCompanySelect.accept(event.getValue()));
    }
}
