package com.example.tpo7.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CodeDTO implements Serializable {

    private String id;

    private String codeBefore;

    private String codeAfter;

    private LocalDateTime durationDate;

    public CodeDTO(Code code) {
        this.id = code.getId();
        this.codeBefore = code.getCodeBefore();
        this.codeAfter = code.getCodeAfter();
        this.durationDate = code.getExpirationTime();
    }

    public String getId() {
        return id;
    }

    public String getCodeBefore() {
        return codeBefore;
    }

    public String getCodeAfter() {
        return codeAfter;
    }

    public LocalDateTime getDurationDate() {
        return durationDate;
    }
}
