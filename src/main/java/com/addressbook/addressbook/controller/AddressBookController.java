package com.addressbook.addressbook.controller;

import com.addressbook.addressbook.dto.AddressBookDTO;
import com.addressbook.addressbook.service.AddressBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private final AddressBookService service;

    public AddressBookController(AddressBookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable int id) {
        return ResponseEntity.ok(service.getContactById(id));
    }

    @PostMapping
    public ResponseEntity<AddressBookDTO> addContact(@Valid @RequestBody AddressBookDTO contactDTO) {
        AddressBookDTO savedContact = service.addContact(contactDTO);
        return ResponseEntity.ok(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookDTO> updateContact(
            @PathVariable int id,
            @Valid @RequestBody AddressBookDTO contactDTO) {
        return ResponseEntity.ok(service.updateContact(id, contactDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
