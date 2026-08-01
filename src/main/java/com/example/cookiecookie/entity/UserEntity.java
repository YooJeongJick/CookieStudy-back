package com.example.cookiecookie.entity;

import com.example.cookiecookie.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String nickname;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CookieEntity> cookies;

}
