package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String username);
}
