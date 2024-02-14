package com.example.application.views.library;

import com.example.application.Application;
import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.components.grid.LibraryGrid;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.example.application.components.grid.BookGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@NavTitle("Edit")
@PageTitle(value = "Library")
@Route(value = "librarylist", layout = MainLayout.class)
public class LibraryListView extends VerticalLayout {

    private final Button addLibrary = UIFactory.btnTertiary("Create", VaadinIcon.PLUS.create(), e -> onCreate());
    private final LibraryGrid libraryGrid;
    private final Text libraryCounter = new Text("");
    private final Text bookCounter = new Text("");
    private final BookGrid bookGrid;
    private Component bookGridHeader;

    private final LibraryService service;

    @Autowired
    public LibraryListView(LibraryService service, LibraryGrid libraryGrid, BookGrid bookGrid) {
        this.service = service;
        this.libraryGrid = libraryGrid;
        this.bookGrid = bookGrid;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        libraryGrid.addActionColumns(this::onEdit, this::onDelete);
        libraryGrid.addSelectionListener(this::onLibrarySelect);

        this.add(createLibraryHeader(), libraryGrid);
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

    private void onLibrarySelect(Library library) {
        if (library == null) {
            this.remove(bookGrid);
            this.remove(bookGridHeader);
            return;
        }
        if (bookGridHeader != null) this.remove(bookGridHeader);

        bookGrid.setItems(query -> service.findBooksByLibraryId(library.getId(),
                                                                VaadinSpringDataHelpers.toSpringPageRequest(query)));
        bookGridHeader = createBookHeader(library);
        bookGrid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);

        this.add(bookGridHeader, bookGrid);
    }

    private Component createBookHeader(Library library) {
        long sumBooks = service.countBooksByLibraryId(library.getId());
        bookCounter.setText(sumBooks + " books");
        Component header = UIFactory.headerActionPanel("Books in " + library.getName(), bookCounter);
        header.getStyle().set("with", "100%");

        return header;
    }

    private void onEdit(Library library) {
        getUI().ifPresent(ui ->
                            ui.navigate( LibraryEditView.class,
                            UIFactory.paramOf("id", String.valueOf(library.getId()))));
    }


    private void onDelete(Library library) {
        Dialog dialog = UIFactory.deleteDialog(library.getName());
        Button yes = UIFactory.btnPrimary("Yes", event -> onDeleteAck(library, dialog));
        Button no = UIFactory.btnTertiary("No", event -> dialog.close());
        Component buttonLayout = UIFactory.buttonLayout(yes, no);
        dialog.add(buttonLayout);
        dialog.open();
    }

    private void onDeleteAck(Library library, Dialog dialog) {
        try {
            dialog.close();
            service.delete(library);
            reload();
            libraryCounter.setText(service.countLibraries() + "  libraries");
            Application.info("Library " + library.getName() + " deleted.");
        } catch (Exception e) {
            Application.error(e.getMessage());
        }
    }

    private void onCreate() {
        getUI().ifPresent(ui -> ui.navigate(LibraryCreateView.class));
    }
}
