package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.UserDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/user"))
public class UserControllerAdmin {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserControllerAdmin(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/listUser")
    public ResponseEntity<BaseResponseDto<List<UserDto>>> getAll() {
        return ResponseEntity.ok(userServiceImpl.getAll());
    }

    @GetMapping("/findUser/{id}")
    public ResponseEntity<BaseResponseDto<UserDto>> getById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userServiceImpl.getById(userId));
    }

    @PostMapping("/createUser")
    public ResponseEntity<BaseResponseDto<UserDto>> create(@RequestBody() UserDto user) {
        return ResponseEntity.ok(userServiceImpl.create(user));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<BaseResponseDto<UserDto>> update(@PathVariable("id") Long userId, @RequestBody() UserDto user) {
        return ResponseEntity.ok(userServiceImpl.update(userId, user));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userServiceImpl.delete(userId));
    }
}
