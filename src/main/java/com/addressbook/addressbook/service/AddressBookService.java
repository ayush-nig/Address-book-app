package com.addressbook.addressbook.service;

import com.addressbook.addressbook.dto.AddressBookDTO;
import com.addressbook.addressbook.entity.AddressBookEntity;
import com.addressbook.addressbook.exception.EntityNotFoundException;
import com.addressbook.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService {

    private final AddressBookRepository repository;

    public AddressBookService(AddressBookRepository repository) {
        this.repository = repository;
    }

    public List<AddressBookDTO> getAllContacts() {
        log.debug("Fetching all contacts from database");
        return repository.findAll().stream()
                .map(contact -> new AddressBookDTO(contact.getName(), contact.getEmail(), contact.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public AddressBookDTO getContactById(int id) {
        log.debug("Fetching contact with ID: {}", id);
        AddressBookEntity contact = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact with ID " + id + " not found"));
        return new AddressBookDTO(contact.getName(), contact.getEmail(), contact.getPhoneNumber());
    }

    public AddressBookDTO addContact(AddressBookDTO contactDTO) {
        log.debug("Adding new contact: {}", contactDTO);
        AddressBookEntity contact = new AddressBookEntity(contactDTO);
        repository.save(contact);
        return contactDTO;
    }

    public AddressBookDTO updateContact(int id, AddressBookDTO contactDTO) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Contact with ID " + id + " not found for update");
        }
        AddressBookEntity updatedContact = new AddressBookEntity(contactDTO);
        updatedContact.setId(id);
        repository.save(updatedContact);
        log.debug("Updating contact with ID: {}", id);
        return contactDTO;
    }

    public void deleteContact(int id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Contact with ID " + id + " not found for deletion");
        }
        repository.deleteById(id);
        log.debug("Deleted contact with ID: {}", id);
    }
}
