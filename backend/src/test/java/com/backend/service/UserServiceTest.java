package com.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.entity.User;
import com.backend.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	

	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void testSave() {
		User userToSave = new User();
		userToSave.setUsername("username");
		userToSave.setPassword("password");
		
		User userExpected = new User();
		userExpected.setAdmin(false);
		userExpected.setId(1L);
		userExpected.setUsername(userToSave.getPassword());
		userExpected.setPassword("password-encoded");
		
		doReturn(userExpected.getPassword()).when(passwordEncoder).encode(userToSave.getPassword());
		doReturn(userExpected).when(userRepository).save(userToSave);
		
		User userSaved = userService.save(userToSave);
		assertEquals(true, userExpected.equals(userSaved));
	}
	
	@Test
	public void testFindUserByUsername() {
		User userDB = new User();
		userDB.setAdmin(false);
		userDB.setId(1L);
		userDB.setUsername("test");
		userDB.setPassword("password");
		
		doReturn(Optional.of(userDB)).when(userRepository).findUserByUsername(userDB.getUsername());
		assertEquals(true, userDB.equals(userService.findUserByUsername(userDB.getUsername()).get()));
	}
	
	@Test
	public void testIsValidPassword() {
		User u = new User();
		u.setAdmin(false);
		u.setId(1L);
		u.setPassword("password-encoded");
		u.setUsername("test");
		
		doReturn(true).when(passwordEncoder).matches("valid-plain-text-password", u.getPassword());
		assertEquals(true, userService.isValidPassword(u, "valid-plain-text-password"));
	}
	
	@Test
	public void testIsNotValidPassword() {
		User u = new User();
		u.setAdmin(false);
		u.setId(1L);
		u.setPassword("password-encoded");
		u.setUsername("test");
		
		doReturn(false).when(passwordEncoder).matches("not-valid-plain-text-password", u.getPassword());
		assertEquals(false, userService.isValidPassword(u, "not-valid-plain-text-password"));
	}
	
	@Test
	public void testFindAll() {
		User u1 = new User(1L,"test","password-encoded",false);
		User u2 = new User(2L,"test2","password-encoded",true);
		List<User> users = new ArrayList<User>();
		users.add(u2);
		users.add(u1);
		
		doReturn(users).when(userRepository).findAll();
		
		assertEquals(true, users.containsAll(userService.findAll()));
	}
	
	@Test
	public void testFindById() {
		User userDB = new User(1L,"test","password",false);
		doReturn(Optional.of(userDB)).when(userRepository).findById(userDB.getId());
		assertEquals(false, userService.findById(userDB.getId()).isEmpty());
	}
	
}
