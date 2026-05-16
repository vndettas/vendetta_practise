package com.example.tpo7.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Code implements Serializable {

    private String id;

    private String codeBefore;

    private String codeAfter;

    private LocalDateTime expirationTime;

    public Code(String id, String before, String after, LocalDateTime expirationTime) {
        this.id = id;
        this.codeBefore = before;
        this.codeAfter = after;
        this.expirationTime = expirationTime;
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

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
