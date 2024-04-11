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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bruno2442onurb.personalBlog.model.Theme;
import com.bruno2442onurb.personalBlog.repository.ThemeRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/theme")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThemeController {

  @Autowired
  private ThemeRepository themeRepository;

  @GetMapping 
  public ResponseEntity<List<Theme>> getAll() {
    return ResponseEntity.ok(themeRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Theme> getById(@PathVariable Long id) {
    return themeRepository.findById(id).map(response -> ResponseEntity.ok(response))
        .orElse(ResponseEntity.notFound().build());
  }
  
  @GetMapping("/search")
  public ResponseEntity<List<Theme>> getByDescricao(@RequestParam String description) {
      List<Theme> themes = themeRepository.findAllByDescriptionContainingIgnoreCase(description);
      if(themes.isEmpty()) {
          return ResponseEntity.noContent().build();
      } else {
          return ResponseEntity.ok(themes);
      }
  }


  @PostMapping
  public ResponseEntity<Theme> createPosting(@Valid @RequestBody Theme newPost) {
    Theme createdPost = themeRepository.save(newPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
  }

  @PutMapping("/{id}")
  public ResponseEntity<@Valid Theme> updatePosting(@Valid @RequestBody Theme updatedPost) {
    return themeRepository.findById(updatedPost.getId()).map(response -> ResponseEntity.status(HttpStatus.OK).body(themeRepository.save(updatedPost)))
    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    /* 
    // FIXME Put here some method thats takes the old values and save 
    */ 
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePosting(@PathVariable Long id){
    Optional<Theme> selectedPosting = themeRepository.findById(id);
    if (selectedPosting.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    themeRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
