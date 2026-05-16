package com.example.tpo7.Controller;


import com.example.tpo7.Model.Code;
import com.example.tpo7.Model.CodeDTO;
import com.example.tpo7.Service.CodeFormatterService;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class CodeFormatterController {

    CodeFormatterService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/code";
    }

    public CodeFormatterController(CodeFormatterService service) {
        this.service = service;
    }

    @GetMapping("/code")
    public String getCode() {
        return "code";
    }

    @PostMapping("/code")
    public String formatCode(@RequestParam String before, RedirectAttributes redirectAttributes) {
        try {
            String after = service.format(before);
            redirectAttributes.addFlashAttribute("codeBefore", before);
            redirectAttributes.addFlashAttribute("codeAfter", after);
            return "redirect:/code";
        } catch (FormatterException e) {
            redirectAttributes.addFlashAttribute("error", "Formatter error: " + e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/saveCode")
    public String saveCode(@RequestParam("ID") String id,
                           @RequestParam long days,
                           @RequestParam long hours,
                           @RequestParam long minutes,
                           @RequestParam long seconds,
                           @RequestParam String before,
                           RedirectAttributes redirectAttributes) {

        long totalSeconds = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).getSeconds();

        if (totalSeconds < 10 || totalSeconds > 90L * 24 * 3600) {
            redirectAttributes.addFlashAttribute("error", "Duration must be between 10 seconds and 90 days.");
            return "redirect:/errorPage";
        }

        try {
            LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(totalSeconds);
            service.saveCode(id, before, expirationTime);
            return "redirect:/findCode?ID=" + id;
        } catch (FormatterException e) {
            redirectAttributes.addFlashAttribute("error", "Formatter error: " + e.getMessage());
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/findCode")
    public String findCode(@RequestParam("ID") String id, Model model) {
        try {
            CodeDTO codeDto = service.findCode(id);
            model.addAttribute("codeDTO", codeDto);
            return "findCode";
        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/errorPage")
    public String showError(Model model) {
        return "error";
    }
}

