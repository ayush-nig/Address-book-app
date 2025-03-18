package com.addressbook.addressbook.config;

import com.addressbook.addressbook.dto.AddressBookDTO;
import com.addressbook.addressbook.service.AddressBookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AddressBookService addressBookService;

    public DataLoader(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load sample data only if database is empty
        if (addressBookService.getAllContacts().isEmpty()) {
            addressBookService.addContact(new AddressBookDTO("John Doe", "john@example.com", "1234567890"));
            addressBookService.addContact(new AddressBookDTO("Jane Smith", "jane@example.com", "0987654321"));
            addressBookService.addContact(new AddressBookDTO("Alice Brown", "alice@example.com", "1112223333"));
            System.out.println(" Sample data loaded into the database.");
        } else {
            System.out.println(" Database already contains data. Skipping data loading.");
        }
    }
}
