package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);
}
