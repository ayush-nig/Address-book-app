package com.addressbook.addressbook.controller;

import com.addressbook.addressbook.dto.AddressBookDTO;
import com.addressbook.addressbook.service.AddressBookService;
import com.addressbook.addressbook.service.RabbitMQSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "AddressBookController", description = "API for Address Book Operations")
@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private final AddressBookService service;
    private final RabbitMQSender rabbitMQSender;

    public AddressBookController(AddressBookService service, RabbitMQSender rabbitMQSender) {
        this.service = service;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Operation(summary = "Get all contacts")
    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    @Operation(summary = "Get contact by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable int id) {
        return ResponseEntity.ok(service.getContactById(id));
    }

    @Operation(summary = "Add a new contact")
    @PostMapping
    public ResponseEntity<AddressBookDTO> addContact(@Valid @RequestBody AddressBookDTO contactDTO) {
        AddressBookDTO savedContact = service.addContact(contactDTO);
        rabbitMQSender.sendMessage("New contact added: " + savedContact.getName());
        return ResponseEntity.ok(savedContact);
    }

    @Operation(summary = "Update a contact by ID")
    @PutMapping("/{id}")
    public ResponseEntity<AddressBookDTO> updateContact(
            @PathVariable int id,
            @Valid @RequestBody AddressBookDTO contactDTO) {
        AddressBookDTO updatedContact = service.updateContact(id, contactDTO);
        rabbitMQSender.sendMessage("Contact updated: " + updatedContact.getName());
        return ResponseEntity.ok(updatedContact);
    }

    @Operation(summary = "Delete a contact by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        service.deleteContact(id);
        rabbitMQSender.sendMessage("Contact deleted with ID: " + id);
        return ResponseEntity.noContent().build();
    }
}
