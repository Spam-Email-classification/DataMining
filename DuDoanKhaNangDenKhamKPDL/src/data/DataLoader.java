package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataLoader {
    public static void loadCSV(String filePath, List<double[]> features, List<Integer> labels) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Bỏ qua dòng tiêu đề
                    continue;
                }

                // Tách các giá trị trong dòng
                String[] values = line.split(",");

                // Lấy cột Gender và ánh xạ thành số
                double gender = values[2].equalsIgnoreCase("M") ? 0 : 1; // M → 0, F → 1

                // Chuyển đổi các cột số
                double age = Double.parseDouble(values[5]); // Age
                double scholarship = Double.parseDouble(values[7]);
                double hypertension = Double.parseDouble(values[8]);
                double diabetes = Double.parseDouble(values[9]);
                double alcoholism = Double.parseDouble(values[10]);
                double handicap = Double.parseDouble(values[11]);
                double smsReceived = Double.parseDouble(values[12]);

                // Chuyển đổi cột NoShow thành nhãn
                int noShow = values[13].equalsIgnoreCase("No") ? 0 : 1; // No → 0, Yes → 1

                // Tạo một mảng tính năng
                double[] featureRow = new double[]{
                    gender, age, scholarship, hypertension, diabetes, alcoholism, handicap, smsReceived
                };

                features.add(featureRow);
                labels.add(noShow); // Cột cuối cùng là nhãn (NoShow)
            }
        }
    }
}
