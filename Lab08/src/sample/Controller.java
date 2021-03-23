package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;

public class Controller {
    @FXML private TableView tableView;
    @FXML private TableColumn SID;
    @FXML private TableColumn assignment;
    @FXML private TableColumn midterm;
    @FXML private TableColumn finalExam;
    @FXML private TableColumn finalMark;
    @FXML private TableColumn letterGrade;
    @FXML private TextField txtSID;
    @FXML private TextField txtAssignments;
    @FXML private TextField txtMidterm;
    @FXML private TextField txtFinal;
    @FXML private MenuBar menuBar;

    private String currentFileName;

    @FXML public void initialize() {
        SID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tableView.setItems(DataSource.getAllMarks());
    }

    public void Add(javafx.event.ActionEvent actionEvent)
    {
       ObservableList<StudentRecord> temp = FXCollections.observableArrayList();
       temp = tableView.getItems();
       temp.add(new StudentRecord(txtSID.getText(),Float.valueOf(txtAssignments.getText()),Float.valueOf(txtMidterm.getText()),Float.valueOf(txtFinal.getText())));
       tableView.setItems(temp);
    }

    public void save() throws Exception
    {
        Writer writer = null;
        try
        {
            File file = new File(currentFileName);
            writer = new BufferedWriter(new FileWriter(file));
            ObservableList<StudentRecord> temp = FXCollections.observableArrayList();
            temp = tableView.getItems();
            for(StudentRecord x: temp)
            {
                String text = x.getStudentID() + "," + x.getAssignment() + "," + x.getMidterm() + "," + x.getFinalExam() + "\n";
                writer.write(text);
            }
        }
        catch (Exception ne)
        {
            ne.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }
    public void load()
    {
        String delimiter = ",";
        BufferedReader br;
        ObservableList<StudentRecord> temp = FXCollections.observableArrayList();
        try
        {
            br = new BufferedReader(new FileReader(currentFileName));
            String line;

            while((line = br.readLine()) != null)
            {
                String[] fields = line.split(delimiter, -1);
                StudentRecord record = new StudentRecord(fields[0],Float.valueOf(fields[1]),Float.valueOf(fields[2]),Float.valueOf(fields[3]));

                temp.add(record);
            }
            tableView.setItems(temp);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void handSaveAction(javafx.event.ActionEvent actionEvent)
    {
        try {
            save();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void handleNewAction(ActionEvent actionEvent)
    {
        tableView.getItems().clear();
    }

    public void handleOpenAction(ActionEvent actionEvent)
    {
        FileChooser directoryChooser = new FileChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showOpenDialog(tableView.getScene().getWindow());
        currentFileName = dir.getAbsolutePath();
        load();
    }

    public void handleSaveAsAction(ActionEvent actionEvent)
    {
        FileChooser directoryChooser = new FileChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showSaveDialog(tableView.getScene().getWindow());
        currentFileName = dir.getAbsolutePath();
        try {
            save();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void handleExitAction(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
