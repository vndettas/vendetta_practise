package com.example.tpo9.Controller;

import com.example.tpo9.Model.BmiResponse;
import com.example.tpo9.Model.BmrResponse;
import com.example.tpo9.Service.CalculatorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(path = "/api/v1")
public class WeightController {

    private final CalculatorService calculatorService;

    public WeightController(CalculatorService service) {
        this.calculatorService = service;
    }

    @GetMapping(path = "/BMI", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> calculateBmi(@RequestParam(name = "weight") double weight, @RequestParam(name = "height") double height) {
        if (weight <= 0 || height <= 0) {
            return ResponseEntity.status(400).header("reason", "invalid data, weight and height parameters must be positive numbers").build();
        }

        double bmiRaw = calculatorService.calculateBmi(weight, height);
        int bmiRounded = (int) Math.round(bmiRaw);
        String type = calculatorService.determineBmiType(bmiRaw);

        return ResponseEntity.ok(new BmiResponse(weight, height, bmiRounded, type));
    }

    @GetMapping(path = "/BMI", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getBmiText(@RequestParam double weight, @RequestParam double height) {
        if (weight <= 0 || height <= 0) {
            return ResponseEntity.status(400)
                    .header("reason", "invalid data, weight and height parameters must be positive numbers")
                    .build();
        }
        double bmiRaw = calculatorService.calculateBmi(weight, height);
        return ResponseEntity.ok(String.format(Locale.US, "%.2f", bmiRaw));
    }

    @GetMapping(path = "/BMR/{gender}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getBmr(@PathVariable(name = "gender") String gender, @RequestParam(name = "weight") double weight, @RequestParam(name = "height") double height, @RequestParam(name = "age") int age) {
        ResponseEntity<?> validationError = validateBmr(gender, weight, height, age);
        if (validationError != null) return validationError;

        double bmrRaw = calculatorService.calculateBmr(gender, weight, height, age);
        int bmrRounded = (int) Math.round(bmrRaw);

        return ResponseEntity.ok(new BmrResponse(gender, weight, height, age, bmrRounded));
    }

    @GetMapping(path = "/BMR/{gender}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getBmrText(@PathVariable String gender, @RequestParam double weight, @RequestParam double height, @RequestParam int age) {
        ResponseEntity<?> validationError = validateBmr(gender, weight, height, age);
        if (validationError != null) return validationError;

        double bmrRaw = calculatorService.calculateBmr(gender, weight, height, age);
        int bmrRounded = (int) Math.round(bmrRaw);

        return ResponseEntity.ok(bmrRounded + "kcal");
    }

    private ResponseEntity<?> validateBmr(String gender, double weight, double height, int age) {
        if (weight <= 0 || height <= 0 || age <= 0) {
            return ResponseEntity.status(499)
                    .header("reason", "invalid data, weight, height and age parameters must be positive numbers")
                    .build();
        }
        if (!gender.equalsIgnoreCase("man") && !gender.equalsIgnoreCase("woman")) {
            return ResponseEntity.status(400)
                    .header("reason", "invalid gender data")
                    .build();
        }
        return null;
    }
}
