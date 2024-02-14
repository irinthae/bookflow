package com.example.application.views.library;

import com.example.application.Application;
import com.example.application.components.NavTitle;
import com.example.application.components.UIFactory;
import com.example.application.components.form.LibraryForm;
import com.example.application.data.entity.Library;
import com.example.application.data.service.LibraryService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@NavTitle("Create")
@PageTitle(value = "Create Library")
@Route(value = "createlibrary", layout = MainLayout.class)
public class LibraryCreateView extends VerticalLayout {

    private final Button save = UIFactory.btnPrimary("Save", e -> onSave());
    private final Button cancel = UIFactory.btnTertiary("Cancel", e -> onCancel());
    private final LibraryForm libraryForm;
    private final LibraryService service;

    @Autowired
    public LibraryCreateView(LibraryForm libraryForm, LibraryService service) {
        this.libraryForm = libraryForm;
        this.service = service;
    }

    @PostConstruct
    private void initUI() {
        addClassName("page-view");
        setSizeFull();
        libraryForm.setLibrary(new Library());
        Component header = UIFactory.headerActionPanel("Library", "Create a new library.");
        Component buttons = UIFactory.buttonLayout(save, cancel);

        this.add(header, libraryForm, buttons);
    }

    private void onSave() {
        if(libraryForm.isValid()) {
            Library library = libraryForm.getLibrary();
            service.update(library);

            Application.info(library.getName() + " created.");
            getUI().ifPresent(ui -> ui.navigate(LibraryListView.class));
        } else {
            Application.warn("Invalid Data. Pleas check form data");
        }
    }

    private void onCancel() {
        getUI().ifPresent(ui -> ui.getPage().getHistory().back());
    }
}
