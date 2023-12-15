package com.example.day_3_source.repository;

import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.entity.Category;
import com.example.day_3_source.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);
    List<Course> findCoursesByCategory_CategoryId(Long categoryId);
}
