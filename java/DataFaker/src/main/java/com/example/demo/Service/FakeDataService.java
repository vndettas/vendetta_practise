package com.example.demo.Service;

import com.example.demo.Model.*;
import net.datafaker.Faker;
import com.example.demo.Model.AdditionalField.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.example.demo.Model.AdditionalField.UNIVERSITY;

@Service
public class FakeDataService {

    Faker faker;

    public FakeDataService(){};

    public String generate(DataGenerationRequest request){

        DataGenerationResponse response = new DataGenerationResponse();

        faker = new Faker(new Locale(request.getLocale()));

        for (int i = 0; i < request.getNumberOfEntries(); i++){
            PersonDTO personDTO = new PersonDTO();
            Person person = new Person();
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setBirthDate(faker.timeAndDate().birthday());
            personDTO.setPerson(person);
            //todo: generate person required fields
            for (AdditionalField additionalFiled : request.getAdditionalFields()){
                switch(additionalFiled) {
                    case UNIVERSITY :
                        String university = faker.university().name();
                        personDTO.setUniversity(university);
                        break;
                    case ADDRESS:
                        String address = faker.address().fullAddress();
                        personDTO.setAddress(address);
                        break;
                    case NATIONALITY:
                        String nationality = faker.nation().nationality();
                        personDTO.setNationality(nationality);
                        break;
                    case COUNTRYOFORIGIN:
                        String countryOfOrigin = faker.country().name();
                        personDTO.setCountryOfOrigin(countryOfOrigin);
                        break;
                    case COUNTRYOFRESIDENCE:
                        String countryOfResidence = faker.country().name();
                        personDTO.setCountryOfResidence(countryOfResidence);
                        break;
                    case JOB:
                        String job = faker.job().position();
                        personDTO.setJob(job);
                        break;
                    case EMAIL:
                        String email = faker.internet().emailAddress();
                        personDTO.setEmail(email);
                        break;
                    case PHONE:
                        String phone = faker.phoneNumber().phoneNumber();
                        personDTO.setPhoneNumber(phone);
                        break;
                    default:
                        break;

                }

            }
            response.addPersonDto(personDTO);
        }

        return generateHTML(response, request.additionalFields);
    }


    public String generateHTML(DataGenerationResponse response, List<AdditionalField> requestedFields) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");

        html.append("<tr>");
        html.append("<th>First Name</th><th>Last Name</th><th>Birth Date</th>");

        if (requestedFields != null) {
            for (AdditionalField field : requestedFields) {
                html.append("<th>").append(field.name()).append("</th>");
            }
        }
        html.append("</tr>");

        for (PersonDTO personDTO : response.getPersons()) {
            Person p = personDTO.getPerson();

            html.append("<tr>");

            html.append("<td>").append(p.getFirstName()).append("</td>");
            html.append("<td>").append(p.getLastName()).append("</td>");
            html.append("<td>").append(p.getBirthDate()).append("</td>");

            if (requestedFields != null) {
                for (AdditionalField field : requestedFields) {
                    html.append("<td>");

                    switch (field) {
                        case UNIVERSITY:
                            html.append(personDTO.getUniversity() != null ? personDTO.getUniversity() : "");
                            break;
                        case ADDRESS:
                            html.append(personDTO.getAddress() != null ? personDTO.getAddress() : "");
                            break;
                        case NATIONALITY:
                            html.append(personDTO.getNationality() != null ? personDTO.getNationality() : "");
                            break;
                        case COUNTRYOFORIGIN:
                            html.append(personDTO.getCountryOfOrigin() != null ? personDTO.getCountryOfOrigin() : "");
                            break;
                        case COUNTRYOFRESIDENCE:
                            html.append(personDTO.getCountryOfResidence() != null ? personDTO.getCountryOfResidence() : "");
                            break;
                        case EMAIL:
                            html.append(personDTO.getEmail() != null ? personDTO.getEmail() : "");
                            break;
                        case JOB:
                            html.append(personDTO.getJob() != null ? personDTO.getJob() : "");
                            break;
                        case PHONE:
                            html.append(personDTO.getPhoneNumber() != null ? personDTO.getPhoneNumber() : "");
                            break;
                        default:
                            html.append("");
                    }

                    html.append("</td>");
                }
            }

            html.append("</tr>");
        }

        html.append("</table>");
        return html.toString();
    }
}
