package com.backend.service;

import java.util.List;
import java.util.Optional;

import com.backend.entity.User;

public interface UserService {
	User save(User user);
	
	Optional<User> findUserByUsername(String username);
	
	boolean isValidPassword(User user, String password);

	List<User> findAll();

	void deleteById(Long id);

	Optional<User> findById(Long id);

	void edit(User u);
}
