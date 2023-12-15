package com.example.day_3_source.controller.user;

import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.custom.CustomUserDetails;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/user"))
public class AccountControllerUser {

    private final AccountServiceImpl accountServiceImpl;
    public AccountControllerUser(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @PutMapping("/updateAccountInfo")
    public ResponseEntity<BaseResponseDto<AccountDto>> updateInfo (@RequestBody() AccountDto accountDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        return ResponseEntity.ok(accountServiceImpl.update(loggedInUserId, accountDto));
    }
}
