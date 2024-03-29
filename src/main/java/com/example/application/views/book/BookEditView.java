package com.example.application.views.book;

import com.example.application.Application;
import com.example.application.components.UIFactory;
import com.example.application.components.form.BookForm;
import com.example.application.data.entity.Book;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Edit Book")
@Route(value = "books/edit/:id", layout = MainLayout.class)
public class BookEditView extends Div implements BeforeEnterObserver {

    private final Button save = UIFactory.btnPrimary("Save", e -> onSave());
    private final Button cancel = UIFactory.btnTertiary("Cancel", e -> onCancel());
    private final BookForm bookForm;
    private final LibraryService service;

    @Autowired
    public BookEditView(BookForm bookForm, LibraryService service) {
        this.bookForm = bookForm;
        this.service = service;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        Component header = UIFactory.headerActionPanel("Book", "Edit current book.");
        Component buttons = UIFactory.buttonLayout(save, cancel);

        this.add(header, bookForm, buttons);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().getLong("id").ifPresent(this::setParameter);
    }

    private void setParameter(Long bookId) {
        service.findBookById(bookId).ifPresentOrElse(bookForm::setBook,
                                                     () -> { Application.error("No book found."); onCancel(); }
        );
        bookForm.setLibraryLabelsValue(service.findLibraryByBookId(bookId).getName());
    }

    private void onCancel() {
        getUI().ifPresent(ui -> ui.getPage().getHistory().back());
    }

    private void onSave() {
        if(bookForm.isValid() ) {
            Book book = bookForm.getBook();
            service.update(book);
            Long library = service.findLibraryIdByName(bookForm.getLibraryLabelsValue());
            service.updateReference(book.getId(), library);

            Application.success(book.getTitle() + " updated.");
            getUI().ifPresent(ui -> ui.getPage().getHistory().back());
        } else {
            Application.warn("Invalid Data. Pleas check form data");
        }
    }

}
