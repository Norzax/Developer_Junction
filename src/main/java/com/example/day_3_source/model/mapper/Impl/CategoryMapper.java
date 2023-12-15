package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.entity.Category;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    @Override
    public List<CategoryDto> mapListToDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto mapEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        if(category.getCategoryId() > 0 ) {
            categoryDto.setCategoryId(category.getCategoryId());
        }
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    @Override
    public List<Category> mapDtoListToList(List<CategoryDto> categoriesDto) {
        return categoriesDto.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Category mapDtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        if(categoryDto.getCategoryId() != null) {
            category.setCategoryId(categoryDto.getCategoryId());
        }
        category.setName(categoryDto.getName());

        return category;
    }
}
