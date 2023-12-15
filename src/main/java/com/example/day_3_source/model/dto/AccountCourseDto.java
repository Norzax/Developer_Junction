package com.example.day_3_source.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountCourseDto {
    private Long accountCourseId;

    private int purchasePrice;
    private Date regstrationDate;
    private Long accountId;
    private Long courseId;
}
