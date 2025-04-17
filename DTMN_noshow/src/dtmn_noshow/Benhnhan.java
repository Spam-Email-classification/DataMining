/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtmn_noshow;

/**
 *
 * @author phuon
 */
public class Benhnhan {
    public double [] features; // cac thuộc tính trong file
    public int label; // 0 va 1

    public Benhnhan(double[] features, int label) {
        this.features = features;
        this.label = label;
    }

    public Benhnhan() {
    }

    public double[] getFeatures() {
        return features;
    }

    public void setFeatures(double[] features) {
        this.features = features;
    }

    public int getLabel() {
        return label;
    }
    
    public void setLabel(int label) {
        this.label = label;
    }
}
