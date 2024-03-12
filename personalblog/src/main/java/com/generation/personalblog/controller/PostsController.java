package com.generation.personalblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.personalblog.model.Posts;
import com.generation.personalblog.repository.PostsRepository;
import com.generation.personalblog.repository.ThemeRepository;

import jakarta.validation.Valid;

//Defines that the class is a RestController (receive requests composed by URL, verb and request body
@RestController
//Defines the default URL
@RequestMapping("/posts")
//Allows requests done out of the original domain
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostsController {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@GetMapping
	public ResponseEntity<List<Posts>> getAll(){
		return ResponseEntity.ok(postsRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Posts> getById(@PathVariable Long id){
		return postsRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Posts>> getByTittle(@PathVariable String title){
		return ResponseEntity.ok(postsRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Posts> post(@Valid @RequestBody Posts posts){
		if (themeRepository.existsById(posts.getTheme().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(postsRepository.save(posts));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Theme does not exist", null);
	}
	
	@PutMapping
	public ResponseEntity<Posts> put(@Valid @RequestBody Posts posts){
		if(postsRepository.existsById(posts.getId())) {
			
			if(themeRepository.existsById(posts.getTheme().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(postsRepository.save(posts));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Posts> posts = postsRepository.findById(id);
		
		if(posts.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postsRepository.deleteById(id);
	}

}
