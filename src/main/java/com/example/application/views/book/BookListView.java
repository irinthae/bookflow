package com.example.application.views.book;

import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.components.grid.BookGrid;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.example.application.views.library.LibraryEditView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import static org.vaadin.lineawesome.LineAwesomeIcon.SEARCH_SOLID;

@NavTitle("Books")
@PageTitle(value = "Books")
@Route(value = "book", layout = MainLayout.class)
public class BookListView extends VerticalLayout {

    private final Button addBook = UIFactory.btnTertiary("Create", VaadinIcon.PLUS.create(), e -> onCreate());
    private final BookGrid bookGrid = new BookGrid(this::onEdit, this::onDelete);
    private final LibraryService service;
    private final Text bookCounter = new Text("");

    public BookListView(LibraryService service) {
        this.service = service;
        initUI();
    }

    private void initUI() {
        addClassName("page-view");
        setSizeFull();

        bookGrid.setItems(query -> service.findAllBooks(VaadinSpringDataHelpers.toSpringPageRequest(query)));

        add(createHeader());
        add(createSearchField());
        add(bookGrid);
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

    }

    private void onEdit(Book book) {
        getUI().ifPresent(ui ->
                ui.navigate(BookEditView.class, new RouteParameters("id", String.valueOf(book.getId()))));
    }

    private void onDelete(Book book) {

    }


}
