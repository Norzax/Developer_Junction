package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.AccountCourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accountCourse")
public class AccountCourseControllerAdmin {
    private final AccountCourseServiceImpl accountCourseServiceImpl;

    @Autowired
    public AccountCourseControllerAdmin(AccountCourseServiceImpl accountCourseServiceImpl) {
        this.accountCourseServiceImpl = accountCourseServiceImpl;
    }

    @GetMapping("/listAccountCourse")
    public ResponseEntity<BaseResponseDto<List<AccountCourseDto>>> getAll() {
        return ResponseEntity.ok(accountCourseServiceImpl.getAll());
    }

    @GetMapping("/findAccountCourse/{id}")
    public ResponseEntity<BaseResponseDto<AccountCourseDto>> getById(@PathVariable("id") Long accountCourseId) {
        return ResponseEntity.ok(accountCourseServiceImpl.getById(accountCourseId));
    }

    @PostMapping("/createAccountCourse")
    public ResponseEntity<BaseResponseDto<AccountCourseDto>> create(@RequestBody() AccountCourseDto accountCourse) {
        return ResponseEntity.ok(accountCourseServiceImpl.create(accountCourse));
    }

    @PutMapping("/updateAccountCourse/{id}")
    public ResponseEntity<BaseResponseDto<AccountCourseDto>> update(@PathVariable("id") Long accountCourseId, @RequestBody() AccountCourseDto accountCourse) {
        return ResponseEntity.ok(accountCourseServiceImpl.update(accountCourseId, accountCourse));
    }

    @DeleteMapping("/deleteAccountCourse/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long accountCourseId) {
        return ResponseEntity.ok(accountCourseServiceImpl.delete(accountCourseId));
    }
}
