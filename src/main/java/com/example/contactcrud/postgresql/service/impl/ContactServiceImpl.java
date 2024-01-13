package com.example.contactcrud.postgresql.service.impl;

import com.example.contactcrud.postgresql.dto.ContactCreateDto;
import com.example.contactcrud.postgresql.dto.ContactFilter;
import com.example.contactcrud.postgresql.dto.ContactReadDto;
import com.example.contactcrud.postgresql.entity.Contact;
import com.example.contactcrud.postgresql.mapper.ContactCreateMapper;
import com.example.contactcrud.postgresql.mapper.ContactReadMapper;
import com.example.contactcrud.postgresql.repository.ContactRepository;
import com.example.contactcrud.postgresql.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service("postgreSqlContactService")
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    @Autowired
    @Qualifier("postgreSqlContactRepository")
    private ContactRepository contactRepository;

    @Override
    public List<ContactReadDto> findAll(ContactFilter filter) {
        return contactRepository.findAllByLimitAndOffset(filter.limit(), filter.offset()).stream()
                .map(ContactReadMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public ContactReadDto findById(Long id) {
        return contactRepository.findById(id)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto findByNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto create(ContactCreateDto contact) {
        return Optional.of(contact)
                .map(ContactCreateMapper.INSTANCE::toEntity)
                .map(contactRepository::save)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    @Override
    public ContactReadDto updateById(Long id, ContactCreateDto contact) {
        return contactRepository.findById(id)
                .map(entity -> copy(contact, entity))
                .map(contactRepository::saveAndFlush)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto updateByNumber(String phoneNumber, ContactCreateDto contact) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {

    }

    private Contact copy(ContactCreateDto fromObject, Contact toObject) {
        toObject.setName(fromObject.getName());
        toObject.setBirthDate(LocalDate.parse(fromObject.getBirthDate()));
        toObject.setPhoneNumber(fromObject.getPhoneNumber());
        toObject.setSecondPhoneNumber(fromObject.getSecondPhoneNumber());

        return toObject;
    }
}
