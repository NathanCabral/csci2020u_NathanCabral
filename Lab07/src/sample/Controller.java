package sample;
import javafx.fxml.FXML;

import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
public class Controller {

    private static Color[] pieColours = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON};
    @FXML private Canvas canvas;
    @FXML public GraphicsContext gc;

    @FXML public void initialize()
    {
        gc = canvas.getGraphicsContext2D();
        FileLoader temp = new FileLoader("C:/Users/Nathan's PC/IdeaProjects/Lab07/src/sample/weatherwarnings-2015.csv");
        temp.loadFile();
        temp.count();
        Map<String, Integer> portions = temp.getSectors();
        drawPieChart(portions);
    }

    @FXML private void drawPieChart(Map<String, Integer> portions)
    {
        int numOfValues = 0;
        for(Map.Entry<String,Integer> entry : portions.entrySet())
        {
            numOfValues += entry.getValue();
        }
        double startAngle = 0.0;
        int i = 0;
        double startFill = 150;
        for(Map.Entry<String,Integer> entry : portions.entrySet())
        {
            double slicePercentage = (double) entry.getValue() / (double) numOfValues;
            double sweepAngle = slicePercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(500,150,300,300,startAngle,sweepAngle, ArcType.ROUND);
            gc.strokeArc(500,150,300,300,startAngle,sweepAngle,ArcType.ROUND);
            gc.fillRect(200,startFill,60,40);
            gc.strokeRect(200,startFill,60,40);
            gc.setFill(Color.BLACK);
            gc.fillText(entry.getKey(),270,startFill+25);


            startAngle += sweepAngle;
            i++;
            startFill += 50;

        }
    }
}
