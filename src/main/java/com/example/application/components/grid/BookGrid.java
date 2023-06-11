package com.example.application.components.grid;

import com.example.application.components.UIFactory;
import com.example.application.data.entity.Book;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.ArrayList;
import java.util.function.Consumer;

import static org.vaadin.lineawesome.LineAwesomeIcon.*;

public class BookGrid extends Grid<Book> {

    public BookGrid() {
        super(Book.class, false);
        initUI();
    }

    public BookGrid(Consumer<Book> onEdit, Consumer<Book> onDelete) {
        super(Book.class, false);
        initUI();
        addActionColumns(onEdit, onDelete);
    }

    private void initUI() {
        setMaxHeight("80vh");
        this.setPageSize(100);

        this.addColumn("title").setAutoWidth(true);
        this.addColumn("author").setAutoWidth(true);
        this.addColumn("language1").setAutoWidth(true).setHeader("language");
        this.addColumn("category").setAutoWidth(true);

        this.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    private void addActionColumns(Consumer<Book> onEdit, Consumer<Book> onDelete) {
        UIFactory.tableActionColumn(this, book-> {
            Button add = UIFactory.btnIcon(EDIT.create(), e -> onEdit.accept(book), ButtonVariant.LUMO_SUCCESS);
            Button remove = UIFactory.btnIcon(TRASH_ALT.create(), e -> onDelete.accept(book), ButtonVariant.LUMO_ERROR);
            return new Span(add, new Span("   "), remove);
        });
    }

    public void clear() {
        setItems(new ArrayList<>());
    }
}
