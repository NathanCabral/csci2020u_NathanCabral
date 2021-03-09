package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller
{
    @FXML private Canvas canvas;
    @FXML public GraphicsContext gc;

    private static double[] avgHousingPricesByYear = {247381.0, 264171.4, 287715.3, 294736.1, 308431.4, 322635.9, 340235.0, 363153.7};
    private static double[] avgCommericalPricesByYear = {1121585.3, 1219479.5, 1246354.2, 1295364.8, 1335932.6, 1472362.0, 1583521.9, 1613246.3};
    private static String[] ageGroups = { "18-25", "26-35", "36-45", "46-55", "56-65", "65+"};
    private static int[] purchasesByAgeGroup = { 648, 1021, 2453, 3173, 1868, 2247};
    private static Color[] pieColours = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};

    @FXML public void initialize()
    {
        gc = canvas.getGraphicsContext2D();
        drawPieChart();
        drawGraph(100,400,avgHousingPricesByYear,Color.RED,0,avgCommericalPricesByYear[7]);
        drawGraph(100,400,avgCommericalPricesByYear,Color.BLUE,100/avgCommericalPricesByYear.length,avgCommericalPricesByYear[7]);
    }

    public void drawGraph(int w, int h, double[] data, Color colour,int startX,double scalar){
        gc.setFill(colour);
        double xInc = (w/data.length);

        double maxVal = Double.NEGATIVE_INFINITY;
        double minVal = Double.MAX_VALUE;
        //finds the max and min for scaled height calculation
        for(double val: data){
            if(val > maxVal){
                maxVal = val;
            } if(val < minVal){
                minVal = val;
            }
        }

        double x = startX;
        for(double val: data){
            //scaled height calculation
            double height = ((val-minVal) / scalar*2) * h;
            gc.fillRect(x,(h-height),xInc,height);
            //added "2 *" to fit another graph between the graph later
            x += 2 * xInc + 10;
        }
    }

    @FXML private void drawPieChart() {
        int numOfPurchases = 0;
        for (int purchases : purchasesByAgeGroup) {
            numOfPurchases += purchases;
        }

        double startAngle = 0.0;
        for (int i = 0; i < purchasesByAgeGroup.length; i++)
        {
            double slicePercentage = (double) purchasesByAgeGroup[i] / (double) numOfPurchases;
            double sweepAngle = slicePercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(350,150,300,300,startAngle,sweepAngle, ArcType.ROUND);

            startAngle += sweepAngle;
        }
    }


}
