package com.example.application.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIFactory {

    private static final String BORDER = "border";
    private static final String BORDER_LUMO_PRIMARY_COLOR = "1px solid var(--lumo-primary-color)";

    private UIFactory() {}


    // -- BUTTONS ------------------------------------------------------------------------------------------------------

    public static Button btnPrimary(String text) {
        return UIFactory.btn(text, ButtonVariant.LUMO_PRIMARY);
    }
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

    public static Button btnIcon(Component icon, ComponentEventListener<ClickEvent<Button>> listener, ButtonVariant ... variants) {
        Button button = new Button(icon, listener);
        button.setClassName("icon-button");
        button.addThemeVariants(variants);
        return button;
    }

    public static Button btn(String text, ComponentEventListener<ClickEvent<Button>> listener, ButtonVariant ... variants) {
        Button button = new Button( text, listener );
        button.addThemeVariants( variants );
        return button;
    }

    public static Button btn(String text, ButtonVariant ... variants) {
        Button button =  new Button(text);
        button.addThemeVariants(variants);
        return button;
    }

    public static Button btn(String text, ComponentEventListener<ClickEvent<Button>> listener) {
        return new Button( text, listener );
    }


    public static Component buttonLayout(Button... actions) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.add(actions);
        return buttonLayout;
    }


    // -- TABLE --------------------------------------------------------------------------------------------------------
    public static Component tableColumnAction(Component ... components) {
        Span actionColumn = new Span( components );
        actionColumn.setClassName("action-column");
        return actionColumn;
    }

    public static Button tableBtn( Component icon ) {
        Button button = new Button( icon );
        button.setClassName("table-button");
        button.addThemeVariants( ButtonVariant.LUMO_TERTIARY );
        return button;
    }

    public static Button tableBtn( Component icon, ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = UIFactory.tableBtn( icon );
        button.addClickListener( listener );
        return button;
    }

    public static <T> void tableActionColumn(Grid<T> grid, ValueProvider<T, Component> provider) {
        grid.addComponentColumn( provider )
                .setAutoWidth(true)
                .setFrozenToEnd(true)
                .setTextAlign( ColumnTextAlign.END );
    }


    public static <T> RouteParameters paramOf(String param, T value) {
        return new RouteParameters( param, value != null ? String.valueOf( value ) : "" );
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
            case INFO           -> { color = "var(--lumo-primary-color)"; icon = VaadinIcon.INFO_CIRCLE.create(); }
            case WARN, ERROR    -> { color = "var(--lumo-error-color)"; icon = VaadinIcon.WARNING.create(); }
            default             -> { color = "var(--lumo-success-color)"; icon = VaadinIcon.CHECK_CIRCLE.create(); }
        }
        icon.setColor(color);

        Div messageContainer = new Div(new Text(header));
        messageContainer.getStyle().set("font-weight", "600").set("color", color);
        Div info = new Div(messageContainer, new Div(new Text( message )));
        info.getStyle().set("font-size", "var(--lumo-font-size-m)").set("color", "var(--lumo-secondary-text-color)");
        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
        return notification;
    }



    // -- PANELS -------------------------------------------------------------------------------------------------------

    public static Component footerPanel(Component leftComponent, Button ... actions) {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setClassName("header-action-panel");
        panel.add( leftComponent , new Span( actions ) );
        return panel;
    }

    public static Component headerActionPanel(String header, String description, Component... actions) {
        return headerActionPanel(header, new Span( description ), actions);
    }

    public static Component headerActionPanel(String header, Component description, Component... actions) {

        Span headerSpan = UIFactory.span("header", header);
        Span descriptionSpan = UIFactory.span( "description", description );
        Span infoContainer = UIFactory.span( "info-container", headerSpan, descriptionSpan );

        for(int i = 0; i < actions.length; i++) {
            if(i < actions.length - 1)
                actions[i].getStyle().set("margin-right", "10px");
        }

        Span actionContainer = UIFactory.span( "action-container", actions );

        Div headerActionContainer = new Div( infoContainer, actionContainer );
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
    public static Div appNav() {
        Div nav = new Div();
        nav.addClassName("nav-container");
        return nav;
    }

    public static RouterLink createNavItem(Class<? extends Component> view, Component icon) {

        String label = "K.A.";
        NavTitle navTitle = view.getAnnotation( NavTitle.class );
        if(navTitle != null) {
            label = navTitle.value();
        }

        RouterLink link = new RouterLink(view);
        link.addClassName("nav-item");
        link.setHighlightCondition(HighlightConditions.locationPrefix());
        Span text = UIFactory.span("nav-text", label);
        Span iconSpan = UIFactory.span("nav-icon", icon);
        link.add(iconSpan, text);
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
            image.setSrc("data/images/default.png");
            return;
        }
        image.setSrc(new StreamResource(name, () -> {
            try {
                return new FileInputStream( imageUrl );
            } catch (FileNotFoundException e) {
                return new ByteArrayInputStream(new byte[0]);
            }
        }));
    }

}
