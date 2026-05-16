package com.example.tpo9.Service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculateBmi(double weightKg, double heightCm) {
        double heightM = heightCm / 100.0;
        return weightKg / Math.pow(heightM, 2);
    }

    public String determineBmiType(double bmi) {
        if (bmi < 19) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    public double calculateBmr(String gender, double weightKg, double heightCm, int age) {
        if ("man".equalsIgnoreCase(gender)) {
            return 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age);
        } else if ("woman".equalsIgnoreCase(gender)) {
            return 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
        }
        throw new IllegalArgumentException("Invalid gender data");
    }
}