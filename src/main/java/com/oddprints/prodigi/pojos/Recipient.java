package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    private String name;
    private Address address;

    public Recipient(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    private Recipient() {
    }

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
}
