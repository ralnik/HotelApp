/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ralnik.core.config.ControllersConfiguration;


@SpringBootApplication
public class HotelApplication extends AbsractJavaFxApplicationSupport {
    @Value("${ui.title}")
    private String titleApp;

    @Qualifier("mainView")
    @Autowired
    private ControllersConfiguration.ViewHolder view;


    public static void main(String[] args) {
        launchApp(HotelApplication.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(titleApp);
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
}
