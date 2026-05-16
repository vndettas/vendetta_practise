package com.example.tpo9.Model;

public class BmrResponse {

    private String gender;

    private double weight;

    private double height;

    private int age;

    private int bmr;

    public BmrResponse(String gender, double weight, double height, int age, int bmr) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.bmr = bmr;
    }

    public String getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public int getBmr() {
        return bmr;
    }
}
