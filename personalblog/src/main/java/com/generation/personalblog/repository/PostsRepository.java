package com.generation.personalblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.personalblog.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long>{
	
	

}
