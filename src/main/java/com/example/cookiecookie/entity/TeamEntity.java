package com.example.cookiecookie.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private long id;

    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<CookieEntity> cookies;


}
