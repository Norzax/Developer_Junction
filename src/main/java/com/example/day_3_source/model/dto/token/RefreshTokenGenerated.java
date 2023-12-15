package com.example.day_3_source.model.dto.token;

import com.example.day_3_source.model.entity.Account;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenGenerated {
    private String refreshToken;
    private Date expiredIn;
    private Account account;
}
