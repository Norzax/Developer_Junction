package com.example.day_3_source.services;

import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.AccountCourse;
import com.example.day_3_source.services.impl.service.Service;

import java.util.List;

public interface AccountCourseService extends Service<AccountCourseDto> {
    BaseResponseDto<List<AccountCourseDto>> getAccountCoursesByAccountId (Long accountId);
    BaseResponseDto<List<AccountCourseDto>> getAccountCoursesByCourseId (Long courseId);
    boolean isAssigned(Long courseId, Long accountId);
    BaseResponseDto<Void> deleteAccountCourseByUser(Long accountId, Long courseId);
    BaseResponseDto<AccountCourseDto> assignByUser(Long accountId, Long courseId);
}
