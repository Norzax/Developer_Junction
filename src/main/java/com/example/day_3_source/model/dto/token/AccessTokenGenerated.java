package com.example.day_3_source.model.dto.token;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenGenerated {
    private String accessToken;
    private Date expiredIn;
}
