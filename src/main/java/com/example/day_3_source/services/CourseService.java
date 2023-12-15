package com.example.day_3_source.services;

import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.service.Service;

import java.util.List;

public interface CourseService extends Service<CourseDto> {
    BaseResponseDto<List<CourseDto>> getCoursesByCategoryId(Long categoryId);
}
