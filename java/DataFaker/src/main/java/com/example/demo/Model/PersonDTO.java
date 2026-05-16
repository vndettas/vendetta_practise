package com.example.demo.Model;

import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Map;

public class PersonDTO{

    Person person;

    String address;

    String university;

    String countryOfOrigin;

    String countryOfResidence;

    String nationality;

    String phoneNumber;

    String email;

    String job;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

//    @Override
//    public String toString() {
//        return "PersonDTO{" +
//                "person=" + person.toString() +
//                ", address='" + address + '\'' +
//                ", university='" + university + '\'' +
//                ", countryOfOrigin='" + countryOfOrigin + '\'' +
//                ", countryOfResidence='" + countryOfResidence + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", email='" + email + '\'' +
//                ", job='" + job + '\'' +
//                '}';
//    }

}
