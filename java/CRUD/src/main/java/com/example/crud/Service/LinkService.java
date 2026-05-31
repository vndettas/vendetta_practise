package com.example.crud.Service;

import com.example.crud.Model.Link;
import com.example.crud.Repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {
        this.repository = repository;
    }

    public String generateRandomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Link createLink(Link link) {
        String id;
        id = generateRandomId();
        link.setId(id);
        link.setVisits(0);
        return repository.save(link);
    }

    public Optional<Link> getLink(String id) {
        return repository.findById(id);
    }

    public Optional<Link> incrementVisitsAndGetRedirectUrl(String id) {
        Optional<Link> optionalLink = repository.findById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            link.setVisits(link.getVisits() + 1);
            repository.save(link);
        }
        return optionalLink;
    }

    public boolean checkPassword(Link link, String providedPassword) {
        String savedPassword = link.getPassword();

        if (savedPassword == null || savedPassword.isEmpty()) {
            return providedPassword == null || providedPassword.isEmpty();
        }

        return savedPassword.equals(providedPassword);
    }

    public Link save(Link link) {
        return repository.save(link);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
