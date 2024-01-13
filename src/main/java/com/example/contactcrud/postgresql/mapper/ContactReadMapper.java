package com.example.contactcrud.postgresql.mapper;

import com.example.contactcrud.postgresql.dto.ContactReadDto;
import com.example.contactcrud.postgresql.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactReadMapper {

    ContactReadMapper INSTANCE = Mappers.getMapper(ContactReadMapper.class);
    ContactReadDto toDto(Contact entity);
}
