package com.example.crud.Controller;

import com.example.crud.Model.Link;
import com.example.crud.Model.LinkRequestDTO;
import com.example.crud.Repository.LinkRepository;
import com.example.crud.Service.LinkService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class WebController {

    private final LinkService service;
    private final LinkRepository repository;

    public WebController(LinkService service, LinkRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("linkDto", new LinkRequestDTO());
        return "index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("linkDto") LinkRequestDTO linkDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        Link link = new Link();
        link.setName(linkDto.getName());
        link.setTargetUrl(linkDto.getTargetUrl());
        link.setPassword(linkDto.getPassword());
        service.createLink(link);
        return "redirect:/?success";
    }

    @GetMapping("/manage")
    public String showManageLogin() {
        return "manage-login";
    }

    @PostMapping("/manage")
    public String manageAuth(@RequestParam String name, @RequestParam String password, Model model) {
        Optional<Link> linkOpt = repository.findByName(name);

        if (linkOpt.isPresent() && service.checkPassword(linkOpt.get(), password)) {
            model.addAttribute("link", linkOpt.get());
            return "manage-details";
        }

        return "redirect:/manage?error";
    }

    @PostMapping("/manage/update/{id}")
    public String updateLinkFromUI(
            @PathVariable String id,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String targetUrl) {

        Optional<Link> linkOpt = service.getLink(id);
        if (linkOpt.isPresent() && service.checkPassword(linkOpt.get(), password)) {
            Link link = linkOpt.get();
            link.setName(name);
            link.setTargetUrl(targetUrl);
            service.save(link);
            return "redirect:/?success";
        }
        return "redirect:/manage?error";
    }

    @PostMapping("/manage/delete/{id}")
    public String deleteLinkFromUI(@PathVariable String id, @RequestParam String password) {
        Optional<Link> linkOpt = service.getLink(id);
        if (linkOpt.isPresent() && service.checkPassword(linkOpt.get(), password)) {
            service.delete(id);
            return "redirect:/?success";
        }
        return "redirect:/manage?error";
    }
}