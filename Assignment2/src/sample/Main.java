package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    public static String filePath;
    public TreeView<String> localTree = null;
    public TreeView<String> sharedTree = null;
    public TreeItem<String> items = new TreeItem<String>("Shared Folder");
    public TreeItem<String> localItems = new TreeItem<String>("Local Folder");
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("File Sharer v1.0");
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);

        Button btnUpload = new Button("Upload");
        pane.add(btnUpload,1,0);
        btnUpload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileServerClient upload = generateNewClient();
                TreeItem<String> fileName = localTree.getSelectionModel().getSelectedItem();
                String fName = fileName.getValue();
                upload.UPLOAD(filePath+"\\"+fName);
                items.getChildren().addAll(new TreeItem<String>(fName));
                sharedTree.refresh();
                sharedTree.getSelectionModel().clearSelection();
                localTree.getSelectionModel().clearSelection();
            }
        });
        Button btnDownload = new Button("Download");
        pane.add(btnDownload,0,0);
        btnDownload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                fileServerClient download = generateNewClient();
                TreeItem<String> fileName = sharedTree.getSelectionModel().getSelectedItem();
                String fName = fileName.getValue();
                download.DOWNLOAD(filePath+"\\"+fName);
                localItems.getChildren().addAll(new TreeItem<String>(fName));
                localTree.refresh();
                localTree.getSelectionModel().clearSelection();
                sharedTree.getSelectionModel().clearSelection();
            }
        });
        Button btnOpenFile = new Button("Open");
        pane.add(btnOpenFile,2,0);
        btnOpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                fileServerClient download = generateNewClient();
                TreeItem<String> fileName = null;
                try
                {
                    fileName = sharedTree.getSelectionModel().getSelectedItem();
                    String fName = fileName.getValue();
                    download.OpenFile(filePath+"\\"+fName,items);
                    localItems.getChildren().addAll(new TreeItem<String>(fName));
                    localTree.refresh();
                    sharedTree.getSelectionModel().clearSelection();
                    localTree.getSelectionModel().clearSelection();
                }
                catch(NullPointerException e)
                {
                    fileName = localTree.getSelectionModel().getSelectedItem();
                    download.OpenFile(filePath+"\\"+fileName.getValue(),localItems);
                    localTree.getSelectionModel().clearSelection();
                    sharedTree.getSelectionModel().clearSelection();
                }
            }
        });

        Button btnPreviewFile = new Button("Preview");
        pane.add(btnPreviewFile,3,0);
        Label previewLabel = new Label("Select File To Preview");
        previewLabel.setWrapText(true);
        previewLabel.setMinWidth(300);
        previewLabel.setAlignment(Pos.CENTER);
        previewLabel.setTextAlignment(TextAlignment.CENTER);
        btnPreviewFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileServerClient display = generateNewClient();
                TreeItem<String> fileName = null;
                try
                {
                    fileName = sharedTree.getSelectionModel().getSelectedItem();
                    String message = display.DISPLAY(fileName.getValue(),1);
                    previewLabel.setText(message);
                    sharedTree.getSelectionModel().clearSelection();

                }
                catch(NullPointerException e)
                {
                    fileName = localTree.getSelectionModel().getSelectedItem();
                    String message = display.DISPLAY(fileName.getValue(),2);
                    previewLabel.setText(message);
                    localTree.getSelectionModel().clearSelection();
                }

            }
        });

        SplitPane splitPane = runUpdate();
        splitPane.getItems().addAll(previewLabel);
        splitPane.autosize();
        pane.setAlignment(Pos.CENTER);
        pane.add(splitPane,0,1,4,2);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public fileServerClient generateNewClient()
    {
        fileServerClient newClient = new fileServerClient();
        return newClient;
    }
    public SplitPane runUpdate()
    {
        fileServerClient temp = generateNewClient();
        String[] values = temp.DIR();
        return updatePane(values);
    }
    public SplitPane updatePane(String[] values)
    {
        SplitPane splitPane = new SplitPane();

        for(int i = 1; i < values.length;i++)
        {
            items.getChildren().addAll(new TreeItem<String>(values[i]));
        }
        items.setExpanded(true);

        localItems.setExpanded(true);
        File sharedDisplay = new File(filePath);
        DisplayFiles(sharedDisplay,localItems);

        localTree = new TreeView<String>(localItems);
        sharedTree = new TreeView<String>(items);
        splitPane.getItems().addAll(localTree,sharedTree);
        return splitPane;
    }
    public void DisplayFiles(File fileDisplay,TreeItem<String> items)
    {
        String[] files;

        files = fileDisplay.list();

        for(String file: files)
        {
            items.getChildren().addAll(new TreeItem<String>(file));
        }
    }

    public static void main(String[] args)
    {
        filePath = args[1];
        launch(args);
    }
}
