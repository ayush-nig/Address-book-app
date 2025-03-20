package com.addressbook.addressbook.service;

import com.addressbook.addressbook.dto.AddressBookDTO;
import com.addressbook.addressbook.entity.AddressBookEntity;
import com.addressbook.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service  // ✅ Make sure this is present
public class AddressBookServiceImpl implements AddressBookService {

    private final AddressBookRepository repository;

    public AddressBookServiceImpl(AddressBookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "contacts")
    public List<AddressBookDTO> getAllContacts() {
        log.info("Fetching all contacts from DB");
        return repository.findAll().stream()
                .map(contact -> new AddressBookDTO(
                        contact.getId(),
                        contact.getName(),
                        contact.getEmail(),
                        contact.getPhoneNumber()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "contact", key = "#id")
    public AddressBookDTO getContactById(int id) {
        log.info("Fetching contact with ID {} from DB", id);
        return repository.findById(id)
                .map(contact -> new AddressBookDTO(
                        contact.getId(),
                        contact.getName(),
                        contact.getEmail(),
                        contact.getPhoneNumber()
                ))
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    @CachePut(value = "contact", key = "#result.id")
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookDTO addContact(AddressBookDTO contactDTO) {
        AddressBookEntity contact = repository.save(contactDTO.toEntity());
        log.info("Contact added with ID: {}", contact.getId());
        return new AddressBookDTO(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber()
        );
    }

    @Override
    @CachePut(value = "contact", key = "#id")
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookDTO updateContact(int id, AddressBookDTO contactDTO) {
        AddressBookEntity existingContact = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        existingContact.setName(contactDTO.getName());
        existingContact.setEmail(contactDTO.getEmail());
        existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
        repository.save(existingContact);

        log.info("Contact updated with ID: {}", id);
        return new AddressBookDTO(
                existingContact.getId(),
                existingContact.getName(),
                existingContact.getEmail(),
                existingContact.getPhoneNumber()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "contact", key = "#id"),
            @CacheEvict(value = "contacts", allEntries = true)
    })
    public void deleteContact(int id) {
        AddressBookEntity existingContact = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        repository.deleteById(id);
        log.info("Contact deleted with ID: {}", id);
    }
}
