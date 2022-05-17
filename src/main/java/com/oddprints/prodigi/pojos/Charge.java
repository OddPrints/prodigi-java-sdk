package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Charge {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private String prodigiInvoiceNumber;
    private Cost totalCost;
    private List<ChargeItem> items;

    private Charge() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdigiInvoiceNumber() {
        return prodigiInvoiceNumber;
    }

    public void setProdigiInvoiceNumber(String prodigiInvoiceNumber) {
        this.prodigiInvoiceNumber = prodigiInvoiceNumber;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Cost totalCost) {
        this.totalCost = totalCost;
    }

    public List<ChargeItem> getItems() {
        return items;
    }

    public void setItems(List<ChargeItem> items) {
        this.items = items;
    }
}
