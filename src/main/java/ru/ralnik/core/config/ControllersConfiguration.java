/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.core.config;

import javafx.fxml.FXMLLoader;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ralnik.core.config.entity.ViewHolder;
import ru.ralnik.main.controller.clientform.FXMLAddClientFormController;
import ru.ralnik.main.controller.clientform.FXMLClientFormController;
import ru.ralnik.main.controller.mainform.FXMLMainFormController;
import ru.ralnik.main.controller.positionform.FXMLPositionFormController;
import ru.ralnik.main.controller.roomsform.FXMLRoomsFormController;
import ru.ralnik.main.controller.roomtype.FXMLRoomTypeFormController;

import java.io.InputStream;

@Configuration
public class ControllersConfiguration {

    @Bean(name = "mainView")
    public ViewHolder getMainView() {
        return loadView("ru/ralnik/main/mainform/MainFormScene.fxml");
    }

    @Bean(name = "roomtypeform")
    public ViewHolder getRoomTypeView() {
        return loadView("ru/ralnik/main/roomtypeforms/RoomTypeForm.fxml");
    }

    @Bean(name = "roomsform")
    public ViewHolder getRoomsView() {
        return loadView("ru/ralnik/main/roomsforms/RoomsForm.fxml");
    }

    @Bean(name = "positionsform")
    public ViewHolder getPositionsView() {
        return loadView("ru/ralnik/main/positionforms/PositionForm.fxml");
    }

    @Bean(name = "clientform")
    public ViewHolder getClientView() {
        return loadView("ru/ralnik/main/clientforms/ClientForm.fxml");
    }

    @Bean(name = "addclientform")
    public ViewHolder getAddClientView() {
        return loadView("ru/ralnik/main/clientforms/AddClientForm.fxml");
    }

    /**
     * Именно благодаря этому методу мы добавили контроллер в контекст спринга,
     * и заставили его произвести все необходимые инъекции.
     */
    @Bean
    public FXMLMainFormController getMainController() {
        return (FXMLMainFormController) getMainView().getController();
    }

    @Bean
    public FXMLRoomsFormController getRoomsController() {
        return (FXMLRoomsFormController) getRoomsView().getController();
    }

    @Bean
    public FXMLRoomTypeFormController getRoomTypeController() {
        return (FXMLRoomTypeFormController) getRoomTypeView().getController();
    }

    @Bean
    public FXMLPositionFormController getPositionsController() {
        return (FXMLPositionFormController) getPositionsView().getController();
    }

    @Bean
    public FXMLClientFormController getClientFormController() {
        return (FXMLClientFormController) getClientView().getController();
    }

    @Bean
    public FXMLAddClientFormController getAddClientFormController() {
        return (FXMLAddClientFormController) getAddClientView().getController();
    }

    @SneakyThrows
    public ViewHolder loadView(String url) {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }
}
