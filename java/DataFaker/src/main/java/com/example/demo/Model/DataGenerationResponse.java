package com.example.demo.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataGenerationResponse{

    List<PersonDTO> response;

    public DataGenerationResponse() {
        this.response = new ArrayList<>();
    }


    public void addPersonDto(PersonDTO personDTO){
        response.add(personDTO);
    }

    public List<PersonDTO> getPersons(){
        return response;
    }
    @Override
    public String toString() {
        return "DataGenerationResponse{" +
                "response=" + response +
                '}';
    }
}
