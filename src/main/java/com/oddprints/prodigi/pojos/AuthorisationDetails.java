package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorisationDetails {
    private String authorisationUrl;
    private Cost paymentDetails;

    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    public void setAuthorisationUrl(String authorisationUrl) {
        this.authorisationUrl = authorisationUrl;
    }

    public Cost getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Cost paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
