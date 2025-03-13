package com.addressbook.addressbook.service;

import com.addressbook.addressbook.model.AddressBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService {

    private final List<AddressBook> addressBookList = new ArrayList<>();

    public List<AddressBook> getAllContacts() {
        return addressBookList;
    }

    public Optional<AddressBook> getContactById(int id) {
        return addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();
    }

    public AddressBook addContact(AddressBook contact) {
        addressBookList.add(contact);
        return contact;
    }

    public Optional<AddressBook> updateContact(int id, AddressBook updatedContact) {
        for (int i = 0; i < addressBookList.size(); i++) {
            if (addressBookList.get(i).getId() == id) {
                addressBookList.set(i, updatedContact);
                return Optional.of(updatedContact);
            }
        }
        return Optional.empty();
    }

    public boolean deleteContact(int id) {
        return addressBookList.removeIf(contact -> contact.getId() == id);
    }
}
