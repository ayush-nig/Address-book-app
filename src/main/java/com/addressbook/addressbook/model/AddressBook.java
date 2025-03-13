package com.addressbook.addressbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
}
