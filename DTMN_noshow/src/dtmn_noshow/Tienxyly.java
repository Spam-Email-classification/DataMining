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

public static ArrayList<Benhnhan> loadData(String filePath) {
    ArrayList<Benhnhan> dataList = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); // đúng format trong file CSV

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line = br.readLine(); // bỏ qua dòng tiêu đề
        while ((line = br.readLine()) != null) {
            String[] token = line.split(",");

            if (token.length < 9) continue; // bỏ qua dòng thiếu cột

            double age = Double.parseDouble(token[0]);
            double gender = token[1].equals("F") ? 1.0 : 0.0;

            // Parse ngày
            Date bookDate = sdf.parse(token[2]);
            Date appDate = sdf.parse(token[3]);

            long diffDays = (appDate.getTime() - bookDate.getTime()) / (1000 * 60 * 60 * 24);

            double distance = Double.parseDouble(token[4]);
            double disease = Double.parseDouble(token[5]);
            double previous = Double.parseDouble(token[6]);
            double sms = Double.parseDouble(token[7]);

            int label = Integer.parseInt(token[8]);

            double[] features = {
                age / 100.0,
                gender,
                distance,
                disease,
                previous,
                sms,
                diffDays / 30.0  // khoảng cách ngày, chuẩn hóa theo tháng
            };

            dataList.add(new Benhnhan(features, label));
        }

    } catch (Exception e) {
        System.out.println("Lỗi khi đọc file CSV: " + e.getMessage());
    }

    return dataList;
}

 public static void splitData(ArrayList<Benhnhan> data, 
                                 ArrayList<Benhnhan> train, 
                                 ArrayList<Benhnhan> test, 
                                 double trainRatio) {
        Collections.shuffle(data); // xáo trộn ngẫu nhiên

        int trainSize = (int)(data.size() * trainRatio);

        for (int i = 0; i < data.size(); i++) {
            if (i < trainSize) {
                train.add(data.get(i));
            } else {
                test.add(data.get(i));
            }
        }
    }

    public static double evaluate(LogisticRegression model, ArrayList<Benhnhan> testSet) {
        int correct = 0;
        for (Benhnhan bn : testSet) {
            int predicted = model.predict(bn.features);
            if (predicted == bn.label) {
                correct++;
            }
        }
        return (double) correct / testSet.size();
    }
    

    

}
