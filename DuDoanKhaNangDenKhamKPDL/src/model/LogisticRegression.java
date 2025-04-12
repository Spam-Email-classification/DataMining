/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Arrays;

/**
 *
 * @author admin
 */
public class LogisticRegression {
     private double[] weights;
    private double learningRate;
    private int epochs;

    public LogisticRegression(int numFeatures, double learningRate, int epochs) {
        this.weights = new double[numFeatures + 1]; // +1 for the bias term
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    // Sigmoid function
    private double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    // Fit the model on training data
    public void fit(double[][] X, int[] y) {
        int n = X.length;
        int m = X[0].length;

        for (int epoch = 0; epoch < epochs; epoch++) {
            double[] gradients = new double[m + 1]; // +1 for bias

            for (int i = 0; i < n; i++) {
                double[] x = Arrays.copyOf(X[i], m + 1);
                x[m] = 1.0; // Add bias term

                double prediction = sigmoid(dotProduct(x, weights));
                double error = prediction - y[i];

                // Update gradients
                for (int j = 0; j < gradients.length; j++) {
                    gradients[j] += error * x[j];
                }
            }

            // Update weights
            for (int j = 0; j < weights.length; j++) {
                weights[j] -= learningRate * gradients[j] / n;
            }
        }
    }

    // Predict the probability of the positive class
    public double predictProbability(double[] x) {
        double[] extendedX = Arrays.copyOf(x, weights.length);
        extendedX[weights.length - 1] = 1.0; // Add bias term
        return sigmoid(dotProduct(extendedX, weights));
    }

    // Predict the class (0 or 1)
    public int predict(double[] x) {
        return predictProbability(x) >= 0.5 ? 1 : 0;
    }

    // Helper function to calculate dot product
    private double dotProduct(double[] a, double[] b) {
        double result = 0.0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }
    
    
}
