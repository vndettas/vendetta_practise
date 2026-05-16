package com.example.tpo7.Service;

import com.example.tpo7.Model.Code;
import com.example.tpo7.Model.CodeDTO;
import com.example.tpo7.Repository.CodeRepository;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CodeFormatterService {

    private final CodeRepository repository;


    public CodeFormatterService(CodeRepository repository) {
        this.repository = repository;
    }

    public String format(String before) throws FormatterException {
        return new Formatter().formatSource(before);
    }

    public CodeDTO findCode(String id) throws FileNotFoundException {
        Optional<Code> code = repository.findById(id);
        if(code.isPresent()){
            CodeDTO codeDTO = new CodeDTO(code.get());
            return codeDTO;
        } else {
            throw new FileNotFoundException("Code for this id was not found");
        }
    }

    public void saveCode(String id, String before, LocalDateTime duration) throws FormatterException {
        String after = format(before);
        Code code = new Code(id, before, after, duration);
        repository.save(code);
    }

    public void delete(String id) {
        repository.delete(id);
    }

    @Scheduled(fixedRate = 10000)
    public void cleanupExpiredCodes() {
        repository.deleteExpired();
    }
}
