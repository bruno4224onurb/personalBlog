package com.bruno2442onurb.personalBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bruno2442onurb.personalBlog.model.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
  List<Theme> findAllByDescriptionContainingIgnoreCase(@Param("description") String description);

}
