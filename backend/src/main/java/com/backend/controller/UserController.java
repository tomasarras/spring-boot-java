package com.backend.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.config.JWTConfigurer;
import com.backend.entity.User;
import com.backend.service.LogService;
import com.backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("logService")
	private LogService logService;
	
	@ApiOperation(value = "Register")
	@ApiResponses(value = {
			@ApiResponse(code = 409, message = "Username already exists"),
			@ApiResponse(code = 201, message = "Created")
	})
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user) {
		logService.info(this.getClass(), "METHOD: 'register()' PARAM user=" + user);
		boolean existsUser = userService.findUserByUsername(user.getUsername()).isPresent();
		if (existsUser) {
			return ResponseEntity.status(Response.SC_CONFLICT).build();
		} else {
			userService.save(user);
			String token = getJWTToken(user);
			return ResponseEntity.status(Response.SC_CREATED).body(token);
		}
	}
	
	
	
	
	@ApiOperation(value = "Login")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Invalid username or password")
	})
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody User user) {
		logService.info(this.getClass(), "METHOD: 'login()' PARAM user=" + user);
		Optional<User> userDB = userService.findUserByUsername(user.getUsername());
		
		if (userDB.isEmpty()) {
			return ResponseEntity.status(Response.SC_UNAUTHORIZED).build();
		} else {
			boolean match = userService.isValidPassword(userDB.get(), user.getPassword());
			if (match) {
				return ResponseEntity.status(Response.SC_OK).body(getJWTToken(userDB.get()));
			} else {
				return ResponseEntity.status(Response.SC_UNAUTHORIZED).build();
			}
		}
	}
	
	
	
	
	@ApiOperation(value = "Exists username")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Exists"),
			@ApiResponse(code = 404, message = "Not exists")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "username", dataTypeClass = String.class, required = true)
	})
	@GetMapping("/username/{username}")
	public ResponseEntity<Void> existsUsername(@PathVariable String username) {
		logService.info(this.getClass(), "METHOD: 'existsUsername()' PARAM username=" + username);
		boolean exists = userService.findUserByUsername(username).isPresent();
		if (exists)
			return ResponseEntity.status(Response.SC_OK).build();
		else
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
	}
	
	
	
	
	@ApiOperation(value = "Get all users, requires role admin")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public List<User> getUsers() {
		logService.info(this.getClass(), "METHOD: 'getUsers()'");
		return userService.findAll();
	}
	
	
	
	@ApiOperation(value = "Delete user, requires role admin")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Deleted no content"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id user", dataTypeClass = Long.class,required = true)
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'deleteUser()' PARAM id=" + id);
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} else {
			userService.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		}
	}
	
	
	
	
	@ApiOperation(value = "Switch admin role of one user, requires role admin")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id user", dataTypeClass = Long.class, required = true)
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<User> editAdmin(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'editAdmin()' PARAM id=" + id);
		Optional<User> userDB = userService.findById(id);
		if (userDB.isEmpty()) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} else {
			User u = userDB.get();
			u.setAdmin(!u.isAdmin());
			userService.edit(u);
			return ResponseEntity.status(Response.SC_OK).body(u);
		}
	}
	
	
	

	private String getJWTToken(User user) {
		String roles = "ROLE_USER";
		if (user.isAdmin()) {
			roles += ",ROLE_ADMIN";
		}
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(roles);
		
		String token = Jwts
				.builder()
				.setId(user.getId().toString())
				.setSubject(user.getUsername())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWTConfigurer.EXPIRE_JWT))
				.signWith(SignatureAlgorithm.HS512,
						JWTConfigurer.PRIVATE_PASSWORD.getBytes()).compact();

		return "Bearer " + token;
	}

}
