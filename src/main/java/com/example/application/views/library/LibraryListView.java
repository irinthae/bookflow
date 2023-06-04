package com.example.application.views.library;


import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.example.application.views.component.grid.BookGrid;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.stream.Stream;

@Slf4j
@PageTitle(value = "library")
@Route(value = "library/list", layout = MainLayout.class)
public class LibraryListView extends VerticalLayout {

    private final Grid<Library> libraryGrid = new Grid<>(Library.class, false);
    private final BookGrid bookGrid;
    private final LibraryService service;

    public LibraryListView(LibraryService service, BookGrid bookGrid) {
        this.service = service;
        this.bookGrid = bookGrid;
        initUI();
    }

    private void initUI() {
        libraryGrid.setColumns("name");
        libraryGrid.addComponentColumn(library -> {
            Button edit = new Button(LineAwesomeIcon.EDIT.create());
            Button delete = new Button(LineAwesomeIcon.TRASH_SOLID.create());
            return new Span(edit, delete);
        });

        libraryGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        libraryGrid.asSingleSelect().addValueChangeListener(event -> onCompanySelect(event.getValue()));

        this.add(new H4("Libraries"), libraryGrid);
        this.add(new H4("Books"), bookGrid);

        libraryGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        reload();

    }


    private void reload() {
        libraryGrid.setItems(query ->
            service.findAll(VaadinSpringDataHelpers.toSpringPageRequest(query))
        );
    }

    private void onCompanySelect(Library library) {
        if (library == null) {
            bookGrid.clear();
            return;
        }

        bookGrid.setItems(query -> service.findBooksByLibraryId(library.getId(),
                                                                VaadinSpringDataHelpers.toSpringPageRequest(query)));
    }
}
