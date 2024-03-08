package com.generation.personalblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.personalblog.model.Posts;
import com.generation.personalblog.repository.PostsRepository;

//Defines that the class is a RestController (receive requests composed by URL, verb and request body
@RestController
//Defines the default URL
@RequestMapping("/posts")
//Allows requests done out of the original domain
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostsController {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@GetMapping
	public ResponseEntity<List<Posts>> getAll(){
		return ResponseEntity.ok(postsRepository.findAll());
	}

}
