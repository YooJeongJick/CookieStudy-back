package com.example.cookiecookie.repository;

import com.example.cookiecookie.entity.CookieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookieRepository extends JpaRepository<CookieEntity, Long> {
}
