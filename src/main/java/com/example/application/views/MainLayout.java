package com.example.application.views;

import com.example.application.components.UIFactory;
import com.example.application.views.book.BookCreateView;
import com.example.application.views.book.BookListView;
import com.example.application.views.landingPage.LandingPageView;
import com.example.application.views.library.LibraryCreateView;
import com.example.application.views.library.LibraryListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainLayout extends AppLayout {

    public MainLayout() {
        initUI();
    }

    private void initUI() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setPadding(true);
        Component menuBar = createMenuBar();
        Component logo = createApplicationTitle();

        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        layout.setAlignSelf(FlexComponent.Alignment.START, logo);
        layout.setAlignSelf(FlexComponent.Alignment.END, menuBar);

        layout.add(menuBar, logo, creatPlaceHolder());

        return layout;
    }

    private Component createApplicationTitle() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("30%");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setClassName("app-title");
        Image image = new Image("images/logo.png", "image");

        layout.setHeight("80px");
        layout.getStyle().set("cursor", "pointer");
        layout.addClickListener(e -> backToLandingPage());

        layout.add(image);

        return layout;
    }

    private Component createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        //menuBar.setOpenOnHover(true);
        menuBar.setWidth("30%");
        MenuItem library = menuBar.addItem("Library");
        library.addClassName("menu-item");
        MenuItem book = menuBar.addItem("Books");
        book.addClassName("menu-item");

        SubMenu librarySubMenu = library.getSubMenu();
        librarySubMenu.addItem(UIFactory.createNavItem(LibraryListView.class));
        librarySubMenu.addItem(UIFactory.createNavItem(LibraryCreateView.class));

        SubMenu bokSubMenu = book.getSubMenu();
        bokSubMenu.addItem(UIFactory.createNavItem(BookListView.class));
        bokSubMenu.addItem(UIFactory.createNavItem(BookCreateView.class));

        return menuBar;
    }

    private Component creatPlaceHolder() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("30%");

        return  layout;
    }

    private void backToLandingPage() {
        getUI().ifPresent(ui -> ui.navigate(LandingPageView.class));
    }
}

