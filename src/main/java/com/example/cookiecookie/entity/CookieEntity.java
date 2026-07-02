package com.example.cookiecookie.entity;

import jakarta.persistence.*;

@Entity
public class CookieEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity user;

    @Column(nullable = false)
    private String cookieName;

    @Column(nullable = false)
    private String cookieLevel;

    @Column(nullable = false)
    private String cookieAttribute;

}
