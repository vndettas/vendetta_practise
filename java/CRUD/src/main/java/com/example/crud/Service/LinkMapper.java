package com.example.crud.Service;

import com.example.crud.Model.Link;
import com.example.crud.Model.LinkRequestDTO;
import com.example.crud.Model.LinkResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class LinkMapper {

    private static final String BASE_URL = "http://localhost:8080/red/";

    public LinkResponseDTO toResponseDto(Link link) {
        LinkResponseDTO dto = new LinkResponseDTO();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetUrl());
        dto.setRedirectUrl(BASE_URL + link.getId());
        dto.setVisits(link.getVisits());

        return dto;
    }

    public Link toEntity(LinkRequestDTO dto) {
        Link link = new Link();
        link.setName(dto.getName());
        link.setTargetUrl(dto.getTargetUrl());
        link.setPassword(dto.getPassword());

        return link;
    }
}
