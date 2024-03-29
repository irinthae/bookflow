package com.example.application.views.library;

import com.example.application.Application;
import com.example.application.components.UIFactory;
import com.example.application.components.form.LibraryForm;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.example.application.views.book.BookListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("EditLibrary")
@Route(value = "libraries/edit/:id", layout = MainLayout.class)
public class LibraryEditView extends VerticalLayout implements BeforeEnterObserver {

    private final Button save = UIFactory.btnPrimary("Save", e -> onSave());
    private final Button cancel = UIFactory.btnTertiary("Cancel", e -> onCancel());
    private final LibraryForm libraryForm;
    private final LibraryService service;

    @Autowired
    public LibraryEditView(LibraryService service, LibraryForm libraryForm) {
        this.libraryForm = libraryForm;
        this.service = service;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        Component header = UIFactory.headerActionPanel("Library", "Edit current library.");
        Component buttons = UIFactory.buttonLayout(save, cancel);
        add(header, libraryForm, buttons);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().getLong("id")
                .flatMap(service::findLibraryById)
                .ifPresent(libraryForm::setLibrary);
    }

    private void onSave() {
        if (libraryForm.isValid()) {
            Library library = libraryForm.getLibrary();
            service.update(library);
            Application.success(library.getName() + " updated");
            getUI().ifPresent( ui -> ui.navigate(LibraryListView.class));
        } else {
            Application.warn("Data not valid. Please check your data.");
        }
    }

    private void onCancel() {
        getUI().ifPresent(ui -> ui.getPage().getHistory().back());
    }
}
