package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;


public class DataGenerationRequest {

    public int numberOfEntries;

    public String locale;

    public List<AdditionalField> additionalFields;

    public DataGenerationRequest(int numberOfEntries, String locale, List<AdditionalField> additionalFields) {
        this.numberOfEntries = numberOfEntries;
        this.locale = locale;
        if (additionalFields == null) {
            this.additionalFields = new ArrayList<>();
        } else {
            this.additionalFields = additionalFields;
        }
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<AdditionalField> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(List<AdditionalField> additionalFields) {
        this.additionalFields = additionalFields;
    }
}
