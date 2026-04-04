package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    @ResponseBody
    public String getTime(@RequestParam(required = false, defaultValue = "HH:mm:ss.SSSS yyyy/MM/dd") String format, @RequestParam(required = false, defaultValue = "Europe/Warsaw", name = "timezone") String zoneId) {

        DateTimeFormatter dateFormatter;
        StringBuilder errorMessage = new StringBuilder();

        try {
            dateFormatter = DateTimeFormatter.ofPattern(format);
        } catch (IllegalArgumentException e) {
            dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSS yyyy/MM/dd");
            errorMessage.append("<div class='error'>Invalid format provided. Defaulting to standard format.</div>");
        }

        if (validTimeZone(zoneId)) {
            ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(zoneId));
            String result = dateTime.format(dateFormatter);
            return "<html>" +
                    "<head><title>Time Result</title>" +
                    "<link rel='stylesheet' href='/style.css'></head>" +
                    "<body>" +
                    "<div class='card'>" +
                    errorMessage +
                    "<h3>Current Time</h3>" +
                    "<h2 style='color: #ffe066;'>" + result + "</h2>" +
                    "<br>" +
                    "<a href='/current-time.html' class='btn'>Go Back</a>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
        } else {
            ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());
            String result = dateTime.format(dateFormatter);
            errorMessage.append("<div class='error'>Invalid time zone provided. Defaulting to standard time zone.</div>");
            return "<html>" +
                    "<head><title>Time Result</title>" +
                    "<link rel='stylesheet' href='/style.css'></head>" +
                    "<body>" +
                    "<div class='card'>" +
                    errorMessage +
                    "<h3>Current Time</h3>" +
                    "<h2 style='color: #ffe066;'>" + result + "</h2>" +
                    "<br>" +
                    "<a href='/current-time.html' class='btn'>Go Back</a>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
        }
    }

    public boolean validTimeZone(String timezone) {
        return Set.of(TimeZone.getAvailableIDs()).contains(timezone);
    }

    @GetMapping("/current-year")
    @ResponseBody
    public int getYear(){
        return LocalDate.now().getYear();
    }

}
