package com.example.application.views.book;

import com.example.application.Application;
import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.components.grid.BookGrid;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import static org.vaadin.lineawesome.LineAwesomeIcon.SEARCH_SOLID;

@NavTitle("Edit")
@PageTitle(value = "Books")
@Route(value = "books", layout = MainLayout.class)
public class BookListView extends VerticalLayout {

    private final Button addBook = UIFactory.btnTertiary("Create", VaadinIcon.PLUS.create(), e -> onCreate());
    private final Text bookCounter = new Text("");
    private final BookGrid bookGrid;
    private final LibraryService service;

    @Autowired
    public BookListView(LibraryService service, BookGrid bookGrid) {
        this.bookGrid = bookGrid;
        this.service = service;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        bookGrid.addDetailColumns();
        bookGrid.addActionColumns(this::onEdit, this::onDelete);
        bookGrid.setItems(query -> service.findAllBooks(VaadinSpringDataHelpers.toSpringPageRequest(query)));

        this.add(createHeader(), createSearchField(), bookGrid);
    }

    private Component createHeader() {
        long booksTotal = service.countBooks();

        bookCounter.setText(booksTotal + " books");
        Component header = UIFactory.headerActionPanel("Books", bookCounter, addBook);
        header.getStyle().set("with", "100%");

        return header;
    }

    private Component createSearchField() {
        TextField searchField = new TextField();

        searchField.setWidth("50%");
        searchField.setPlaceholder("Search by title or author");
        searchField.setPrefixComponent(SEARCH_SOLID.create());
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> reload(e.getValue()));

        return searchField;
    }

    private void reload(String keyword) {
        bookGrid.setItems(query -> service.findBooks(keyword, query));
    }

    private void onCreate() {
        getUI().ifPresent(ui -> ui.navigate(BookCreateView.class));
    }

    private void onEdit(Book book) {
        getUI().ifPresent(ui ->
                ui.navigate(BookEditView.class, UIFactory.paramOf("id", String.valueOf(book.getId()))));
    }

    private void onDelete(Book book) {
        Dialog dialog = UIFactory.deleteDialog(book.getTitle());
        Button yes = UIFactory.btnPrimary("Yes", event -> onDeleteAck(book, dialog));
        Button no = UIFactory.btnTertiary("No", event -> dialog.close());
        Component buttonLayout = UIFactory.buttonLayout(yes, no);
        dialog.add(buttonLayout);
        dialog.open();
    }

    private void onDeleteAck(Book book, Dialog dialog) {
        try {
            dialog.close();
            service.delete(book);
            bookGrid.getDataProvider().refreshAll();
            bookCounter.setText(service.countBooks() + "  books");
            Application.info("Book " + book.getTitle() + " deleted.");
        } catch (Exception e) {
            Application.error(e.getMessage());
        }
    }
}
