package com.example.contactcrud.postgresql.controller;

import com.example.contactcrud.postgresql.dto.ContactCreateDto;
import com.example.contactcrud.postgresql.dto.ContactFilter;
import com.example.contactcrud.postgresql.dto.ContactReadDto;
import com.example.contactcrud.postgresql.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController("postgreSqlContactController")
@RequestMapping("/contacts/postgresql")
public class ContactController {

    @Autowired
    @Qualifier("postgreSqlContactService")
    private ContactService contactService;

    @GetMapping
    public List<ContactReadDto> findAll(ContactFilter filter){
        return contactService.findAll(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactReadDto> findById(@PathVariable("id") Long id){
        ContactReadDto contactReadDto = contactService.findById(id);
        return ResponseEntity.ok(contactReadDto);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ContactReadDto> findByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){
        ContactReadDto contactReadDto = contactService.findByNumber(phoneNumber);
        return ResponseEntity.ok(contactReadDto);
    }

    @PostMapping
    public ResponseEntity<ContactReadDto> create(@RequestBody ContactCreateDto contactCreateDto){
        ContactReadDto contactReadDto = contactService.create(contactCreateDto);
        return new ResponseEntity<>(contactReadDto, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactReadDto> updateById(@PathVariable("id") Long id,
                                                     @RequestBody ContactCreateDto contactCreateDto){
        ContactReadDto contactReadDto = contactService.updateById(id, contactCreateDto);
        return ResponseEntity.ok(contactReadDto);
    }

    @PutMapping("/phone/{phoneNumber}")
    public ResponseEntity<ContactReadDto> updateByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber,
                                                              @RequestBody ContactCreateDto contactCreateDto){
        ContactReadDto contactReadDto = contactService.updateByNumber(phoneNumber, contactCreateDto);
        return ResponseEntity.ok(contactReadDto);
    }


}
