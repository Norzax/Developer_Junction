package com.example.day_3_source.model.mapper;

import java.util.List;

public interface Mapper<Entity, DTO> {
    List<DTO> mapListToDtoList(List<Entity> listData);
    DTO mapEntityToDto(Entity data);
    List<Entity> mapDtoListToList(List<DTO> listDtoData);
    Entity mapDtoToEntity(DTO dataDto);
}
