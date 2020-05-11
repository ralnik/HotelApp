/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.main.controller.roomtype;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ralnik.core.db.entity.RoomTypes;
import ru.ralnik.core.db.repository.RoomTypesRepository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
public class FXMLRoomTypeFormController implements Initializable {
    @Autowired
    private RoomTypesRepository roomTypesRepository;

    @FXML
    private TableView<RoomTypes> tableViewRoomTypes;

    @FXML
    private TextField nameTypeEdit;
    @FXML
    private TextField commentsEdit;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<RoomTypes> roomTypesList = roomTypesRepository.findAll();
        ObservableList<RoomTypes> roomTypesObservableList = FXCollections.observableList(roomTypesList);

        TableColumn<RoomTypes, Long> idCol = new TableColumn<>("№");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<RoomTypes, String> nameCol = new TableColumn<>("Тип комнаты");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<RoomTypes, String> commentsCol = new TableColumn<>("Дополнительная информация");
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));

        tableViewRoomTypes.getColumns().clear();
        tableViewRoomTypes.getColumns().addAll(idCol, nameCol, commentsCol);
        tableViewRoomTypes.setItems(roomTypesObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Do nothing
    }

    public void addNewType() {
        RoomTypes roomTypes = new RoomTypes();
        roomTypes.setName(nameTypeEdit.getText());
        roomTypes.setComments(commentsEdit.getText());
        roomTypesRepository.save(roomTypes);
        init();
        clearAllEdits();
    }

    public void editType() {
        try {
            Long id = tableViewRoomTypes.selectionModelProperty().getValue().getSelectedItem().getId();
            RoomTypes roomTypes = roomTypesRepository.getOne(id);
            roomTypes.setName(nameTypeEdit.getText());
            roomTypes.setComments(commentsEdit.getText());
            roomTypesRepository.save(roomTypes);
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

    public void deleteType() {
        try {
            Long id = tableViewRoomTypes.selectionModelProperty().getValue().getSelectedItem().getId();
            roomTypesRepository.deleteById(id);
            init();
            clearAllEdits();
        } catch (NullPointerException e) {
            log.error("TableView item is not selected, error: ", e);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Не выбран элемент!");
            alert.showAndWait();
        }
    }

    public void doubleClickTableViewItem() {
        nameTypeEdit.setText(tableViewRoomTypes.selectionModelProperty().getValue().getSelectedItem().getName());
        commentsEdit.setText(tableViewRoomTypes.selectionModelProperty().getValue().getSelectedItem().getComments());
    }

    private void clearAllEdits() {
        nameTypeEdit.clear();
        commentsEdit.clear();
    }
}
