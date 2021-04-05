import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

// Note: parsowanie danych z plików, przechowywanie danych
public class Service {
    public static HashMap<String, Integer> counters = new HashMap<>();

    public static ArrayList<Observation> parse(String path) {
        ArrayList<Observation> observations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while (null != (line = br.readLine())) {
                String[] strings = line.split(",");
                double[] values = new double[strings.length-1];
                for (int i = 0; i < values.length; i++)
                    values[i] = Double.parseDouble(strings[i].trim());
                counters.put(strings[strings.length-1], 0);
                observations.add(new Observation(strings[strings.length-1], values));
            }
        } catch (Exception exception) { System.out.println(exception.getMessage()); }
        return observations;
    }
}