package com.my.boy.supershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.my.boy.supershop.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
