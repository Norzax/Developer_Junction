package com.example.day_3_source.services.impl;

import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.Category;
import com.example.day_3_source.model.mapper.Impl.CategoryMapper;
import com.example.day_3_source.repository.CategoryRepository;
import com.example.day_3_source.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CourseServiceImpl courseServiceImpl;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CourseServiceImpl courseServiceImpl){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.courseServiceImpl = courseServiceImpl;
    }

    @Override
    public BaseResponseDto<List<CategoryDto>> getAll() {
        BaseResponseDto<List<CategoryDto>> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(categoryMapper.mapListToDtoList(categoryRepository.findAll()));
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<CategoryDto> getById(Long id) {
        BaseResponseDto<CategoryDto> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(categoryMapper.mapEntityToDto(categoryRepository.findById(id).orElse(null)));
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<CategoryDto> create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.mapDtoToEntity(categoryDto));
        BaseResponseDto<CategoryDto> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setData(categoryMapper.mapEntityToDto(category));
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    public BaseResponseDto<CategoryDto> update(Long categoryId, CategoryDto updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        BaseResponseDto<CategoryDto> responseDto = new BaseResponseDto<>();
        if (optionalCategory.isPresent()) {
            CategoryDto existingCategory = categoryMapper.mapEntityToDto(optionalCategory.get());
            if (updatedCategory.getName() != null && !updatedCategory.getName().equals(existingCategory.getName())) {
                existingCategory.setName(updatedCategory.getName());
            }

            Category savedCategory = categoryRepository.save(categoryMapper.mapDtoToEntity(existingCategory));
            responseDto.setStatus("200");
            responseDto.setData(categoryMapper.mapEntityToDto(savedCategory));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("Category not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long categoryId) {
        Category categoryToDelete = categoryRepository.findById(categoryId).orElse(null);
        List<CourseDto> courseDto = courseServiceImpl.getCoursesByCategoryId(categoryId).getData();

        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        if (categoryToDelete != null) {
            if (courseDto.isEmpty()) {
                categoryRepository.delete(categoryToDelete);

                responseDto.setStatus("200");
                responseDto.setMessage("Category deleted successfully");
            } else {
                responseDto.setStatus("409");
                responseDto.setMessage("Cannot delete category as it has associated courses");
            }
        } else {
            responseDto.setStatus("404");
            responseDto.setMessage("Category not found");
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> deleteMultipleCategories(List<Long> categoryIds) {
        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();

        List<Category> categoriesToDelete = categoryRepository.findAllById(categoryIds);
        Map<Long, List<CourseDto>> associatedCourses = new HashMap<>();

        for (Long categoryId : categoryIds) {
            List<CourseDto> courseDto = courseServiceImpl.getCoursesByCategoryId(categoryId).getData();
            if (!courseDto.isEmpty()) {
                associatedCourses.put(categoryId, courseDto);
            }
        }

        if (categoriesToDelete.isEmpty()) {
            responseDto.setStatus("404");
            responseDto.setMessage("Categories not found");
        } else {
            for (Category categoryToDelete : categoriesToDelete) {
                Long categoryId = categoryToDelete.getCategoryId();
                if (associatedCourses.containsKey(categoryId)) {
                    responseDto.setStatus("409");
                    responseDto.setMessage("Cannot delete category with ID: " + categoryId + " as it has associated courses");
                    return responseDto;
                }
            }

            categoryRepository.deleteAll(categoriesToDelete);

            responseDto.setStatus("200");
            responseDto.setMessage("Categories deleted successfully");
        }

        return responseDto;
    }
}
