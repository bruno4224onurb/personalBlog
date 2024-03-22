package com.generation.personalblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.personalblog.model.UserLogin;
import com.generation.personalblog.model.User;
import com.generation.personalblog.repository.UserRepository;
import com.generation.personalblog.security.JwtService;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

	public Optional<User> registerUser(User user) {

		if (userRepository.findByUserName(user.getUserName()).isPresent())
			return Optional.empty();

		user.setPassword(encryptPassword(user.getPassword()));

		return Optional.of(userRepository.save(user));
	
	}

	public Optional<User> updateUsuario(User user) {
		
		if(userRepository.findById(user.getId()).isPresent()) {

			Optional<User> searchUser = userRepository.findByUserName(user.getUserName());

			if ( (searchUser.isPresent()) && ( searchUser.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!", null);

			user.setPassword(encryptPassword(user.getPassword()));

			return Optional.ofNullable(userRepository.save(user));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UserLogin> authUser(Optional<UserLogin> userLogin) {
        
		var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getUser(), userLogin.get().getPassword());
		
		Authentication authentication = authenticationManager.authenticate(credentials);
        
		if (authentication.isAuthenticated()) {
			
			Optional<User> usuario = userRepository.findByUserName(userLogin.get().getUser());
			
			if (usuario.isPresent()) {
			   userLogin.get().setId(usuario.get().getId());
			   userLogin.get().setName(usuario.get().getName());
			   userLogin.get().setUserPhoto(usuario.get().getUserPhoto());
               userLogin.get().setToken(generateToken(userLogin.get().getUser()));
               userLogin.get().setPassword("");
			   return userLogin;
			
			}

        } 
            
		return Optional.empty();

    }

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);

	}

	private String generateToken(String user) {
		return "Bearer " + jwtService.generateToken(user);
	}


}
