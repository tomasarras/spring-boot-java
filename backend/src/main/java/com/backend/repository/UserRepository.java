package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUsername(String username);

}
