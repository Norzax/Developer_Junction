package com.example.day_3_source.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseDto {
    private Long courseId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int price;

    private CategoryDto category;
    private List<AccountCourseDto> accountCourses;
}
