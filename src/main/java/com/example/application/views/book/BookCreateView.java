package com.example.application.views.book;

import com.example.application.Application;
import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.components.form.BookForm;
import com.example.application.data.entity.Book;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@NavTitle("Create")
@PageTitle(value = "Create Book")
@Route(value = "create", layout = MainLayout.class)
public class BookCreateView extends Div {

    private final Button save = UIFactory.btnPrimary("Save", e -> onSave());
    private final Button cancel = UIFactory.btnTertiary("Cancel", e -> onCancel());
    private final BookForm bookForm;
    private final LibraryService service;

    @Autowired
    public BookCreateView(BookForm bookForm, LibraryService service) {
        this.bookForm = bookForm;
        this.service = service;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        bookForm.setBook(new Book());
        Component header = UIFactory.headerActionPanel("Book", "Create a new book.");
        Component buttons = UIFactory.buttonLayout(save, cancel);

        this.add(header, bookForm, buttons);
    }

    private void onSave() {
        if(bookForm.isValid()) {
            Book book = bookForm.getBook();
            service.update(book);
            Long library = service.findLibraryIdByName(bookForm.getLibraryLabelsValue());
            service.updateReference(book.getId(), library);

            Application.info(book.getTitle() + " created.");
            getUI().ifPresent(ui -> ui.navigate(BookListView.class));
        } else {
            Application.warn("Invalid Data. Pleas check form data");
        }
    }

    private void onCancel() {
        getUI().ifPresent(ui -> ui.getPage().getHistory().back());
    }
}