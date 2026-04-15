package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConversionController {

    @PostMapping("/convert")
    @ResponseBody
    public String convert(@RequestParam(required = true) String value, @RequestParam(required = true) int from, @RequestParam(required = true) int to){
        if((from < 2 || from > 36) || (to < 2 || to > 36)){
            return "<html>" +
                    "<head><title>Error</title><link rel='stylesheet' href='/style.css'></head>" +
                    "<body>" +
                    "<div class='card'>" +
                    "<div class='error'>fromBase or toBase outside the allowed range.</div>" +
                    "<br><a href='/intconvertor.html' class='btn'>Go Back</a>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
        } else {
            try {
                Integer num = Integer.parseInt(value, from);
                String num_bin = Integer.toBinaryString(num);
                String num_oct  = Integer.toOctalString(num);
                String num_hex= Integer.toHexString(num);
                String num_dec = Integer.toString(num,10);
                String result = Integer.toString(num , to);
                return "<html>" +
                        "<head><title>Result</title><link rel='stylesheet' href='/style.css'></head>" +
                        "<body>" +
                        "<div class='card'>" +
                        "<h3>Result (Base " + to + ")</h3>" +
                        "<h2 style='color: #ffe066;'>" + result.toUpperCase() + "</h2>" +
                        "<hr style='border-color: #444; margin: 20px 0;'>" +
                        "<div style='text-align: left; font-size: 14px; color: #ccc;'>" +
                        "<p><b>BIN:</b> " + num_bin + "</p>" +
                        "<p><b>OCT:</b> " + num_oct + "</p>" +
                        "<p><b>DEC:</b> " + num_dec + "</p>" +
                        "<p><b>HEX:</b> " + num_hex + "</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
            } catch (NumberFormatException e){
                return "<html>" +
                        "<head><title>Error</title><link rel='stylesheet' href='/style.css'></head>" +
                        "<body>" +
                        "<div class='card'>" +
                        "<div class='error'>Invalid numeric value for the given base.</div>" +
                        "<br><a href='/intconvertor.html' class='btn'>Go Back</a>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
            }

        }
    }
}
