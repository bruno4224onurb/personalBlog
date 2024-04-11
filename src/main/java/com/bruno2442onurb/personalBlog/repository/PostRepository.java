package com.bruno2442onurb.personalBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bruno2442onurb.personalBlog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
  
  List<Post> findAllByTitleContainingIgnoreCase(@Param("title") String title);
  
}