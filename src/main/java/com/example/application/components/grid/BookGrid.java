package com.example.application.components.grid;

import com.example.application.components.UIFactory;
import com.example.application.data.entity.Book;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Consumer;
import static org.vaadin.lineawesome.LineAwesomeIcon.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookGrid extends Grid<Book> {

    public BookGrid() {
        super(Book.class, false);
    }

    @PostConstruct
    private void initUI() {
        setMaxHeight("80vh");
        this.setPageSize(100);
        UIFactory.gridInfoColumn(this);
        this.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    public void addActionColumns(Consumer<Book> onEdit, Consumer<Book> onDelete) {
        UIFactory.gridActionColumn(this, book-> {
            Button edit = UIFactory.gridBtn(EDIT.create(), e -> onEdit.accept(book), ButtonVariant.LUMO_SUCCESS);
            Button remove = UIFactory.gridBtn(TRASH_ALT.create(), e -> onDelete.accept(book), ButtonVariant.LUMO_ERROR);
            return UIFactory.gridColumnAction(edit, remove);
        });
    }

    public void addDetailColumns() {
        this.addColumn("title").setAutoWidth(true);
        this.addColumn("author").setAutoWidth(true);
        this.addColumn("language1").setAutoWidth(true).setHeader("language");
        this.addColumn("category").setAutoWidth(true);
    }

    public void clear() {
        setItems(new ArrayList<>());
    }
}
