package dtmn_noshow;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;

public class DTMN_noshow {

    public static void main(String[] args) {
        ArrayList<Benhnhan> dataset = Tienxyly.loadData("C:\\NetBeansProjects\\datasetcsv_edit27.csv");
        if (dataset.isEmpty()) {
            System.out.println("Không đọc được dữ liệu từ file CSV. Vui lòng kiểm tra lại.");
            return; }

        int m = dataset.size();
        int n = dataset.get(0).features.length;
        System.out.println("Du lieu da xu ly xong. So mau: " + m);

        // 1️⃣ Tách tập train/test
        ArrayList<Benhnhan> trainSet = new ArrayList<>();
        ArrayList<Benhnhan> testSet = new ArrayList<>();
        Tienxyly.splitData(dataset, trainSet, testSet, 0.8);

        System.out.println("Tap huan luyen: " + trainSet.size() + ", Tap kiem tra: " + testSet.size());
        // 2️⃣ Chuẩn bị dữ liệu huấn luyện
        double[][] X_train = new double[trainSet.size()][n];
        int[] Y_train = new int[trainSet.size()];
        for (int i = 0; i < trainSet.size(); i++) {
            X_train[i] = trainSet.get(i).features;
            Y_train[i] = trainSet.get(i).label;
        }

        // 3️⃣ Huấn luyện
        LogisticRegression model = new LogisticRegression(n);
        model.train(X_train, Y_train);

        // 4️⃣ Đánh giá
        Tienxyly.evaluateAccuracy(model, testSet);

//        // 5️⃣ Dự đoán thử
//        double[] newPatient = {0.30, 1.0, 2.5, 0, 1, 1, 0.25}; // đã chuẩn hóa
//        int prediction = model.predict(newPatient);
//        System.out.println("KET QUA DU DOAN BENH NHAN MOI: " + (prediction == 1 ? "NO SHOW" : "DEN KHAM"));

 // Khởi động giao diện
    SwingUtilities.invokeLater(() -> {
        new TestUI(model);
    });
    }
}




