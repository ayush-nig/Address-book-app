package com.addressbook.addressbook.service;

import com.addressbook.addressbook.entity.AddressBookEntity;
import com.addressbook.addressbook.exception.EntityNotFoundException;
import com.addressbook.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressBookService {

    private final AddressBookRepository repository;

    public AddressBookService(AddressBookRepository repository) {
        this.repository = repository;
    }

    public List<AddressBookEntity> getAllContacts() {
        log.debug("Fetching all contacts from database");
        return repository.findAll();
    }

    public AddressBookEntity getContactById(int id) {
        log.debug("Fetching contact with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact with ID " + id + " not found"));
    }

    public AddressBookEntity addContact(AddressBookEntity contact) {
        log.debug("Adding new contact to database: {}", contact);
        return repository.save(contact);
    }

    public AddressBookEntity updateContact(int id, AddressBookEntity updatedContact) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Contact with ID " + id + " not found for update");
        }
        updatedContact.setId(id);
        log.debug("Updating contact with ID: {}", id);
        return repository.save(updatedContact);
    }

    public void deleteContact(int id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Contact with ID " + id + " not found for deletion");
        }
        repository.deleteById(id);
        log.debug("Deleted contact with ID: {}", id);
    }
}
