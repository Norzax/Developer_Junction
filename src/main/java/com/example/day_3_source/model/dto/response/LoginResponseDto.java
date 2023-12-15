package com.example.day_3_source.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginResponseDto {
    private String refreshToken;
    private String accessToken;
    private Date expiredIn;
    private List<String> roles;
}
