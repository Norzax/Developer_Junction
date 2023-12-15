package com.example.day_3_source.services.impl.service;

import com.example.day_3_source.model.dto.response.BaseResponseDto;

import java.util.List;

public interface Service<T> {
    BaseResponseDto<List<T>> getAll();
    BaseResponseDto<T> getById(Long id);
    BaseResponseDto<T> create(T data);
    BaseResponseDto<T> update(Long Id, T updatedData);
    BaseResponseDto<Void> delete(Long accountId);
}
