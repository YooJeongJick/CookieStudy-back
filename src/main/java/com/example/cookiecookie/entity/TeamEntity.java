package com.example.cookiecookie.entity;

import com.example.cookiecookie.core.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class TeamEntity extends BaseEntity {

    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<CookieEntity> cookies;

}
