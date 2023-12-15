package com.example.day_3_source.configurator.security.web;

import com.example.day_3_source.constant.RoleConstant;
import com.example.day_3_source.services.impl.JwtUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] WHITE_LIST = {
            "/access-denied",
            "/user/home",
            "/uploads/**",
            "/assets/**",
            "/api/v1/auth/**",
            "/"
    };
    private final JwtRequestFilter jwtRequestFilter;

    private final JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtUserDetailsServiceImpl)
//                .passwordEncoder(PasswordSecurityConfig.bCryptPasswordEncoder());
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(WHITE_LIST).permitAll()
            .antMatchers("/api/**").hasAuthority(RoleConstant.ADMIN.name())
            .antMatchers("/courses/**").hasAnyAuthority(RoleConstant.ADMIN.name(), RoleConstant.USER.name())
            .antMatchers("/user/**").hasAnyAuthority(RoleConstant.ADMIN.name(), RoleConstant.USER.name())
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed"));
    }
}

