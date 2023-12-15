package com.example.day_3_source.controller;

import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.dto.response.LoginResponseDto;
import com.example.day_3_source.services.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountServiceImpl accountServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto<AccountDto>> register(@RequestBody AccountDto account) {
        return ResponseEntity.ok(accountServiceImpl.create(account));
    }

    @PostMapping("/authentication")
    public ResponseEntity<BaseResponseDto<LoginResponseDto>> login(@RequestBody @Valid AccountDto account) {
        return ResponseEntity.ok(accountServiceImpl.login(account));
    }
}
