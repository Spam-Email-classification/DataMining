/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/


package dtmn_noshow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestUI extends JFrame {

    private LogisticRegression model;

    private JTextField[] inputFields;
    private JLabel resultLabel;

    public TestUI(LogisticRegression model) {
        this.model = model;
        setTitle("Dự đoán No-Show");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] labels = {
            "Tuổi",
            "Giới tính (0=Nam, 1=Nữ)",
            "Khoảng cách đến bệnh viện (km)",
            "Có bệnh nền? (0/1)",
            "Số lần khám trước đó",
            "Đã nhận SMS? (0/1)",
            "Số ngày chờ"
        };

        inputFields = new JTextField[labels.length];

        JPanel inputPanel = new JPanel(new GridLayout(labels.length, 2, 5, 5));
        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(new JLabel(labels[i]));
            inputFields[i] = new JTextField();
            inputPanel.add(inputFields[i]);
        }

        JButton predictBtn = new JButton("Dự đoán");
        predictBtn.addActionListener(e -> predictNoShow());

        resultLabel = new JLabel("Kết quả sẽ hiện ở đây", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));

        setLayout(new BorderLayout(10, 10));
        add(resultLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(predictBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void predictNoShow() {
        try {
            double[] input = new double[inputFields.length];
            input[0] = Double.parseDouble(inputFields[0].getText()) / 100.0; // normalize age
            input[1] = Double.parseDouble(inputFields[1].getText()); // gender
            input[2] = Double.parseDouble(inputFields[2].getText()); // distance
            input[3] = Double.parseDouble(inputFields[3].getText()); // chronic disease
            input[4] = Double.parseDouble(inputFields[4].getText()); // previous appointments
            input[5] = Double.parseDouble(inputFields[5].getText()); // SMS received
            input[6] = Double.parseDouble(inputFields[6].getText()) / 30.0; // normalize waiting days

            int prediction = model.predict(input);
            if (prediction == 1) {
                resultLabel.setText("🟥 NO SHOW");
                resultLabel.setForeground(Color.RED);
            } else {
                resultLabel.setText("🟩 ĐẾN KHÁM");
                resultLabel.setForeground(Color.GREEN);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}
