package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Main extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception{
       Group root = new Group();
       Scene scene = new Scene(root,460,310);

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);

        primaryStage.setTitle("Lab09 - Solution");
        primaryStage.setScene(scene);
        primaryStage.show();

        ArrayList<Float> GOOG = new ArrayList<>();
        ArrayList<Float> AAPL = new ArrayList<>();

        GOOG = downloadStockPrices("GOOG");
        AAPL = downloadStockPrices("AAPL");

        drawLinePlot(GOOG,AAPL);

    }


    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<Float> downloadStockPrices(String ticker)
    {
        ArrayList<Float> arr = new ArrayList<>();
        String line="";
        try
        {
            URL sURL = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + ticker + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true");

            URLConnection conn = sURL.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);

            InputStream inStream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));

            while((line = br.readLine())!=null)
            {
                String[] columns = line.split(",");
                if(columns[4].equals("Close"))
                {
                    continue;
                }
                else
                {
                    arr.add(Float.valueOf(columns[4]));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    public void drawLinePlot(ArrayList<Float> arr1, ArrayList<Float> arr2)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.strokeLine(50,50,50,260);
        gc.strokeLine(50,260,410,260);

        gc.setStroke(Color.RED);
        plotLine(arr1);
        gc.setStroke(Color.BLUE);
        plotLine(arr2);

    }

    private void plotLine(ArrayList<Float> arr)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for(int i = 0; i < arr.size();i++)
        {
            if(i+1 != arr.size())
            {
                gc.strokeLine((i*5 + 50),260 - arr.get(i) * 0.25 ,(i*5+55),260 - arr.get(i+1) * 0.25);
            }
            else
            {
                double temp = (260 - arr.get(i) * 0.25);
            }

        }
    }
}
