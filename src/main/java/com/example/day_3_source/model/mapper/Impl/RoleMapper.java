package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.RoleDto;
import com.example.day_3_source.model.entity.Role;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper implements Mapper<Role, RoleDto> {
    @Override
    public List<RoleDto> mapListToDtoList(List<Role> roles) {
        return roles.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto mapEntityToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        if(role.getRoleId() > 0) {
            roleDto.setRoleId(role.getRoleId());
        }
        roleDto.setName(role.getName());

        return roleDto;
    }

    @Override
    public List<Role> mapDtoListToList(List<RoleDto> rolesDto) {
        return rolesDto.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Role mapDtoToEntity(RoleDto roleDto) {
        Role role = new Role();
        if(roleDto.getRoleId() != null) {
            role.setRoleId(roleDto.getRoleId());
        }
        role.setName(roleDto.getName());

        return role;
    }
}
