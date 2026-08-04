package com.example.cookiecookie.repository;

import com.example.cookiecookie.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);

    Optional<UserEntity> findByLoginId(String loginId);
}
