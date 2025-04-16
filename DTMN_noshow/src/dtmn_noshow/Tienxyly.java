/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtmn_noshow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author phuon
 */
public class Tienxyly {

    // doc và xử lý dữ liệu file csv
    public static ArrayList<Benhnhan> loadData(String filePath) {
        ArrayList<Benhnhan> dataList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); // đúng format trong file CSV

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // bỏ qua dòng tiêu đề
            while ((line = br.readLine()) != null) {
                String[] token = line.split(",");

                if (token.length < 9) {
                    continue; // bỏ qua dòng thiếu cột
                }
                double gender = Double.parseDouble(token[0]);
                double age = Double.parseDouble(token[3]);

                // Parse ngày
                double bookDate = Double.parseDouble(token[1]);
                double appDate = Double.parseDouble(token[2]);

                long diffDays = (long) ((appDate - bookDate) / (1000 * 60 * 60 * 24));

                double schoolarship = Double.parseDouble(token[4]);
                double Hipertension = Double.parseDouble(token[5]);
                double Diabetes = Double.parseDouble(token[6]);
                double Alcoholism = Double.parseDouble(token[7]);
                double Handcap = Double.parseDouble(token[8]);
                double SMS_received = Double.parseDouble(token[9]);

                int label = token[10].equalsIgnoreCase("Yes") ? 1 : 0;


                double[] features = {
                    age / 100.0,
                    gender,
                    schoolarship,
                    Hipertension,
                    Diabetes,
                    Alcoholism,
                    Handcap,
                    SMS_received,
                    diffDays / 30.0 // khoảng cách ngày, chuẩn hóa theo tháng
                };

                dataList.add(new Benhnhan(features, label));
            }
            
            // HELLOW CA NHA

        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }

        return dataList;
    }

// chia dl thành tập huấn luận và kiểm tra
    public static void splitData(ArrayList<Benhnhan> data,
            ArrayList<Benhnhan> train,
            ArrayList<Benhnhan> test,
            double trainRatio) {
        Collections.shuffle(data); // xáo trộn ngẫu nhiên

        int trainSize = (int) (data.size() * trainRatio);

        for (int i = 0; i < data.size(); i++) {
            if (i < trainSize) {
                train.add(data.get(i));
            } else {
                test.add(data.get(i));
            }
        }
    }

    //Đánh giá độ chính xác của mô hình
public static void evaluateAccuracy(LogisticRegression model, ArrayList<Benhnhan> testSet) {
     int tp = 0, tn = 0, fp = 0, fn = 0;
     
     double[][] X_test = new double[testSet.size()][testSet.get(0).features.length];
     int[] Y_test = new int[testSet.size()];
     
     // Chuyển dữ liệu kiểm tra thành mảng cho LogisticRegression
     for (int i = 0; i < testSet.size(); i++) {
         X_test[i] = testSet.get(i).features;
         Y_test[i] = testSet.get(i).label;
     }

     for (int i = 0; i < X_test.length; i++) {
         int pred = model.predict(X_test[i]);
         int actual = Y_test[i];

         if (pred == 1 && actual == 1) {
             tp++;
         } else if (pred == 0 && actual == 0) {
             tn++;
         } else if (pred == 1 && actual == 0) {
             fp++;
         } else if (pred == 0 && actual == 1) {
             fn++;
         }
     }

     double accuracy = (double) (tp + tn) / testSet.size();
     double precision = tp + fp == 0 ? 0 : (double) tp / (tp + fp);
     double recall = tp + fn == 0 ? 0 : (double) tp / (tp + fn);
     double f1 = (precision + recall == 0) ? 0 : 2 * precision * recall / (precision + recall);

     System.out.printf("Accuracy: %.2f%%\n", accuracy * 100);
     System.out.printf("Precision: %.2f%%\n", precision * 100);
     System.out.printf("Recall: %.2f%%\n", recall * 100);
     System.out.printf("F1 Score: %.2f%%\n", f1 * 100);
     System.out.printf("TP: %d, TN: %d, FP: %d, FN: %d\n", tp, tn, fp, fn);
}
}
