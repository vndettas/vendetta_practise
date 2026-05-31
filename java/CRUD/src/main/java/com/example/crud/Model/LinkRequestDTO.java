package com.example.crud.Model;

import com.example.crud.Validation.UniqueUrl;
import com.example.crud.Validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class LinkRequestDTO {

    @NotBlank(message = "{error.name.blank}")
    @Size(min = 5, max = 20, message = "{error.name.size}")
    private String name;

    @NotBlank(message = "{error.url.blank}")
    @URL(message = "{error.url.valid}")
    @Pattern(regexp = "^https://.*", message = "{error.url.https}")
    @UniqueUrl
    private String targetUrl;

    @ValidPassword
    private String password;
    public LinkRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
