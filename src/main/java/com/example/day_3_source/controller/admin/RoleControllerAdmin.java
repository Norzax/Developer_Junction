package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.RoleDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/role"))
public class RoleControllerAdmin {
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public RoleControllerAdmin(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping("/listRole")
    public ResponseEntity<BaseResponseDto<List<RoleDto>>> getAll() {
        return ResponseEntity.ok(roleServiceImpl.getAll());
    }

    @GetMapping("/findRole/{id}")
    public ResponseEntity<BaseResponseDto<RoleDto>> getById(@PathVariable("id") Long roleId) {
        return ResponseEntity.ok(roleServiceImpl.getById(roleId));
    }

    @PostMapping("/createRole")
    public ResponseEntity<BaseResponseDto<RoleDto>> create(@RequestBody() RoleDto role) {
        return ResponseEntity.ok(roleServiceImpl.create(role));
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<BaseResponseDto<RoleDto>> update(@PathVariable("id") Long roleId, @RequestBody() RoleDto role) {
        return ResponseEntity.ok(roleServiceImpl.update(roleId, role));
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long roleId) {
        return ResponseEntity.ok(roleServiceImpl.delete(roleId));
    }
}
