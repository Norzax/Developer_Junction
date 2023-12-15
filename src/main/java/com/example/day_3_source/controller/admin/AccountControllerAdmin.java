package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountControllerAdmin {
    private final AccountServiceImpl accountServiceImpl;

    @Autowired
    public AccountControllerAdmin(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping("/listAccount")
    public ResponseEntity<BaseResponseDto<List<AccountDto>>> getAll() {
        return ResponseEntity.ok(accountServiceImpl.getAll());
    }

    @GetMapping("/findAccount/{id}")
    public ResponseEntity<BaseResponseDto<AccountDto>> getById(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(accountServiceImpl.getById(accountId));
    }

    @PostMapping("/createAccount")
    public ResponseEntity<BaseResponseDto<AccountDto>> create(@RequestBody() AccountDto account) {
        return ResponseEntity.ok(accountServiceImpl.create(account));
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<BaseResponseDto<AccountDto>> update(@PathVariable("id") Long accountId, @RequestBody() AccountDto account) {
        return ResponseEntity.ok(accountServiceImpl.update(accountId, account));
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(accountServiceImpl.delete(userId));
    }
}