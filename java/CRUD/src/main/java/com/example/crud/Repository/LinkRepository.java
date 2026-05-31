package com.example.crud.Repository;

import com.example.crud.Model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, String> {

    boolean existsByTargetUrl(String targetUrl);

    Optional<Link> findByName(String name);
}
