package com.example.application.views.landingPage;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.PostConstruct;

@PageTitle(value = "Bookflow")
@Route(value = "bookflow", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LandingPageView extends VerticalLayout {
    private final Image image;
    private final VerticalLayout content;

    public LandingPageView() {
        this.image = new Image("images/landing-page.webp", "Image");
        this. content = new VerticalLayout();
    }

    @PostConstruct
    private void initUI() {
        setSizeFull();
        setPadding(false);

        UI.getCurrent().getPage().retrieveExtendedClientDetails(receiver -> {
            int screenWidth = receiver.getScreenWidth();
            int screenHeight = receiver.getScreenHeight();
            image.setWidth((float) screenWidth, Unit.PIXELS);
            image.setHeight((float) screenHeight - 120, Unit.PIXELS);
        });

        content.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.add(image);

        this.add(content);
    }
}
