package dtmn_noshow;

import java.util.Arrays;

public class LogisticRegression {
    private double[] weights;
    private double learningRate = 0.01;
    private int iterations = 1000;

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

// hàm huấn luyện
    public void train(double[][] X, int[] Y) {
        
        
        
        int m = X.length; // số bản ghi
        int n = X[0].length; // số features
        for (int iter = 0; iter < iterations; iter++) {
            // 1 vòng epoch: mô hình duyệt qua toàn bộ dữ liệu 1 lần để
            // cập nhập trọng số
            
            
            
            // Khởi tạo mảng để lưu đạo hàm
            double[] gradients = new double[n + 1];

            for (int j = 0; j < m; j++) {
                // lấy bản ghi thứ j
                double[] x = X[j];
                
                // label của mẫu thứ j
                int y = Y[j];
                
                // dự đoán
                double prediction = sigmoid(predictRaw(x)); // Apply sigmoid to the raw score
                
                // chenh lech giữa thực tế và dự đoán
                double error = prediction - y;
                
                // tính gradient
                // cộng dồn tất cả các đạo hàm rồi tính trung bình
                gradients[0] += error; // Gradient for bias term
                for (int k = 0; k < n; k++) {
                    gradients[k + 1] += error * x[k];
                }
            }
                
            
            //Cập  nhập trọng số wi = wi + learningRate * gradients[i]/m
            for (int k = 0; k < weights.length; k++) {
                weights[k] -= learningRate * gradients[k] / m;
            }

            // in ra Loss để giám sát mô hình
            if (iter % 100 == 0) {
                double loss = computeLoss(X, Y);
                System.out.printf("Epoch %d - Loss: %.4f\n", iter, loss);
            }}}

    // hàm tính Loss````
    private double computeLoss(double[][] X, int[] Y) {
        double loss = 0.0;
        int m = X.length;

        for (int i = 0; i < m; i++) {
            
            // Kq dữ đoán
            double prediction = sigmoid(predictRaw(X[i]));
            int y = Y[i]; // nhãn
            // công thích tính Loss
            loss += -y * Math.log(prediction + 1e-15) - (1 - y) * Math.log(1 - prediction + 1e-15);
        }

      // Loss trung bình của m mẫu (J(w))
        return loss / m;
    }

   
}
