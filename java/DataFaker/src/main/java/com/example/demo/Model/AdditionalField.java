package com.example.demo.Model;

public enum AdditionalField {
    ADDRESS ("address"),
    UNIVERSITY ("university"),
    COUNTRYOFORIGIN("country of origin"),
    COUNTRYOFRESIDENCE("country of residence"),
    NATIONALITY("nationality"),
    PHONE("phone"),
    EMAIL("email"),
    JOB("job")
    ;

    public String name;

    AdditionalField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

