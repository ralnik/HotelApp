/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.main.controller.positionform;

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
import org.springframework.transaction.annotation.Transactional;
import ru.ralnik.core.db.entity.Positions;
import ru.ralnik.core.db.repository.PositionRepository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FXMLPositionFormController implements Initializable {
    @Autowired
    private PositionRepository positionRepository;

    @FXML
    private TextField namePositionEdit;
    @FXML
    private TextField commentEdit;
    @FXML
    private TableView<Positions> tablePosition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //DO NOTHING
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
        List<Positions> positionsList = positionRepository.findAll();
        ObservableList<Positions> positionObservableList = FXCollections.observableList(positionsList);

        TableColumn<Positions, Long> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Positions, String> nameColumn = new TableColumn<>("Должность");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Positions, String> commentColumn = new TableColumn<>("Дополнительная информация");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));

        tablePosition.getColumns().clear();
        tablePosition.getColumns().setAll(idColumn, nameColumn, commentColumn);
        tablePosition.setItems(positionObservableList);
    }

    public void doubleClickTablePosition() {
        Positions positionSelected = tablePosition.selectionModelProperty().getValue().getSelectedItem();
        namePositionEdit.setText(positionSelected.getName());
        commentEdit.setText(positionSelected.getComments());
    }

    public void buttonAddOnClick() {
        Positions position = new Positions();
        position.setName(namePositionEdit.getText());
        position.setComments(commentEdit.getText());
        positionRepository.save(position);
        clearAllEdits();
        init();
    }

    @Transactional
    public void buttonEditOnClick() {
        try {
            Positions positionSelected = tablePosition.selectionModelProperty().getValue().getSelectedItem();
            Positions position = positionRepository.getOne(positionSelected.getId());
            position.setName(namePositionEdit.getText());
            position.setComments(commentEdit.getText());
            positionRepository.save(position);
            clearAllEdits();
            init();
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
            Positions positionSelected = tablePosition.selectionModelProperty().getValue().getSelectedItem();
            positionRepository.delete(positionSelected);
            clearAllEdits();
            init();
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
        namePositionEdit.clear();
        commentEdit.clear();
    }
}
