/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.main.controller.mainform;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.ralnik.core.config.entity.ViewHolder;
import ru.ralnik.core.db.entity.RecieptionList;
import ru.ralnik.core.db.entity.Reserved;
import ru.ralnik.core.db.repository.RecieptionListRepository;
import ru.ralnik.core.db.repository.ReservedRepository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class FXMLMainFormController {

    @Value("${ui.roomTypeFormTitle}")
    private String roomTypeFormTitle;
    @Value("${ui.roomFormTitle}")
    private String roomFormTitle;
    @Value("${ui.positionTitle}")
    private String positionTitle;

    @Autowired
    private RecieptionListRepository recieptionListRepository;
    @Autowired
    private ReservedRepository reservedRepository;

    @Qualifier("roomtypeform")
    @Autowired
    private ViewHolder roomTypeView;

    @Qualifier("roomsform")
    @Autowired
    private ViewHolder roomView;

    @Qualifier("positionsform")
    @Autowired
    private ViewHolder positionView;

    @FXML
    private TableView<RecieptionList> tableRecieptionList;
    @FXML
    private TableView<Reserved> tableReserveList;
    @FXML
    private MenuItem menuRoomType;

    private Stage roomTypeStage;
    private Stage roomStage;
    private Stage positionStage;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        createTableRecieptionList();
        createTableReserveList();
    }

    private void createTableRecieptionList() {
        List<RecieptionList> recieptionList = recieptionListRepository.findAll();
        ObservableList<RecieptionList> recieptionObservableList = FXCollections.observableArrayList(recieptionList);

        TableColumn<RecieptionList, String> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<RecieptionList, Integer> numberRoomColumn = new TableColumn<>("Номер комнаты");
        numberRoomColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRoom().getNumber()));


        TableColumn<RecieptionList, String> countPersonColumn = new TableColumn<>("Количество человек");
        countPersonColumn.setCellValueFactory(new PropertyValueFactory<>("countPerson"));

        TableColumn<RecieptionList, Date> enterDateColumn = new TableColumn<>("Дата заезда");
        enterDateColumn.setCellValueFactory(new PropertyValueFactory<>("enterDate"));

        TableColumn<RecieptionList, Date> exitDateColumn = new TableColumn<>("Дата выезда");
        exitDateColumn.setCellValueFactory(new PropertyValueFactory<>("exitDate"));

        TableColumn<RecieptionList, Date> registrationDateColumn = new TableColumn<>("Дата Регистрации");
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        TableColumn<RecieptionList, String> famSpecCol = new TableColumn("Специалист");
        famSpecCol.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        param.getValue().getSpecialist().getFam() + " " +
                                param.getValue().getSpecialist().getIm() + " " +
                                param.getValue().getSpecialist().getOt()
                )
        );

        TableColumn<RecieptionList, String> fioClientCol = new TableColumn("ФИО");
        fioClientCol.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        param.getValue().getClient().getFam() + " " +
                                param.getValue().getClient().getIm() + " " +
                                param.getValue().getClient().getOt()
                )
        );

        TableColumn<RecieptionList, String> pasportClient = new TableColumn<>("Паспорт");
        pasportClient.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        "серия: " + param.getValue().getClient().getPasportSerial() + " " +
                                " номер: " + param.getValue().getClient().getPasportNumber() + " " +
                                "дата выдачи: " + param.getValue().getClient().getPasportRecievedDate() + " " +
                                " кем выдан: " + param.getValue().getClient().getPasportRecievedPlace()
                )
        );

        TableColumn<RecieptionList, String> otCol = new TableColumn("Отчество");
        otCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSpecialist().getOt()));

        TableColumn clientCol = new TableColumn("Клиент");
        clientCol.getColumns().addAll(fioClientCol, pasportClient);

        tableRecieptionList.getColumns().addAll(
                idColumn,
                numberRoomColumn,
                countPersonColumn,
                enterDateColumn,
                exitDateColumn,
                registrationDateColumn,
                famSpecCol,
                clientCol
        );
        tableRecieptionList.setItems(recieptionObservableList);
    }

    private void createTableReserveList() {
        List<Reserved> reservedList = reservedRepository.findAll();
        ObservableList<Reserved> reservedObservableList = FXCollections.observableArrayList(reservedList);

        TableColumn<Reserved, String> idCol = new TableColumn<>("№");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reserved, Date> enterDateCol = new TableColumn<>("Дата заезда");
        enterDateCol.setCellValueFactory(new PropertyValueFactory<>("enterDate"));

        TableColumn<Reserved, Date> exitDateCol = new TableColumn<>("Дата выезда");
        exitDateCol.setCellValueFactory(new PropertyValueFactory<>("exitDate"));

        TableColumn<Reserved, Integer> roomNumberCol = new TableColumn<>("Номер комнаты");
        roomNumberCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRoom().getNumber()));

        TableColumn<Reserved, Integer> countPersonCol = new TableColumn<>("Количество человек");
        countPersonCol.setCellValueFactory(new PropertyValueFactory<>("countPerson"));

        TableColumn<Reserved, Date> reserveDateCol = new TableColumn<>("Дата резервирования");
        reserveDateCol.setCellValueFactory(new PropertyValueFactory<>("reserveDate"));

        TableColumn<Reserved, String> fioClientCol = new TableColumn("ФИО");
        fioClientCol.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        param.getValue().getClient().getFam() + " " +
                                param.getValue().getClient().getIm() + " " +
                                param.getValue().getClient().getOt()
                )
        );

        TableColumn<Reserved, String> pasportClient = new TableColumn<>("Паспорт");
        pasportClient.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        "серия: " + param.getValue().getClient().getPasportSerial() + " " +
                                " номер: " + param.getValue().getClient().getPasportNumber() + " " +
                                "дата выдачи: " + param.getValue().getClient().getPasportRecievedDate() + " " +
                                " кем выдан: " + param.getValue().getClient().getPasportRecievedPlace()
                )
        );

        TableColumn clientCol = new TableColumn("Клиент");
        clientCol.getColumns().addAll(fioClientCol, pasportClient);

        TableColumn<Reserved, String> famSpecCol = new TableColumn("Специалист");
        famSpecCol.setCellValueFactory(param -> new SimpleObjectProperty<>(
                        param.getValue().getSpecialist().getFam() + " " +
                                param.getValue().getSpecialist().getIm() + " " +
                                param.getValue().getSpecialist().getOt()
                )
        );

        tableReserveList.getColumns().addAll(
                idCol,
                enterDateCol,
                exitDateCol,
                roomNumberCol,
                countPersonCol,
                reserveDateCol,
                clientCol,
                famSpecCol
        );
        tableReserveList.setItems(reservedObservableList);
    }

    public void menuRoomTypeOnClick() {
        if (roomTypeStage == null) {
            roomTypeStage = new Stage();
            roomTypeStage.setTitle(roomTypeFormTitle);
            roomTypeStage.setScene(new Scene(roomTypeView.getView()));
            roomTypeStage.setResizable(true);
            roomTypeStage.centerOnScreen();
        }
        roomTypeStage.show();
        roomTypeStage.setOnCloseRequest(event -> roomTypeStage.hide());
    }

    public void menuRoomOnClick() {
        if (roomStage == null) {
            roomStage = new Stage();
            roomStage.setTitle(roomFormTitle);
            roomStage.setScene(new Scene(roomView.getView()));
            roomStage.setResizable(true);
            roomStage.centerOnScreen();
        }
        roomStage.show();
        roomStage.setOnCloseRequest(event -> roomStage.hide());
    }

    public void menuPositionOnClick() {
        if (positionStage == null) {
            positionStage = new Stage();
            positionStage.setTitle(positionTitle);
            positionStage.setScene(new Scene(positionView.getView()));
            positionStage.setResizable(true);
            positionStage.centerOnScreen();
        }
        positionStage.show();
        positionStage.setOnCloseRequest(event -> positionStage.hide());
    }
}
