package com.example.application.views.book;

import com.example.application.Application;
import com.example.application.components.UIFactory;
import com.example.application.components.form.BookForm;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Edit Book")
@Route(value = "books/edit/:id", layout = MainLayout.class)
public class BookEditView extends Div implements BeforeEnterObserver {

    private final com.vaadin.flow.component.button.Button save = UIFactory.btnPrimary("Save", e -> onSave());
    private final Button cancel = UIFactory.btnTertiary("Cancel", e -> onCancel());

    private final ComboBox<Library> test = new ComboBox<>();

    private final BookForm bookForm;
    private final LibraryService service;


    @Autowired
    public BookEditView(BookForm bookForm, LibraryService service) {
        this.bookForm = bookForm;
        this.service = service;
        initUI();
    }

    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        Component header = UIFactory.headerActionPanel("Book", "Edit current book.");
        add(header);
        add(bookForm);
        add(UIFactory.buttonLayout(save, cancel));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().getLong( "id").ifPresent(this::setParameter);
    }

    private void setParameter(Long bookId) {
        service.findBookById(bookId).ifPresentOrElse(
                                        bookForm::setBook,
                                        () -> { Application.error("No employee found."); onCancel(); }
        );

        //TODO
        bookForm.libraryBook.setItems((query -> service.findAllLibraries(VaadinSpringDataHelpers.toSpringPageRequest(query))));
        bookForm.libraryBook.setValue(service.findLibraryByBookId(bookId));
    }

    private void onCancel() {
        getUI().ifPresent(ui -> ui.getPage().getHistory().back());
    }

    private void onSave() {
        if(bookForm.isValid() ) {
            Book book = bookForm.getBook();
            service.update(book);
            Application.success(book.getTitle() + " updated.");
            getUI().ifPresent( ui -> ui.navigate(BookListView.class));
        } else {
            Application.warn("Invalid Data. Pleas check form data");
        }
    }

}
