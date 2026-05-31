package com.example.crud.Controller;

import com.example.crud.Model.Link;
import com.example.crud.Model.LinkRequestDTO;
import com.example.crud.Model.LinkResponseDTO;
import com.example.crud.Service.LinkMapper;
import com.example.crud.Service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LinkController {

    private final LinkService service;
    private final LinkMapper mapper;

    public LinkController(LinkService service, LinkMapper mapper) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping("/api/links")
    public ResponseEntity<LinkResponseDTO> saveLink(@Valid @RequestBody LinkRequestDTO requestDTO) {
        Link link = mapper.toEntity(requestDTO);
        Link savedLink = service.createLink(link);
        LinkResponseDTO responseDto = mapper.toResponseDto(savedLink);

        URI location = URI.create("http://localhost:8080/api/links/" + savedLink.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/api/links/{id}")
    public ResponseEntity<LinkResponseDTO> getLink(@PathVariable String id) {
        Optional<Link> linkOpt = service.getLink(id);
        return linkOpt.map(link -> ResponseEntity.ok(mapper.toResponseDto(link)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/api/links/{id}")
    public ResponseEntity<Void> updateLink(@PathVariable String id, @RequestBody LinkRequestDTO requestDTO) {
        Optional<Link> linkOpt = service.getLink(id);
        if (linkOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = linkOpt.get();
        if (!service.checkPassword(link, requestDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("reason", "wrong password")
                    .build();
        }

        if (requestDTO.getName() != null) {
            link.setName(requestDTO.getName());
        }
        if (requestDTO.getTargetUrl() != null) {
            link.setTargetUrl(requestDTO.getTargetUrl());
        }

        service.save(link);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<Void> deleteLink(
            @PathVariable String id,
            @RequestHeader(value = "pass", required = false) String pass) {

        Optional<Link> linkOpt = service.getLink(id);

        if (linkOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Link link = linkOpt.get();

        if (!service.checkPassword(link, pass)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("reason", "wrong password")
                    .build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/red/{id}")
    public ResponseEntity<Void> redirectLink(@PathVariable String id) {
        Optional<Link> linkOpt = service.incrementVisitsAndGetRedirectUrl(id);

        if (linkOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(linkOpt.get().getTargetUrl()))
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 + " | " + msg2
                ));
    }
}