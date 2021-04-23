import java.util.*;

public class Perceptron {
    private double alpha = Math.random();
    private double theta = Math.random();
    private double[] weights;

    public Perceptron() {
        weights = fillRandom();
    }

    private double[] fillRandom() {
        double[] array = new double[26];
        Arrays.fill(array, (Math.random()*2)-1);
        return array;
    }

    public int evaluate(Observation observation) {
        double net = theta;

        for (int i = 0; i < observation.getAttributes().length; i++)
            net += observation.getAttributes()[i] * weights[i];

        return (net >= 0? 1:0);
    }

    public void learn(Observation observation, String writtenLabel, String actualLabel) {
        int d = writtenLabel.equals(actualLabel)? 1:0;
        int y = evaluate(observation);
        int error = d - y;

        for (int i = 0; i < weights.length; i++)
            weights[i] += alpha * error * observation.getAttributes()[i];

        theta += error * alpha;
    }

    public double getAlpha() { return alpha; }

    public void setAlpha(double alpha) { this.alpha = alpha; }

    public double getTheta() {return theta; }

    public void setTheta(double theta) { this.theta = theta; }

    public double[] getWeights() { return weights; }

    public void setWeights(double[] weights) { this.weights = weights; }
}