package com.example.tpo7.Repository;

import com.example.tpo7.Model.Code;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class CodeRepository {

    private final String codeFilesPath = "codeFiles/";

    public void save(Code code) {
        File directory = new File(codeFilesPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(codeFilesPath + code.getId()))) {
            oos.writeObject(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Code> findById(String id) {
        File file = new File(codeFilesPath + id);

        if (!file.exists()) {
            return Optional.empty();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Code code = (Code) ois.readObject();
            return Optional.of(code);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public void delete(String id) {
        File file = new File(codeFilesPath + id);
        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteExpired() {
        File directory = new File(codeFilesPath);
        if (directory.exists() && directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Code code = (Code) ois.readObject();
                    if (LocalDateTime.now().isAfter(code.getExpirationTime())) {
                        ois.close();
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
