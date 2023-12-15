package com.example.day_3_source.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class AccountDto {
    private Long accountId;
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private boolean status;
    private Date createdDate;

    private UserDto user;
    private RoleDto role;
    private List<AccountCourseDto> accountCourses;
}
