package com.example.day_3_source.services.impl;

import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.UserDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.User;
import com.example.day_3_source.model.mapper.Impl.UserMapper;
import com.example.day_3_source.repository.UserRepository;
import com.example.day_3_source.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public BaseResponseDto<List<UserDto>> getAll() {
        BaseResponseDto<List<UserDto>> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(userMapper.mapListToDtoList(userRepository.findAll()));
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<UserDto> getById(Long id) {
        BaseResponseDto<UserDto> responseDto = new BaseResponseDto<>();

        User user = userRepository.findById(id).orElse(null);

        try {
            responseDto.setStatus("200");
            if(user != null) {
                responseDto.setData(userMapper.mapEntityToDto(user));
            }
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<UserDto> create(UserDto userDto) {
        BaseResponseDto<UserDto> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setData(userMapper.mapEntityToDto(userRepository.save(userMapper.mapDtoToEntity(userDto))));
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    public BaseResponseDto<UserDto> update(Long userId, UserDto updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);

        BaseResponseDto<UserDto> responseDto = new BaseResponseDto<>();
        if (optionalUser.isPresent()) {
            UserDto existingUser = userMapper.mapEntityToDto(optionalUser.get());

            if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().equals(existingUser.getFirstName())) {
                existingUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName()  != null && !updatedUser.getLastName().equals(existingUser.getLastName())) {
                existingUser.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getEmail()  != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            User savedUser = userRepository.save(userMapper.mapDtoToEntity(existingUser));
            responseDto.setStatus("200");
            responseDto.setData(userMapper.mapEntityToDto(savedUser));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("Category not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long userId) {
        User userToDelete = userRepository.findById(userId).orElse(null);
        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        if(userToDelete != null) {
            userRepository.delete(userToDelete);
            responseDto.setStatus("200");
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setMessage("User not found");
        }
        return responseDto;
    }
}
