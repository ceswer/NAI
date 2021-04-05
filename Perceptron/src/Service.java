import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Service {
    public static HashMap<String, Integer> determinationValues = new HashMap<>();
    public static int length;

    public static ArrayList<Observation> parse(String path) {
        ArrayList<Observation> observations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while (null != (line = br.readLine())) {
                String[] strings = line.split(",");
                length = strings.length - 1;
                double[] values = new double[length];
                for (int i = 0; i < values.length; i++)
                    values[i] = Double.parseDouble(strings[i].trim());
                determinationValues.put(strings[length], null);
                observations.add(new Observation(strings[length], values));
            }
        } catch (Exception exception) { System.out.println(exception.getMessage()); }

        int identifier = 0;
        for (Map.Entry<String, Integer> entry : determinationValues.entrySet())
            entry.setValue(identifier++);

        return observations;
    }
}
