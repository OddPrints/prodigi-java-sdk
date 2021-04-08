package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String line1;
    private String line2;
    private String postalOrZipCode;
    private CountryCode countryCode;
    private String townOrCity;

    public Address(String line1, String line2, String postalOrZipCode, CountryCode countryCode, String townOrCity) {
        this.line1 = line1;
        this.line2 = line2;
        this.postalOrZipCode = postalOrZipCode;
        this.countryCode = countryCode;
        this.townOrCity = townOrCity;
    }

    private Address() {
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostalOrZipCode() {
        return postalOrZipCode;
    }

    public void setPostalOrZipCode(String postalOrZipCode) {
        this.postalOrZipCode = postalOrZipCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public void setTownOrCity(String townOrCity) {
        this.townOrCity = townOrCity;
    }
}
