package Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client extends Application {

    public static MessageClient connection = MessageClient.connect("localhost", 16789);
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SimpleBBS Client v1.0");
        GridPane pane = new GridPane();

        Label lblUsername = new Label("Username: ");
        TextField txtUsername = new TextField("Enter Username");

        Label lblMessage = new Label("Message");
        TextField txtMessage = new TextField("Enter Message");

        Button btnSend = new Button("Send");

        Button btnExit = new Button("Exit");

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(lblUsername,0,0);
        pane.add(txtUsername,1,0);
        pane.add(lblMessage,0,1);
        pane.add(txtMessage,1,1);
        pane.add(btnSend,0,2);
        pane.add(btnExit,0,3);



        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                connection.sendMessage(txtUsername.getText(), txtMessage.getText());
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                connection.exit();
            }
        });

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
