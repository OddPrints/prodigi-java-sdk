package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    private String shippingMethod = "Overnight";
    private Recipient recipient = new Recipient();
    private List<Item> items;
    private Status status;

    public Order() {
        items = new ArrayList<>();
        items.add(new Item());
    }

    @JsonIgnore // don't serialise id...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonIgnore // don't serialise Status...
    public Status getStatus() {
        return status;
    }

    @JsonProperty // ...but allow it to be read
    public void setStatus(Status status) {
        this.status = status;
    }
}
