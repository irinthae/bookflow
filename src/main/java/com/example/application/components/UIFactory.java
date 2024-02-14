package com.example.application.components;

import com.example.application.Application;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Library;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class UIFactory {

    private static final String BORDER = "border";
    private static final String BORDER_LUMO_PRIMARY_COLOR = "1px solid var(--lumo-primary-color)";

    private UIFactory() {}

    // -- BUTTONS ------------------------------------------------------------------------------------------------------

    public static Button btnPrimary(String text, ComponentEventListener<ClickEvent<Button>> listener) {
        return UIFactory.btn(text, listener, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button btnPrimary(String text, Component icon, ComponentEventListener<ClickEvent<Button>> listener) {
        return UIFactory.btn(text, icon, listener, ButtonVariant.LUMO_PRIMARY);
    }

    public static Button btnTertiary(String text, ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = UIFactory.btn(text, listener, ButtonVariant.LUMO_TERTIARY);
        button.getStyle().set(BORDER, BORDER_LUMO_PRIMARY_COLOR);
        return button;
    }

    public static Button btnTertiary(String text, Component icon, ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = btn(text, icon, listener, ButtonVariant.LUMO_TERTIARY);
        button.getStyle().set(BORDER, BORDER_LUMO_PRIMARY_COLOR);
        return button;
    }

    public static Button btn(String text, Component icon, ComponentEventListener<ClickEvent<Button>> listener, ButtonVariant ... variants) {
        icon.setClassName("button-icon");
        Button button = new Button(text, icon, listener);
        button.addThemeVariants(variants);
        return button;
    }

    public static Button btn(String text, ComponentEventListener<ClickEvent<Button>> listener, ButtonVariant ... variants) {
        Button button = new Button(text, listener);
        button.addThemeVariants(variants);
        return button;
    }

    public static Button btn(String text, ButtonVariant ... variants) {
        Button button =  new Button(text);
        button.addThemeVariants(variants);
        return button;
    }

    public static Button btn(String text, ComponentEventListener<ClickEvent<Button>> listener) {
        return new Button(text, listener);
    }

    public static Component buttonLayout(Button... actions) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.add(actions);
        return buttonLayout;
    }

    // -- GRID ---------------------------------------------------------------------------------------------------------

    public static Component gridColumnAction(Component ... components) {
        Span actionColumn = new Span(components);
        actionColumn.setClassName("action-column");
        return actionColumn;
    }

    public static Button gridBtn(Component icon) {
        Button button = new Button(icon);
        button.setClassName("grid-button");
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return button;
    }

    public static Button gridBtn(Component icon, ComponentEventListener<ClickEvent<Button>> listener, ButtonVariant ... variants) {
        Button button = UIFactory.gridBtn(icon);
        button.addClickListener(listener);
        button.addThemeVariants(variants);
        return button;
    }

    public static <T> void gridActionColumn(Grid<T> grid, ValueProvider<T, Component> provider) {
        grid.addComponentColumn(provider)
                .setAutoWidth(true)
                .setFrozenToEnd(true)
                .setTextAlign(ColumnTextAlign.END)
                .setHeader("Actions");
    }

    public static <T> RouteParameters paramOf(String param, T value) {
        return new RouteParameters(param, value != null ? String.valueOf( value ) : "");
    }


    public static void gridInfoColumn(Grid<Book> grid) {
        grid.addComponentColumn(book -> {
                    Span bookTitle = new Span(book.getTitle());
                    bookTitle.setClassName("book-title");
                    Span bookAuthor = new Span(book.getAuthor());
                    bookAuthor.setClassName("book-author");
                    Span bookData = new Span(bookTitle, bookAuthor);
                    bookData.setClassName("book-data");

                    Span profileColumn = new Span(UIFactory.loadImage(book.getTitle(), book.getImageUrl()), bookData);
                    profileColumn.setClassName("info-column");

                    return profileColumn;
                })
                .setAutoWidth(true)
                .setFrozen(true)
                .setTextAlign(ColumnTextAlign.CENTER);
    }

    // -- NOTIFICATION -------------------------------------------------------------------------------------------------

    public enum NotifyType {
        INFO,
        SUCCESS,
        ERROR,
        WARN
    }

    public static Notification notify(String header, String message, NotifyType type) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_END);
        notification.setDuration(4000);
        Icon icon;
        String color;

        switch (type) {
            case INFO           -> { color = "#81b2b2"; icon = VaadinIcon.INFO_CIRCLE.create(); }
            case WARN, ERROR    -> { color = "var(--lumo-error-color)"; icon = VaadinIcon.WARNING.create(); }
            default             -> { color = "var(--lumo-success-color)"; icon = VaadinIcon.CHECK_CIRCLE.create(); }
        }
        icon.setColor(color);

        Div messageContainer = new Div(new Text(header));
        Div info = new Div(messageContainer, new Div(new Text( message )));
        HorizontalLayout layout = new HorizontalLayout(icon, info);

        messageContainer.getStyle().set("font-weight", "600").set("color", color);
        info.getStyle().set("font-size", "var(--lumo-font-size-m)").set("color", "var(--lumo-secondary-text-color)");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    //TODO
    public static Dialog deleteDialog(String object) {
        Dialog dialog = new Dialog();
        Paragraph paragraph = new Paragraph("Do you really want to delete " + object + " ?");
        VerticalLayout dialogLayout = new VerticalLayout(paragraph);

        dialogLayout.setPadding(true);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        dialog.add(dialogLayout);

        return dialog;
    }

    // -- PANELS -------------------------------------------------------------------------------------------------------

    public static Component footerPanel(Component leftComponent, Button ... actions) {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setClassName("header-action-panel");
        panel.add( leftComponent , new Span( actions ) );
        return panel;
    }

    public static Component headerActionPanel(String header, String description, Component... actions) {
        return headerActionPanel(header, new Span(description), actions);
    }

    public static Component headerActionPanel(String header, Component description, Component... actions) {
        Span headerSpan = UIFactory.span("header", header);
        Span descriptionSpan = UIFactory.span("description", description);
        Span infoContainer = UIFactory.span("info-container", headerSpan, descriptionSpan);

        for(int i = 0; i < actions.length; i++) {
            if(i < actions.length - 1)
                actions[i].getStyle().set("margin-right", "10px");
        }

        Span actionContainer = UIFactory.span("action-container", actions);

        Div headerActionContainer = new Div(infoContainer, actionContainer);
        headerActionContainer.setClassName("header-action-panel");

        return headerActionContainer;
    }

    public static Component headerActionPanel(String header, Component description) {
        Span headerSpan = UIFactory.span("sub-header", header);
        Span descriptionSpan = UIFactory.span("description", description);
        Span infoContainer = UIFactory.span("info-container", headerSpan, descriptionSpan);

        Div headerActionContainer = new Div(infoContainer);
        headerActionContainer.setClassName("header-action-panel");

        return headerActionContainer;
    }


    // -- SPANS --------------------------------------------------------------------------------------------------------

    public static Span span(String className, String text) {
        Span span = new Span(text);
        span.setClassName(className);
        return span;
    }

    public static Span span(String className, Component ... childs) {
        Span span = new Span();
        if(childs != null && childs.length > 0)
            span.add( childs );

        span.setClassName( className );
        return span;
    }

    // -- NAV LINK -----------------------------------------------------------------------------------------------------

    public static RouterLink createNavItem(Class<? extends Component> view) {
        String label = "K.A.";
        NavTitle navTitle = view.getAnnotation(NavTitle.class);
        if(navTitle != null) {
            label = navTitle.value();
        }

        RouterLink link = new RouterLink(view);
        link.addClassName("nav-item");
        link.setHighlightCondition(HighlightConditions.locationPrefix());
        Span text = UIFactory.span("nav-text", label);
        link.add(text);
        return link;
    }

    // -- IMAGES -------------------------------------------------------------------------------------------------------

    public static Image loadImage(String name, String imageUrl) {
        Image image = new Image();
        loadImageSrc(name, imageUrl, image);
        return image;
    }

    public static void loadImageSrc(String name, String imageUrl, Image image) {
        if(imageUrl == null || imageUrl.isBlank()) {
            image.setSrc(Application.DEFAULT_IMAGE);
            return;
        }
        image.setSrc(new StreamResource(name, () -> {
            try {
                return new FileInputStream(imageUrl);
            } catch (FileNotFoundException e) {
                return new ByteArrayInputStream(new byte[0]);
            }
        }));
    }
}
