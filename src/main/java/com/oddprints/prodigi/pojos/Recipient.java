package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    private String name;
    private Address address;
    private String phoneNumber;
    private String email;

    public Recipient(String name, Address address) {
        this(name, address, null, null);
    }

    public Recipient(String name, Address address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        setPhoneNumber(phoneNumber);
        this.email = email;
    }

    private Recipient() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
