import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {
    private static final HashMap<Integer, Double> chartXY = new HashMap<>();
    private static final Algorithm algorithm = new Algorithm(55);
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("Accuracy: " + algorithm.run(true));

        System.out.println("=====================================");

        for (int i = 1; i <= algorithm.getTrainingArray().size(); i+=1) {
            algorithm.setK(i);
            double accuracy = algorithm.run(false);
            chartXY.put(i, accuracy);
            System.out.println(i + " -> " + accuracy);
        }
        launch(args);

        System.out.println("=====================================");

        setDataFromConsole();
    }

    // Note: pobieranie danych z consoli i wyświetlenie etykiety
    public static void setDataFromConsole() {
        while (isRunning) {
            System.out.println("Enter attributes 'a1,a2,a3,a4,...,k' or 'exit': ");
            String line = scanner.nextLine();

            if (line.equals("exit")) {
                isRunning = false;
                break;
            }

            String[] attrsString = line.split(",");
            double[] attributes = new double[attrsString.length-1];

            try {
                for (int i = 0; i < attributes.length; i++)
                    attributes[i] = Double.parseDouble(attrsString[i].trim());
                algorithm.setK(Integer.parseInt(attrsString[attrsString.length-1].trim()));
            } catch (Exception exception) {
                System.out.println("Entered data is not valid!");
                System.out.println("=====================================");
                setDataFromConsole();
                break;
            }

            ArrayList<Observation> observations = new ArrayList<>();
            observations.add(new Observation(null, attributes));
            algorithm.setTestArray(observations);
            algorithm.run(true);

            System.out.println("=====================================");
        }
    }

    // Note: rysowanie wykresu
    @Override
    public void start(Stage stage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Accuracy depending on K");

        for ( Map.Entry<Integer, Double> entry : chartXY.entrySet())
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}