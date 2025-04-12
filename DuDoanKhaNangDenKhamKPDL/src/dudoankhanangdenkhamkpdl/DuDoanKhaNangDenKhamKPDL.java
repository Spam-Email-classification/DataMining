/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dudoankhanangdenkhamkpdl;
import model.LogisticRegression;
import data.DataLoader;
import utils.Normalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DuDoanKhaNangDenKhamKPDL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         List<double[]> features = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        try {
            // Load dataset
            DataLoader.loadCSV("src/dataset/dataset.csv", features, labels);

            // Normalize features
            Normalizer.normalize(features);

            // Convert lists to arrays for processing
            double[][] featureArray = features.toArray(new double[0][0]);
            int[] labelArray = labels.stream().mapToInt(i -> i).toArray();

            // Split data into training and testing sets (80%-20% split)
            int splitIndex = (int) (featureArray.length * 0.8);
            double[][] XTrain = new double[splitIndex][];
            int[] yTrain = new int[splitIndex];
            double[][] XTest = new double[featureArray.length - splitIndex][];
            int[] yTest = new int[featureArray.length - splitIndex];

            System.arraycopy(featureArray, 0, XTrain, 0, splitIndex);
            System.arraycopy(labelArray, 0, yTrain, 0, splitIndex);
            System.arraycopy(featureArray, splitIndex, XTest, 0, featureArray.length - splitIndex);
            System.arraycopy(labelArray, splitIndex, yTest, 0, labelArray.length - splitIndex);

            // Train Logistic Regression Model
            LogisticRegression lr = new LogisticRegression(XTrain[0].length, 0.1, 1000);
            lr.fit(XTrain, yTrain);

            // Evaluate the model
            int correct = 0;
            for (int i = 0; i < XTest.length; i++) {
                int prediction = lr.predict(XTest[i]);
                if (prediction == yTest[i]) {
                    correct++;
                }
            }

            double accuracy = (double) correct / XTest.length * 100;
            System.out.println("Accuracy: " + accuracy + "%");
        } catch (IOException e) {
            System.err.println("Error loading dataset: " + e.getMessage());
        }
    }
        // TODO code application logic here
}
    
