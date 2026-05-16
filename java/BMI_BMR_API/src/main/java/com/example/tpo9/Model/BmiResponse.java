package com.example.tpo9.Model;

public class BmiResponse {

    private double weight;

    private double height;

    private int bmi;

    private String type;

    public BmiResponse(double weight, double height, int bmi, String type) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public int getBmi() {
        return bmi;
    }

    public double getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }
}
