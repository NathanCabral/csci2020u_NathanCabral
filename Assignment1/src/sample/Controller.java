package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.sql.DataSource;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Controller
{
    @FXML private TableView tableView;
    @FXML private javafx.scene.control.TableColumn FileName;
    @FXML private javafx.scene.control.TableColumn ActualClass;
    @FXML private javafx.scene.control.TableColumn  spamProbability;
    @FXML private Button Display;

    @FXML private TextField accuracy;
    @FXML private TextField percision;

    @FXML public void initialize()
    {
        FileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        ActualClass.setCellValueFactory(new PropertyValueFactory<>("ActualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        tableView.setItems(WordCounter.getFileList());
    }

    public void Register(javafx.event.ActionEvent actionEvent) {
        percision.setText(WordCounter.getPercision());
        accuracy.setText(WordCounter.getAccuracy());
    }
}
