package com.example.day_3_source.services;

import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.service.Service;

import java.util.List;

public interface CategoryService extends Service<CategoryDto> {
    BaseResponseDto<Void> deleteMultipleCategories(List<Long> categoryIds);
}
