package com.example.application.views.library;


import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.example.application.components.grid.BookGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Slf4j
@NavTitle("Libraries")
@PageTitle(value = "Library")
@Route(value = "librarylist", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LibraryListView extends VerticalLayout {

    private final Button addLibrary = UIFactory.btnTertiary("Create", VaadinIcon.PLUS.create(), e -> onCreate());
    private final Grid<Library> libraryGrid = new Grid<>(Library.class, false);
    private final Text libraryCounter = new Text("");
    private final BookGrid bookGrid = new BookGrid();
    private Component bookGridHeader;
    private final Text bookCounter = new Text("");
    private final LibraryService service;

    @Autowired
    public LibraryListView(LibraryService service) {
        this.service = service;
        initUI();
    }

    private void initUI() {
        addClassName("page-view");
        setSizeFull();

        libraryGrid.setColumns("name", "address");
        libraryGrid.addComponentColumn(library -> {
            Button edit = UIFactory.btnIcon(LineAwesomeIcon.EDIT.create(), event -> onEdit(library), ButtonVariant.LUMO_SUCCESS);
            Button delete = UIFactory.btnIcon(LineAwesomeIcon.TRASH_SOLID.create(), event -> onDelete(library), ButtonVariant.LUMO_ERROR);
            return new Span(edit, new Span("   "), delete);
        }).setHeader("Actions");

        libraryGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        libraryGrid.asSingleSelect().addValueChangeListener(event -> onCompanySelect(event.getValue()));

        this.add(createLibraryHeader(), libraryGrid);
        //this.add(UIFactory.span("header", "Books"), bookGrid);

        libraryGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        reload();
    }

    private Component createLibraryHeader() {
        long librariesTotal = service.countLibraries();
        libraryCounter.setText(librariesTotal + " libraries");
        Component header = UIFactory.headerActionPanel("Libraries", libraryCounter, addLibrary);
        header.getStyle().set("with", "100%");

        return header;
    }


    private void reload() {
        libraryGrid.setItems(query -> service.findAllLibraries(VaadinSpringDataHelpers.toSpringPageRequest(query)));
    }

    private void onCompanySelect(Library library) {
        if (library == null) {
            this.remove(bookGrid);
            this.remove(bookGridHeader);
            return;
        }
        if (bookGridHeader != null) this.remove(bookGridHeader);

        bookGrid.setItems(query -> service.findBooksByLibraryId(library.getId(),
                                                                VaadinSpringDataHelpers.toSpringPageRequest(query)));
        bookGridHeader = createBookHeader(library);

        this.add(bookGridHeader, bookGrid);
    }

    private Component createBookHeader(Library library) {
        long sumBooks = service.countBooksByLibraryId(library.getId());
        bookCounter.setText(sumBooks + " books");
        Component header = UIFactory.headerActionPanel("Books in " + library.getName() + ": ", bookCounter);
        header.getStyle().set("with", "100%");

        return header;
    }

    private void onEdit(Library library) {
        getUI().ifPresent(ui ->
                            ui.navigate( LibraryEditView.class,
                            new RouteParameters( "id", String.valueOf( library.getId() ) )));
    }

    private void onDelete(Library library) {

    }

    private void onCreate() {

    }
}
