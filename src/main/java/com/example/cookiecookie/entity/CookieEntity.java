package com.example.cookiecookie.entity;

import com.example.cookiecookie.core.entity.BaseEntity;
import com.example.cookiecookie.dto.CookieDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CookieEntity extends BaseEntity {

    private String cookieName;
    private String cookieLevel;
    private String cookieAttribute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    public void update(CookieDto cookieDto) {
        this.cookieName = cookieDto.getCookieName();
        this.cookieLevel = cookieDto.getCookieLevel();
        this.cookieAttribute = cookieDto.getCookieAttribute();
    }

}
