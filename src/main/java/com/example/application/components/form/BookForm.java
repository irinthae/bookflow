package com.example.application.components.form;

import com.example.application.Application;
import com.example.application.components.UIFactory;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Category;
import com.example.application.data.entity.Language;
import com.example.application.data.service.LibraryService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookForm extends Div {
    private final Image image = new Image();
    private final TextField imageUrl = new TextField("Image");
    private final TextField title = new TextField("Title");
    private final TextField author = new TextField("Author");
    private final ComboBox<Language> language1 = new ComboBox<>("Language");
    private final IntegerField yearOfPublication = new IntegerField("Year of publication");
    private final IntegerField pages = new IntegerField("Pages");
    private final ComboBox<Category> category = new ComboBox<>("Category");
    private final TextArea content = new TextArea("Content");
    private final ComboBox<String> libraryLabels = new ComboBox<>("Library");

    private final Binder<Book> binder = new BeanValidationBinder<>(Book.class);
    private final LibraryService service;

    private Upload upload;

    @Autowired
    public BookForm(LibraryService service) {
        this.service = service;
    }

    @PostConstruct
    private void init() {
        addClassName("page-view");

        setComboboxes();
        content.getElement().setAttribute("colspan", "2");
        content.setHeight("300px");

        this.add(createUploadForm(), new FormLayout(title, author, language1, yearOfPublication, pages, category, libraryLabels, content));

        binder.bindInstanceFields(this);
    }

    private void setComboboxes() {
        language1.setItems(Language.values());
        category.setItems(Category.values());
        libraryLabels.setItems(query -> service.findLibraryLabels(VaadinSpringDataHelpers.toSpringPageRequest(query)));
    }

    private HorizontalLayout createUploadForm() {
        image.addClassName("image-upload");

        FileBuffer fileBuffer = new FileBuffer(fileName -> new File("data/images/", fileName).getCanonicalFile());
        upload = new Upload(fileBuffer);
        upload.addFinishedListener(this::onUploadFinished);
        upload.setWidthFull();
        upload.setMaxFileSize(5000000);

        HorizontalLayout profileLayout = new HorizontalLayout(image, upload);
        profileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        profileLayout.setAlignSelf(FlexComponent.Alignment.START, image);
        profileLayout.setAlignSelf(FlexComponent.Alignment.END, upload);
        imageUrl.setEnabled(false);

        return profileLayout;
    }

    private void onUploadFinished(FinishedEvent finishedEvent) {
        String fileName = finishedEvent.getFileName();
        String imageSrc = "data/images/" + fileName;
        String name = title.getValue();
        setImage(name, imageSrc);
    }

    private void setImage(String name, String imageSrc) {
        UIFactory.loadImageSrc(name, imageSrc, image);
        imageUrl.setValue(imageSrc);
    }

    public Book getBook() {
        return binder.getBean();
    }

    public void setBook(Book book) {
        if(book == null) {
            Application.error("Book must not be null!");
            return;
        }
        binder.setBean(book);
        setImage(book.getTitle(), book.getImageUrl() );
    }

    public String getLibraryLabelsValue() {
        return libraryLabels.getValue();
    }

    public void setLibraryLabelsValue(String value) {
        libraryLabels.setValue(value);
    }

    public boolean isValid() {
        binder.validate();
        return binder.isValid() && this.libraryLabels.getValue() != null;
    }

    public void refresh() {
        binder.readBean(new Book());
        upload.clearFileList();
        image.setSrc(Application.DEFAULT_IMAGE);
    }
}
