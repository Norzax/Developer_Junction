package com.example.day_3_source.services.impl;

import com.example.day_3_source.model.dto.custom.CustomUserDetails;
import com.example.day_3_source.model.entity.Account;
import com.example.day_3_source.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository userRepository;

    @Autowired
    public JwtUserDetailsServiceImpl(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User get username " + username + " does not exist!"));

        return new CustomUserDetails(user);
    }
}