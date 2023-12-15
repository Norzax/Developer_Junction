package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.RoleDto;
import com.example.day_3_source.model.dto.UserDto;
import com.example.day_3_source.model.entity.Account;
import com.example.day_3_source.model.entity.Role;
import com.example.day_3_source.model.entity.User;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper implements Mapper<Account, AccountDto> {

    @Override
    public List<AccountDto> mapListToDtoList(List<Account> accountEntities) {
        return accountEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto mapEntityToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        if(account.getAccountId() > 0) {
            accountDto.setAccountId(account.getAccountId());
        }
        accountDto.setUsername(account.getUsername());
        accountDto.setPassword(account.getPassword());
        accountDto.setStatus(account.isStatus());
        accountDto.setCreatedDate(account.getCreatedDate());

        if(account.getUser() != null) {
            UserDto userDto = new UserDto();

            userDto.setUserId(account.getUser().getUserId());
            userDto.setFirstName(account.getUser().getFirstName());
            userDto.setLastName(account.getUser().getLastName());
            userDto.setEmail(account.getUser().getEmail());

            accountDto.setUser(userDto);
        }

        if(account.getRole() != null) {
            RoleDto roleDto = new RoleDto();
            roleDto.setRoleId(account.getRole().getRoleId());
            roleDto.setName(account.getRole().getName());

            accountDto.setRole(roleDto);
        }

        return accountDto;
    }

    @Override
    public List<Account> mapDtoListToList(List<AccountDto> accountDtos) {
        return accountDtos.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Account mapDtoToEntity(AccountDto accountDto) {
        Account account = new Account();
        if(accountDto.getAccountId() != null) {
            account.setAccountId(accountDto.getAccountId());
        }
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setStatus(accountDto.isStatus());
        account.setCreatedDate(accountDto.getCreatedDate());

        if(accountDto.getUser() != null) {
            User user = new User();

            user.setUserId(accountDto.getUser().getUserId());
            user.setFirstName(accountDto.getUser().getFirstName());
            user.setLastName(accountDto.getUser().getLastName());
            user.setEmail(accountDto.getUser().getEmail());

            account.setUser(user);
        }

        if(accountDto.getRole()  != null) {
            Role role = new Role();
            role.setRoleId(accountDto.getRole().getRoleId());
            role.setName(accountDto.getRole().getName());

            account.setRole(role);
        }

        return account;
    }
}
