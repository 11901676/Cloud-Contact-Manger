package com.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

//Repository folder is userd for DB related operations and files.
@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailToken(String token);
}
