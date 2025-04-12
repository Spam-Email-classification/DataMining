/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.util.List;

/**
 *
 * @author admin
 */
public class Normalizer {
     public static void normalize(List<double[]> data) {
        int numFeatures = data.get(0).length;

        for (int i = 0; i < numFeatures; i++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            // Find min and max for each feature
            for (double[] row : data) {
                min = Math.min(min, row[i]);
                max = Math.max(max, row[i]);
            }

            // Normalize each value
            for (double[] row : data) {
                row[i] = (row[i] - min) / (max - min);
            }
        }
    }
    
}
