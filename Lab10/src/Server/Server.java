package Server;

import Client.Client;
import javafx.application.Application;;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;



public class Server extends Application {

    public static TextArea board = new TextArea();
    @Override
    public void start(Stage primaryStage) throws Exception{
        MainServer app = new MainServer();
        primaryStage.setTitle("SimpleBBs Server v1.0");
        GridPane pane = new GridPane();

        Button exit = new Button("Exit");

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               app.close();
            }
        });

        pane.add(board,0,0);
        pane.add(exit,0,1);

        primaryStage.setScene(new Scene(pane, 380, 250));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
