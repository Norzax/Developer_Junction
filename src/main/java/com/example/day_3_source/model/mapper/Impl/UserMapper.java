package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.UserDto;
import com.example.day_3_source.model.entity.User;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public List<UserDto> mapListToDtoList(List<User> userEntities) {
        return userEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto mapEntityToDto(User user) {
        UserDto userDto = new UserDto();
        if (user.getUserId() > 0) {
            userDto.setUserId(user.getUserId());
        }
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    @Override
    public List<User> mapDtoListToList(List<UserDto> usersDto) {
        return usersDto.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public User mapDtoToEntity(UserDto userDto) {
        User user = new User();
        if(userDto.getUserId()!= null) {
            user.setUserId(userDto.getUserId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
