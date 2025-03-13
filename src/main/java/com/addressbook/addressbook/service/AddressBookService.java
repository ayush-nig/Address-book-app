package com.addressbook.addressbook.service;

import com.addressbook.addressbook.model.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressBookService {

    private final List<AddressBook> addressBookList = new ArrayList<>();

    public List<AddressBook> getAllContacts() {
        log.info("Fetching all contacts");
        return addressBookList;
    }

    public Optional<AddressBook> getContactById(int id) {
        log.info("Fetching contact with ID: {}", id);
        return addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();
    }

    public AddressBook addContact(AddressBook contact) {
        log.info("Adding new contact: {}", contact);
        addressBookList.add(contact);
        return contact;
    }

    public Optional<AddressBook> updateContact(int id, AddressBook updatedContact) {
        log.info("Updating contact with ID: {}", id);
        for (int i = 0; i < addressBookList.size(); i++) {
            if (addressBookList.get(i).getId() == id) {
                addressBookList.set(i, updatedContact);
                log.info("Updated contact: {}", updatedContact);
                return Optional.of(updatedContact);
            }
        }
        log.warn("Contact with ID {} not found for update", id);
        return Optional.empty();
    }

    public boolean deleteContact(int id) {
        log.info("Deleting contact with ID: {}", id);
        boolean removed = addressBookList.removeIf(contact -> contact.getId() == id);
        if (removed) {
            log.info("Contact deleted successfully");
        } else {
            log.warn("Contact with ID {} not found for deletion", id);
        }
        return removed;
    }
}
