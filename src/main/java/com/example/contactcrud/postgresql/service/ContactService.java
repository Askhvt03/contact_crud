package com.example.contactcrud.postgresql.service;

import com.example.contactcrud.postgresql.dto.ContactCreateDto;
import com.example.contactcrud.postgresql.dto.ContactFilter;
import com.example.contactcrud.postgresql.dto.ContactReadDto;

import java.util.List;

public interface ContactService {
    List<ContactReadDto> findAll(ContactFilter filter);
    ContactReadDto findById(Long id);
    ContactReadDto findByNumber(String phoneNumber);

    ContactReadDto create(ContactCreateDto contact);
    ContactReadDto updateById(Long id, ContactCreateDto contact);
    ContactReadDto updateByNumber(String phoneNumber, ContactCreateDto contact);

    void deleteById(Long id);
    void deleteByPhoneNumber(String phoneNumber);
}
