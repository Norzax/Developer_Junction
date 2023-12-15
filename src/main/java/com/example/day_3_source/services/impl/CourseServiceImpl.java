package com.example.day_3_source.services.impl;

import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.Category;
import com.example.day_3_source.model.entity.Course;
import com.example.day_3_source.model.mapper.Impl.CategoryMapper;
import com.example.day_3_source.model.mapper.Impl.CourseMapper;
import com.example.day_3_source.repository.CategoryRepository;
import com.example.day_3_source.repository.CourseRepository;
import com.example.day_3_source.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private  final AccountCourseServiceImpl accountCourseServiceImpl;
    private final CourseMapper courseMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository, AccountCourseServiceImpl accountCourseServiceImpl, CourseMapper courseMapper, CategoryMapper categoryMapper) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.accountCourseServiceImpl = accountCourseServiceImpl;
        this.courseMapper = courseMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public BaseResponseDto<List<CourseDto>> getAll() {
        BaseResponseDto<List<CourseDto>> responseDto = new BaseResponseDto<>();

        try {
            List<CourseDto> courses = courseMapper.mapListToDtoList(courseRepository.findAll());
            for(CourseDto courseDto : courses){
                List<AccountCourseDto> accountCourses = accountCourseServiceImpl.getAccountCoursesByCourseId(courseDto.getCourseId()).getData();
                courseDto.setAccountCourses(accountCourses);
            }

            responseDto.setStatus("200");
            responseDto.setData(courses);
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<CourseDto> getById(Long id) {
        BaseResponseDto<CourseDto> responseDto = new BaseResponseDto<>();

        try {
            CourseDto courseDto = courseMapper.mapEntityToDto(courseRepository.findById(id).orElse(null));
            List<AccountCourseDto> accountCourses = accountCourseServiceImpl.getAccountCoursesByCourseId(courseDto.getCourseId()).getData();
            courseDto.setAccountCourses(accountCourses);
            responseDto.setStatus("200");
            responseDto.setData(courseDto);
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<CourseDto> create(CourseDto courseDto) {
        CategoryDto categoryDto = courseDto.getCategory();
        if (categoryDto.getCategoryId() != null) {
            Optional<Category> existingCategory = categoryRepository.findById(categoryDto.getCategoryId());
            if (existingCategory.isPresent()) {
                courseDto.setCategory(categoryMapper.mapEntityToDto(existingCategory.get()));
            } else {
                courseDto.setCategory(null);
            }
        }

        courseRepository.save(courseMapper.mapDtoToEntity(courseDto));

        BaseResponseDto<CourseDto> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setData(courseDto);
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    public BaseResponseDto<CourseDto> update(Long courseId, CourseDto updatedCourse) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        BaseResponseDto<CourseDto> responseDto = new BaseResponseDto<>();
        if (optionalCourse.isPresent()) {
            CourseDto existingCourse = courseMapper.mapEntityToDto(optionalCourse.get());

            if (updatedCourse.getName() != null && !updatedCourse.getName().equals(existingCourse.getName())) {
                existingCourse.setName(updatedCourse.getName());
            }
            if (updatedCourse.getDescription() != null && !updatedCourse.getDescription().equals(existingCourse.getDescription())) {
                existingCourse.setDescription(updatedCourse.getDescription());
            }
            if (updatedCourse.getStartDate() != null && !updatedCourse.getStartDate().equals(existingCourse.getStartDate())) {
                existingCourse.setStartDate(updatedCourse.getStartDate());
            }
            if (updatedCourse.getEndDate() != null && !updatedCourse.getEndDate().equals(existingCourse.getEndDate())) {
                existingCourse.setEndDate(updatedCourse.getEndDate());
            }
            if (updatedCourse.getPrice() != existingCourse.getPrice()) {
                existingCourse.setPrice(updatedCourse.getPrice());
            }

            Course savedCourse = courseRepository.save(courseMapper.mapDtoToEntity(existingCourse));
            responseDto.setStatus("200");
            responseDto.setData(courseMapper.mapEntityToDto(savedCourse));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("Course not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long courseId) {
        Course courseToDelete = courseRepository.findById(courseId).orElse(null);
        courseRepository.delete(courseToDelete);

        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    public BaseResponseDto<List<CourseDto>> getCoursesByCategoryId(Long categoryId) {
        BaseResponseDto<List<CourseDto>> responseDto = new BaseResponseDto<>();

        try {
            List<CourseDto> courses = courseMapper.mapListToDtoList(courseRepository.findCoursesByCategory_CategoryId(categoryId));
            responseDto.setStatus("200");
            responseDto.setData(courses);
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }
}
