package com.example.day_3_source.services.impl;

import com.example.day_3_source.constant.AppConstant;
import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.RoleDto;
import com.example.day_3_source.model.dto.UserDto;
import com.example.day_3_source.model.dto.custom.CustomUserDetails;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.dto.response.LoginResponseDto;
import com.example.day_3_source.model.entity.Account;
import com.example.day_3_source.model.mapper.Impl.AccountMapper;
import com.example.day_3_source.model.mapper.Impl.RoleMapper;
import com.example.day_3_source.model.mapper.Impl.UserMapper;
import com.example.day_3_source.repository.AccountRepository;
import com.example.day_3_source.repository.RoleRepository;
import com.example.day_3_source.model.dto.token.AccessTokenGenerated;
import com.example.day_3_source.configurator.security.web.JwtTokenUtil;
import com.example.day_3_source.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final AccountCourseServiceImpl accountCourseServiceImpl;
    private final AccountMapper accountMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserServiceImpl userServiceImpl, RoleRepository roleRepository, RoleServiceImpl roleServiceImpl, AuthenticationManager authenticationManager, AccountCourseServiceImpl accountCourseServiceImpl, AccountMapper accountMapper, UserMapper userMapper, RoleMapper roleMapper, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.accountCourseServiceImpl = accountCourseServiceImpl;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public BaseResponseDto<List<AccountDto>> getAll() {
        BaseResponseDto<List<AccountDto>> responseDto = new BaseResponseDto<>();

        try {
            List<AccountDto> accounts = accountMapper.mapListToDtoList(accountRepository.findAll());
            for(AccountDto accountDto : accounts){
                List<AccountCourseDto> accountCourses = accountCourseServiceImpl.getAccountCoursesByAccountId(accountDto.getAccountId()).getData();
                accountDto.setAccountCourses(accountCourses);
            }

            responseDto.setStatus("200");
            responseDto.setData(accounts);
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountDto> getById(Long id) {
        BaseResponseDto<AccountDto> responseDto = new BaseResponseDto<>();

        try {
            AccountDto accountDto = accountMapper.mapEntityToDto(accountRepository.findById(id).orElse(null));
            List<AccountCourseDto> accountCourses = accountCourseServiceImpl.getAccountCoursesByAccountId(accountDto.getAccountId()).getData();
            accountDto.setAccountCourses(accountCourses);
            responseDto.setStatus("200");
            responseDto.setData(accountDto);
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountDto> create(AccountDto accountDto) {
        BaseResponseDto<AccountDto> responseDto = new BaseResponseDto<>();
        if(!isExistUsername(accountDto.getUsername())) {
            BaseResponseDto<UserDto> createdUserResponse = userServiceImpl.create(new UserDto());

            UserDto createdUser = createdUserResponse.getData();

            RoleDto defaultUserRole = roleMapper.mapEntityToDto(roleRepository.findById(1L).orElseThrow(() -> new ApplicationException("Default user role not found")));

            accountDto.setRole(defaultUserRole);

            String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
            accountDto.setPassword(encodedPassword);

            Date createdDate = new Date();
            accountDto.setCreatedDate(createdDate);

            accountDto.setStatus(true);

            accountDto.setUser(createdUser);

            Account savedAccount = accountRepository.save(accountMapper.mapDtoToEntity(accountDto));

            responseDto.setStatus("200");
            responseDto.setData(accountMapper.mapEntityToDto(savedAccount));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("409");
            responseDto.setData(null);
            responseDto.setMessage("Username is exist, try another username");
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountDto> update(Long accountId, AccountDto updatedAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        BaseResponseDto<AccountDto> responseDto = new BaseResponseDto<>();
        if (optionalAccount.isPresent()) {
            AccountDto existingAccount = accountMapper.mapEntityToDto(optionalAccount.get());
            if (updatedAccount.getPassword() != null && !updatedAccount.getPassword().equals(existingAccount.getPassword())) {
                existingAccount.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
            }
            if(updatedAccount.getUser() != null) {
                existingAccount.setUser(userMapper.mapEntityToDto(optionalAccount.get().getUser()));
                userServiceImpl.update(accountId, updatedAccount.getUser());
            }

            Account savedAccount = accountRepository.save(accountMapper.mapDtoToEntity(existingAccount));

            responseDto.setStatus("200");
            responseDto.setData(accountMapper.mapEntityToDto(savedAccount));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("User not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long accountId) {
        List<AccountCourseDto> accountCoursesDto = accountCourseServiceImpl.getAccountCoursesByAccountId(accountId).getData();

        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        if(accountCoursesDto.isEmpty()) {
            accountRepository.findById(accountId).ifPresent(accountRepository::delete);

            userServiceImpl.delete(getById(accountId).getData().getUser().getUserId());

            responseDto.setStatus("200");
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("409");
            responseDto.setMessage("This account was assign course(s), cannot delete");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<LoginResponseDto> login(AccountDto account) {
        Optional<Account> optionalUserAccount = accountRepository.findByUsername(account.getUsername());

        if (optionalUserAccount.isPresent()) {
            Account userAccount = optionalUserAccount.get();

            if (!userAccount.isStatus()) {
                throw new UsernameNotFoundException("Inactive account.");
            }

            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                AccessTokenGenerated accessTokenGenerated = jwtTokenUtil.generateToken(userDetails);

                return BaseResponseDto.<LoginResponseDto>builder()
                        .status(AppConstant.SUCCESS_STATUS)
                        .message(AppConstant.SUCCESS_MESSAGE)
                        .data(
                                LoginResponseDto.builder()
                                        .accessToken(accessTokenGenerated.getAccessToken())
                                        .expiredIn(accessTokenGenerated.getExpiredIn())
                                        .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                        .build()
                        )
                        .build();
            }
        } else {
            throw new UsernameNotFoundException("Invalid username.");
        }

        throw new UsernameNotFoundException("Authentication failed.");
    }

    @Override
    public boolean isExistUsername(String username) {
        Optional account = accountRepository.findByUsername(username);
        return account.isPresent() ? true : false;
    }
}
