package com.example.application.views;

import com.example.application.Application;
import com.example.application.components.UIFactory;
import com.example.application.views.book.BookListView;
import com.example.application.views.library.LibraryListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class MainLayout extends AppLayout {

    private final H2 viewTitleContainer = new H2();

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        initUI();
    }

    private void initUI() {
        addToDrawer(createDrawerHeader());
        addToDrawer(new Scroller(createNavigation()));
        addToDrawer(new Footer());
        addToNavbar(true, createContentHeader());
    }

    private Component createDrawerHeader() {
        H1 appName = new H1(Application.APP_TITLE);
        //appName.getStyle().set("color", "#5FACBF7F");
        Header header = new Header(appName);
        header.getStyle().set("background-color", "#795548");
        header.setHeight("77px");

        return header;
    }

    private Component createNavigation() {
        Div nav = UIFactory.appNav();
        nav.add(
                UIFactory.createNavItem(LibraryListView.class, LineAwesomeIcon.BUILDING_SOLID.create()),
                UIFactory.createNavItem(BookListView.class, LineAwesomeIcon.BOOK_SOLID.create())
        );

        return nav;
    }

    private Component createContentHeader() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.setWidthFull();
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.getStyle().set("background-color", "#c4bcb9");
        layout.setHeight("77px");

        DrawerToggle toggle = new DrawerToggle();
        toggle.getStyle().set("color", "#212121");

        layout.add(toggle, viewTitleContainer);

        return layout;
    }


    // -- READ BREADCRUMB ----------------------------------------------------------------------------------------------
    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        String[] elements = getCurrentPageTitle().split("\\|");
        viewTitleContainer.removeAll();
        int length = elements.length;

        // Create Breadcrumb e.g: Employee > Edit - set css arrow between elements
        for (int i = 0; i < length; i++) {
            viewTitleContainer.add(elements[i]);
            if (i < length - 1) {
                Span arrow = new Span();
                arrow.addClassNames("arrow", "arrow-right");
                viewTitleContainer.add(arrow);
            }
        }
    }

    private String getCurrentPageTitle() {
        Component content = getContent();
        if (content instanceof HasDynamicTitle dynamicTitle) {
            return dynamicTitle.getPageTitle();
        }

        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}

