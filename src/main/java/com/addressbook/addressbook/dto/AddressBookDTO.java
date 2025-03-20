package com.addressbook.addressbook.dto;

import com.addressbook.addressbook.entity.AddressBookEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressBookDTO implements Serializable {  // ✅ Implement Serializable

    private static final long serialVersionUID = 1L;  // ✅ Add serial version UID

    private int id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    public AddressBookDTO(int id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public AddressBookDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public AddressBookEntity toEntity() {
        AddressBookEntity contact = new AddressBookEntity();
        contact.setId(this.id);
        contact.setName(this.name);
        contact.setEmail(this.email);
        contact.setPhoneNumber(this.phoneNumber);
        return contact;
    }
}