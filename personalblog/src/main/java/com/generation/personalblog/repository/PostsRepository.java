package com.generation.personalblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.personalblog.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long>{
	
	public List <Posts> findAllByTitleContainingIgnoreCase(@Param("title") String title);
	
	

}
