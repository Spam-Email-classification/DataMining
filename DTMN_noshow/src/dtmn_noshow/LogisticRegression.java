package dtmn_noshow;

import java.util.Arrays;

public class LogisticRegression {
    private double[] weights;
    private double learningRate = 0.01;
    private int iterations = 1000;
    private double lambda = 0.1; // Regularization parameter

    public LogisticRegression(int n_features) {
        weights = new double[n_features + 1]; // +1 for bias term
    }

    // Sigmoid function
    public double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    // Predict the raw score (without applying sigmoid)
    private double predictRaw(double[] x) {
        double z = weights[0]; // Bias term
        for (int i = 0; i < x.length; i++) {
            z += weights[i + 1] * x[i];
        }
        return z;
    }

    // Predict the label based on sigmoid of raw score
    public int predict(double[] x) {
        return sigmoid(predictRaw(x)) >= 0.5 ? 1 : 0;
    }

    // Train the logistic regression model with given data and labels
    public void train(double[][] X, int[] Y) {
        int m = X.length; // number of training examples
        int n = X[0].length; // number of features

        // Gradient Descent loop
        for (int iter = 0; iter < iterations; iter++) {
            double[] gradients = new double[n + 1];

            for (int j = 0; j < m; j++) {
                double[] x = X[j];
                int y = Y[j];
                double prediction = sigmoid(predictRaw(x)); // Apply sigmoid to the raw score
                double error = prediction - y;

                gradients[0] += error; // Gradient for bias term
                for (int k = 0; k < n; k++) {
                    gradients[k + 1] += error * x[k];
                }
            }

            // Regularization term (L2 Regularization)
            for (int k = 1; k < weights.length; k++) {
                gradients[k] += lambda * weights[k];
            }

            // Update weights with the gradient and learning rate
            for (int k = 0; k < weights.length; k++) {
                weights[k] -= learningRate * gradients[k] / m;
            }

            // Optional: Print loss for every 100th iteration to monitor progress
            if (iter % 100 == 0) {
                double loss = computeLoss(X, Y);
                System.out.printf("Epoch %d - Loss: %.4f\n", iter, loss);
            }
        }
    }

    // Compute the logistic regression loss (log loss with regularization)
    private double computeLoss(double[][] X, int[] Y) {
        double loss = 0.0;
        int m = X.length;

        for (int i = 0; i < m; i++) {
            double prediction = sigmoid(predictRaw(X[i]));
            int y = Y[i];
            loss += -y * Math.log(prediction + 1e-15) - (1 - y) * Math.log(1 - prediction + 1e-15);
        }

        // Add regularization term to the loss
        for (int i = 1; i < weights.length; i++) {
            loss += lambda * weights[i] * weights[i]; // L2 regularization
        }

        return loss / m;
    }

    // Standardize the dataset (zero mean, unit variance) before training
    public static double[][] standardize(double[][] X) {
        int m = X.length;
        int n = X[0].length;

        double[][] standardized = new double[m][n];

        // Calculate mean and standard deviation for each feature
        double[] means = new double[n];
        double[] stdDevs = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < m; j++) {
                sum += X[j][i];
            }
            means[i] = sum / m;

            double varianceSum = 0;
            for (int j = 0; j < m; j++) {
                varianceSum += Math.pow(X[j][i] - means[i], 2);
            }
            stdDevs[i] = Math.sqrt(varianceSum / m);
        }

        // Standardize the data
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                standardized[i][j] = (X[i][j] - means[j]) / stdDevs[j];
            }
        }

        return standardized;
    }

    // Evaluate the model's performance on a test set
    
}
