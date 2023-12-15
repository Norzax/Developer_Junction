package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.entity.Category;
import com.example.day_3_source.model.entity.Course;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper implements Mapper<Course, CourseDto> {
    @Override
    public List<CourseDto> mapListToDtoList(List<Course> cours) {
        return cours.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto mapEntityToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        if(course.getCourseId() > 0) {
            courseDto.setCourseId(course.getCourseId());
        }
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription());
        courseDto.setStartDate(course.getStartDate());
        courseDto.setEndDate(course.getEndDate());
        courseDto.setPrice(course.getPrice());

        if (course.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(course.getCategory().getCategoryId());
            categoryDto.setName(course.getCategory().getName());
            courseDto.setCategory(categoryDto);
        }

        return courseDto;
    }

    @Override
    public List<Course> mapDtoListToList(List<CourseDto> coursesDto) {
        return coursesDto.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Course mapDtoToEntity(CourseDto courseDto) {
        Course course = new Course();
        if(courseDto.getCourseId() != null) {
            course.setCourseId(courseDto.getCourseId());
        }
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setPrice(courseDto.getPrice());

        if (courseDto.getCategory() != null) {
            Category category = new Category();
            category.setCategoryId(courseDto.getCategory().getCategoryId());
            category.setName(courseDto.getCategory().getName());
            course.setCategory(category);
        }
        return course;
    }
}
