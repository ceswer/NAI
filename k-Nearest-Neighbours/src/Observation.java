public class Observation implements Comparable<Observation> {
    private String name;
    private double[] values;
    private double distance;

    public Observation(String name, double[] values) {
        this.name = name;
        this.values = values;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double[] getValues() { return values; }

    public void setValues(double[] values) { this.values = values; }

    public double getDistance() { return distance; }

    public void setDistance(double distance) { this.distance = distance; }

    @Override
    public int compareTo(Observation observation) {
        double difference = this.getDistance() - observation.getDistance();
        if (difference > 0) return 1;
        else if (difference < 0) return -1;
        else return 0;
    }
}