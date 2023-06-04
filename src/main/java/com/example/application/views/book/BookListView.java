package com.example.application.views.book;

import com.example.application.data.entity.Book;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "book", layout = MainLayout.class)
public class BookListView extends VerticalLayout {
    private Grid<Book> grid;

    public BookListView() {
        grid = new Grid<>(Book.class, false);
        initUI();
    }

    private void initUI() {
        grid.addColumns("title", "author", "language1", "category");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.add(grid);
    }


}
