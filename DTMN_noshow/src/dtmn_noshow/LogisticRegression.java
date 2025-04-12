package dtmn_noshow;

public class LogisticRegression {
    private double[] weights;
    private double learningRate = 0.01;
    private int iterations = 1000;

    public LogisticRegression(int n_features) {
        weights = new double[n_features + 1]; // +1 cho bias
    }

    public double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    private double predictRaw(double[] x) {
        double z = weights[0]; // bias
        for (int i = 0; i < x.length; i++) {
            z += weights[i + 1] * x[i];
        }
        return sigmoid(z);
    }

    public int predict(double[] x) {
        return predictRaw(x) >= 0.5 ? 1 : 0;
    }

    public void train(double[][] X, int[] Y) {
        int m = X.length;
        int n = X[0].length;

        for (int iter = 0; iter < iterations; iter++) {
            double[] gradients = new double[n + 1];

            for (int j = 0; j < m; j++) {
                double[] x = X[j];
                int y = Y[j];

                double prediction = predictRaw(x);
                double error = prediction - y;

                gradients[0] += error;
                for (int k = 0; k < n; k++) {
                    gradients[k + 1] += error * x[k];
                }
            }

            // Cập nhật trọng số
            for (int k = 0; k < weights.length; k++) {
                weights[k] -= learningRate * gradients[k] / m;
            }

            // Tùy chọn: In ra loss mỗi 100 vòng
            if (iter % 100 == 0) {
                double loss = computeLoss(X, Y);
                System.out.printf("Epoch %d - Loss: %.4f\n", iter, loss);
            }
        }
    }

    private double computeLoss(double[][] X, int[] Y) {
        double loss = 0.0;
        for (int i = 0; i < X.length; i++) {
            double prediction = predictRaw(X[i]);
            int y = Y[i];

            loss += -y * Math.log(prediction + 1e-15) - (1 - y) * Math.log(1 - prediction + 1e-15);
        }
        return loss / X.length;
    }

    public double evaluateAccuracy(double[][] X, int[] Y) {
        int correct = 0;
        for (int i = 0; i < X.length; i++) {
            if (predict(X[i]) == Y[i]) correct++;
        }
        return (double) correct / X.length;
    }
}
