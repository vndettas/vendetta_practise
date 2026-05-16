package com.example.demo.Controller;


import com.example.demo.Model.AdditionalField;
import com.example.demo.Model.DataGenerationRequest;
import com.example.demo.Model.DataGenerationResponse;
import com.example.demo.Model.PersonDTO;
import com.example.demo.Service.FakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DataGeneratorController {

    @Autowired
    FakeDataService generator;

    @PostMapping("/dataGenerator")
    @ResponseBody
    public String dataGenerator(@RequestParam("numberOfEntries") int numberOfEntries,
                                @RequestParam("locale") String locale,
                                @RequestParam(value = "additionalFields", required = false) List<AdditionalField> additionalFields) {
        DataGenerationRequest request = new DataGenerationRequest(numberOfEntries, locale, additionalFields);
        String response =  generator.generate(request);

        return response;
    }
}
