import java.util.*;

public class Algorithm {
    private ArrayList<Observation> testArray, trainingArray;
    private int K;

    public Algorithm(int K) {
        this.testArray = Service.parse("data\\test.csv");
        this.trainingArray = Service.parse("data\\training.csv");
        this.K = K;
    }

    // Note: rachunek odległości euklidesowej
    private double calcEuclidesDistance(Observation o1, Observation o2) {
        double distance = 0;

        Observation observation;

        if (o1.getValues().length > o2.getValues().length) observation = o2;
        else observation = o1;

        for (int i = 0; i < observation.getValues().length; i++)
            distance += Math.pow(o1.getValues()[i] - o2.getValues()[i], 2);

        return Math.sqrt(distance);
    }

    // Note: określenie typu odnośnie k-najbliższych sąsiadów, odzerowanie poprzednich danych
    private String determine() {
        if (this.getK() > trainingArray.size())
            this.setK(trainingArray.size());

        for (Map.Entry<String, Integer> entry : Service.counters.entrySet())
            entry.setValue(0);

        for (int i = 0; i < this.getK(); i++)
            for (Map.Entry<String, Integer> entry : Service.counters.entrySet())
                if (trainingArray.get(i).getName().equals(entry.getKey()))
                    entry.setValue(entry.getValue() + 1);

        return Collections.max(Service.counters.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    // Note: przejście po wszystkim danym treningowym dla określenia prawdziwego typu każdego z danych testowych
    public double run(boolean flag) {
        double total = 0, found = 0;

        for (Observation observationTest : this.getTestArray()) {
            for (Observation observationTraining : this.getTrainingArray())
                observationTraining.setDistance(calcEuclidesDistance(observationTest, observationTraining));

            Collections.sort(trainingArray);
            String name = determine();

            if (name.equals(observationTest.getName())) found++;
            if (flag) System.out.println("Expected name: " + observationTest.getName() + " " + Arrays.toString(observationTest.getValues()) + " -> Actual name: " + name);
            total++;
        }
        return found/total;
    }

    public ArrayList<Observation> getTestArray() { return testArray; }

    public void setTestArray(ArrayList<Observation> testArray) { this.testArray = testArray; }

    public ArrayList<Observation> getTrainingArray() { return trainingArray; }

    public void setTrainingArray(ArrayList<Observation> trainingArray) { this.trainingArray = trainingArray; }

    public int getK() { return K; }

    public void setK(int K) { this.K = K; }
}