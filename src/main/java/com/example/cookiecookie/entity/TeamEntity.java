package com.example.cookiecookie.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class TeamEntity {

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<CookieEntity> cookies;

    @Column(nullable = false, unique = true)
    private String teamId;

    @Column(nullable = false)
    private String teamName;

}
