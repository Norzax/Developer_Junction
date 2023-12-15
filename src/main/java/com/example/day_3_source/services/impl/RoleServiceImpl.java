package com.example.day_3_source.services.impl;

import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.RoleDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.Role;
import com.example.day_3_source.model.mapper.Impl.RoleMapper;
import com.example.day_3_source.repository.RoleRepository;
import com.example.day_3_source.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public BaseResponseDto<List<RoleDto>> getAll() {
        BaseResponseDto<List<RoleDto>> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(roleMapper.mapListToDtoList(roleRepository.findAll()));
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<RoleDto> getById(Long id) {
        BaseResponseDto<RoleDto> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(roleMapper.mapEntityToDto(roleRepository.findById(id).orElse(null)));
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<RoleDto> create(RoleDto roleDto) {
        Role role = roleRepository.save(roleMapper.mapDtoToEntity(roleDto));
        BaseResponseDto<RoleDto> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setData(roleMapper.mapEntityToDto(role));
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    public BaseResponseDto<RoleDto> update(Long roleId, RoleDto updatedRole) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        BaseResponseDto<RoleDto> responseDto = new BaseResponseDto<>();
        if (optionalRole.isPresent()) {
            RoleDto existingRole = roleMapper.mapEntityToDto(optionalRole.get());
            if (updatedRole.getName() != null && !updatedRole.getName().equals(existingRole.getName())) {
                existingRole.setName(updatedRole.getName());
            }

            Role savedRole = roleRepository.save(roleMapper.mapDtoToEntity(existingRole));
            responseDto.setStatus("200");
            responseDto.setData(roleMapper.mapEntityToDto(savedRole));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("Category not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long roleId) {
        Role roleToDelete = roleRepository.findById(roleId).orElse(null);
        roleRepository.delete(roleToDelete);

        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setMessage("Success");
        return responseDto;
    }
}
