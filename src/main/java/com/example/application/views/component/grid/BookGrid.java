package com.example.application.views.component.grid;

import com.example.application.data.entity.Book;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BookGrid extends Grid<Book> {

    public BookGrid() {
        super(Book.class, false);
        initUI();
    }

    private void initUI() {
        this.addColumn("title").setAutoWidth(true);
        this.addColumn("author").setAutoWidth(true);
        this.addColumn("language1").setAutoWidth(true).setHeader("language");
        this.addColumn("category").setAutoWidth(true);
    }

    public void clear() {
        setItems(new ArrayList<>());
    }
}
