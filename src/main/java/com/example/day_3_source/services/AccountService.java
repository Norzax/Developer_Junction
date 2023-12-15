package com.example.day_3_source.services;

import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.dto.response.LoginResponseDto;
import com.example.day_3_source.services.impl.service.Service;

public interface AccountService extends Service<AccountDto> {
    BaseResponseDto<LoginResponseDto> login(AccountDto account);
    boolean isExistUsername(String username);
}
