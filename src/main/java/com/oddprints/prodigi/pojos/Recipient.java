package com.oddprints.prodigi.pojos;

import static org.apache.logging.log4j.util.Strings.trimToNull;

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
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    private Recipient() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = trimToNull(name);
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
        this.phoneNumber = trimToNull(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = trimToNull(email);
    }
}
