/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.main.controller.clientform;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.ralnik.core.config.entity.ViewHolder;
import ru.ralnik.core.db.entity.Client;
import ru.ralnik.core.db.entity.RecieptionList;
import ru.ralnik.core.db.repository.ClientRepository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class FXMLClientFormController implements Initializable {
    @Autowired
    private ClientRepository clientRepository;
    @Qualifier("addclientform")
    @Autowired
    private ViewHolder addClientView;
    @Value("ui.addClientTitle")
    private String addClientFormTitle;

    @FXML
    private TableView<Client> clientTableView;

    private Stage addClientStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Do nothing
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<Client> clientList = clientRepository.findAll();
        ObservableList<Client> clientObservableList = FXCollections.observableList(clientList);

        TableColumn<Client, Long> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Client, String> fioClientCol = new TableColumn("ФИО");
        fioClientCol.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        param.getValue().getFam() + " " +
                                param.getValue().getIm() + " " +
                                param.getValue().getOt()
                )
        );

        TableColumn<Client, String> pasportClient = new TableColumn<>("Паспорт");
        pasportClient.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        "серия: " + param.getValue().getPasportSerial() + " " +
                                " номер: " + param.getValue().getPasportNumber() + " " +
                                "дата выдачи: " + param.getValue().getPasportRecievedDate() + " " +
                                " кем выдан: " + param.getValue().getPasportRecievedPlace()
                )
        );

        TableColumn clientCol = new TableColumn("Данные клиента");
        clientCol.getColumns().addAll(fioClientCol, pasportClient);

        clientTableView.getColumns().clear();
        clientTableView.getColumns().setAll(idColumn, clientCol);
        clientTableView.setItems(clientObservableList);
    }

    public void buttonAddOnClick() {
        if (addClientStage == null) {
            addClientStage = new Stage();
            addClientStage.setTitle(addClientFormTitle);
            addClientStage.setScene(new Scene(addClientView.getView()));

            addClientStage.setResizable(true);
            addClientStage.centerOnScreen();
        }
        addClientStage.show();
        addClientStage.setOnCloseRequest(event -> addClientStage.hide());
    }

    public void buttonEditOnClick() {

    }

    public void buttonDeleteOnClick() {

    }

    public void doubleClickTablePosition() {

    }


}
