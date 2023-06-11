package com.example.application;

import com.example.application.components.UIFactory;
import com.example.application.data.repository.LibraryRepository;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@SpringBootApplication
@NpmPackage(value = "@fontsource/roboto", version = "4.5.0")
@Theme(value = "bookflow")
public class Application implements AppShellConfigurator {

    public static final String APP_TITLE = "BOOKFLOW";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile("default")
    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource,
                                                                               SqlInitializationProperties properties,
                                                                               LibraryRepository repository) {

        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                if (repository.count() == 0L)
                    return super.initializeDatabase();
                return false;
            }
        };
    }

    public static void info(String text) {
        UIFactory.notify("Info", text, UIFactory.NotifyType.INFO).open();
    }

    public static void success(String text) {
        success("Success", text);
    }

    public static void success(String header, String text) {
        UIFactory.notify(header, text, UIFactory.NotifyType.SUCCESS).open();
    }

    public static void warn(String text) {
        UIFactory.notify("Warn", text, UIFactory.NotifyType.WARN).open();
    }

    public static void error(String text) {
        UIFactory.notify("Error", text, UIFactory.NotifyType.ERROR).open();
    }
}
