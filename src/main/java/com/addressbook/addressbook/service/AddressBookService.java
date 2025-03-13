package com.addressbook.addressbook.service;

import com.addressbook.addressbook.dto.AddressBookDTO;

import java.util.List;

public interface AddressBookService {

    List<AddressBookDTO> getAllContacts();

    AddressBookDTO getContactById(int id);

    AddressBookDTO addContact(AddressBookDTO contactDTO);

    AddressBookDTO updateContact(int id, AddressBookDTO contactDTO);

    void deleteContact(int id);
}
