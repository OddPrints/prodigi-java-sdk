package com.oddprints.prodigi.pojos;

import static org.apache.logging.log4j.util.Strings.trimToNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String line1;
    private String line2;
    private String postalOrZipCode;
    private String countryCode;
    private String townOrCity;
    private String stateOrCounty;

    public Address(
            String line1,
            String line2,
            String postalOrZipCode,
            CountryCode countryCode,
            String townOrCity,
            String stateOrCounty) {
        setLine1(line1);
        setLine2(line2);
        setPostalOrZipCode(postalOrZipCode);
        setCountryCode(countryCode.name());
        setTownOrCity(townOrCity);
        setStateOrCounty(stateOrCounty);
    }

    private Address() {}

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = trimToNull(line1);
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = trimToNull(line2);
    }

    public String getPostalOrZipCode() {
        return postalOrZipCode;
    }

    public void setPostalOrZipCode(String postalOrZipCode) {
        this.postalOrZipCode = trimToNull(postalOrZipCode);
    }

    public CountryCode getCountryCode() {
        return CountryCode.valueOf(countryCode.toUpperCase());
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode.name();
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public void setTownOrCity(String townOrCity) {
        this.townOrCity = trimToNull(townOrCity);
    }

    public String getStateOrCounty() {
        return stateOrCounty;
    }

    public void setStateOrCounty(String stateOrCounty) {
        this.stateOrCounty = trimToNull(stateOrCounty);
    }
}
