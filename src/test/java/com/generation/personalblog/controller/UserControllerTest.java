package com.generation.personalblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.personalblog.model.User;
import com.generation.personalblog.repository.UserRepository;
import com.generation.personalblog.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	void start() {
		userRepository.deleteAll();

		userService.registerUser(new User(0L, "Root", "root@root.com", "rootroot", " "));
	}

	@Test
	@DisplayName("Register a User")
	public void mustCreateUser() {
		HttpEntity<User> requisitionBody = new HttpEntity<User>(
				new User(0L, "Bruno", "bruno@gmail.com", "12345678", " "));

		ResponseEntity<User> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/signin", HttpMethod.POST,
				requisitionBody, User.class);
		assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());

	}

	@Test
	@DisplayName("Must block the creation of duplicated users")
	public void noDuplicatedUser() {

		userService.registerUser(new User(0L, "Test2", "test2@gmail.com", "12345678", " "));

		HttpEntity<User> requisitionBody = new HttpEntity<User>(
				new User(0L, "Test2", "test2@gmail.com", "12345678", " "));

		ResponseEntity<User> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/signin", HttpMethod.POST,
				requisitionBody, User.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("Update User")
	public void mustUpdateUser() {

		Optional<User> userRegistered = userService.registerUser(new User(0L, "Test3", "test3@gmail.com", "12345678", " "));
		
		User userUpdate = new User(userRegistered.get().getId(), "Test 3 update", "test3update@gmail.com", "123456789", " ");

		HttpEntity<User> requisitionBody = new HttpEntity<User>(userUpdate);

		ResponseEntity<User> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/update", HttpMethod.PUT,
				requisitionBody, User.class);
		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("List all Users")
	public void mustListAllUsers() {

		userService.registerUser(new User(0L, "Test4", "test4@gmail.com", "12345678", " "));
		
		userService.registerUser(new User(0L, "Test5", "test5@gmail.com", "12345678", " "));

		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/all", HttpMethod.GET,
				null, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
