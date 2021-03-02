package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    @FXML private TableView tableView;
    @FXML private TableColumn SID;
    @FXML private TableColumn assignment;
    @FXML private TableColumn midterm;
    @FXML private TableColumn finalExam;
    @FXML private TableColumn finalMark;
    @FXML private TableColumn letterGrade;


    @FXML public void initialize() {
        SID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tableView.setItems(DataSource.getAllMarks());
    }

}
