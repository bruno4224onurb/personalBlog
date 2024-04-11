package com.bruno2442onurb.personalBlog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bruno2442onurb.personalBlog.model.Post;
import com.bruno2442onurb.personalBlog.repository.PostRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

  @Autowired
  private PostRepository postingRepository;

  @GetMapping
  public ResponseEntity<List<Post>> getAll() {
    return ResponseEntity.ok(postingRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getById(@PathVariable Long id) {
    return postingRepository.findById(id).map(response -> ResponseEntity.ok(response))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("title/{title}")
  public ResponseEntity<List<Post>>getByTitle(@PathVariable String title) {
    return ResponseEntity.ok(postingRepository.findAllByTitleContainingIgnoreCase(title));
  }

  @PostMapping
  public ResponseEntity<Post> createPost(@Valid @RequestBody Post newPost) {
    Post createdPost = postingRepository.save(newPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Post> updatePut(@Valid @RequestBody Post updatedPost) {
    return postingRepository.findById(updatedPost.getId()).map(response -> ResponseEntity.status(HttpStatus.OK).body(postingRepository.save(updatedPost)))
    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    /* 
    // FIXME Put here some method thats takes the old values and save 
    */ 
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id){
    Optional<Post> selectedPosting = postingRepository.findById(id);
    if (selectedPosting.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    postingRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
