/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.main.controller.roomsform;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.ralnik.core.db.entity.RoomTypes;
import ru.ralnik.core.db.entity.Rooms;
import ru.ralnik.core.db.repository.RoomTypesRepository;
import ru.ralnik.core.db.repository.RoomsRepository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FXMLRoomsFormController implements Initializable {
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private RoomTypesRepository roomTypesRepository;

    @FXML
    private TableView<Rooms> tableViewRooms;
    @FXML
    private TextField floorEdit;
    @FXML
    private TextField numberRoomEdit;
    @FXML
    private TextField commentEdit;
    @FXML
    private ComboBox<RoomTypes> typeRoomComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //DO NOTHING
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
        List<RoomTypes> roomTypesList = roomTypesRepository.findAll();
        ObservableList<RoomTypes> roomTypesObservableList = FXCollections.observableList(roomTypesList);
        typeRoomComboBox.setItems(roomTypesObservableList);

        List<Rooms> roomsList = roomsRepository.findAll();
        ObservableList<Rooms> roomsObservableList = FXCollections.observableList(roomsList);

        TableColumn<Rooms, Long> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rooms, Integer> floorColumn = new TableColumn<>("Этаж");
        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));

        TableColumn<Rooms, Integer> numberColumn = new TableColumn<>("Номер комнаты");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Rooms, String> roomTypeColumn = new TableColumn<>("Тип комнаты");
        roomTypeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(
                param.getValue().getRoomType().getName()
        ));

        TableColumn<Rooms, String> commentColumn = new TableColumn<>("Дополнительная информация");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));

        tableViewRooms.getColumns().clear();
        tableViewRooms.getColumns().addAll(idColumn, floorColumn, numberColumn, roomTypeColumn, commentColumn);
        tableViewRooms.setItems(roomsObservableList);
    }

    public void doubleClickTableViewItem() {
        Rooms selectedRoom = tableViewRooms.selectionModelProperty().getValue().getSelectedItem();
        floorEdit.setText(selectedRoom.getFloor().toString());
        numberRoomEdit.setText(selectedRoom.getNumber().toString());
        typeRoomComboBox.setValue(selectedRoom.getRoomType());
        commentEdit.setText(selectedRoom.getComments());
    }

    @Transactional
    public void buttonAddOnClick() {
        Rooms room = new Rooms();
        if (validatorEdits()) {
            room.setFloor(Integer.valueOf(floorEdit.getText()));
            room.setNumber(Integer.valueOf(numberRoomEdit.getText()));
            room.setRoomType(typeRoomComboBox.getValue());
            room.setComments(commentEdit.getText());
            roomsRepository.save(room);
            init();
            clearAllEdits();
        }
    }

    @Transactional
    public void buttonEditOnClick() {
        try {
            Rooms selectedRoom = tableViewRooms.selectionModelProperty().getValue().getSelectedItem();
            Rooms room = roomsRepository.getOne(selectedRoom.getId());
            room.setFloor(Integer.valueOf(floorEdit.getText()));
            room.setNumber(Integer.valueOf(numberRoomEdit.getText()));
            room.setRoomType(typeRoomComboBox.getValue());
            room.setComments(commentEdit.getText());
            roomsRepository.save(room);
            init();
            clearAllEdits();
        } catch (NullPointerException e) {
            log.error("TableView item is not selected, error: ", e);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Не выбран элемент!");
            alert.showAndWait();
        }
    }

    public void buttonDeleteOnClick() {
        try {
            Rooms selectedRoom = tableViewRooms.selectionModelProperty().getValue().getSelectedItem();
            Rooms room = roomsRepository.getOne(selectedRoom.getId());
            roomsRepository.delete(room);
            init();
            clearAllEdits();
        } catch (NullPointerException e) {
            log.error("TableView item is not selected, error: ", e);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Не выбран элемент!");
            alert.showAndWait();
        }
    }

    private void clearAllEdits() {
        floorEdit.clear();
        numberRoomEdit.clear();
        commentEdit.clear();
    }

    private boolean validatorEdits() {
        if (numberRoomEdit.getText().isEmpty() || floorEdit.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Одно из обязательных полей пустое!");
            alert.showAndWait();
            return false;
        }
        try {
            if (Integer.valueOf(numberRoomEdit.getText()).getClass() != Integer.class ||
                    Integer.valueOf(floorEdit.getText()).getClass() != Integer.class) {
                return true;
            }
        } catch (NumberFormatException e) {
            log.error("Error number format ", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Одно из обязательных полей содержит неверный формат данных");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
