package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private ShippingMethod shippingMethod;
    private Recipient recipient;
    private List<Item> items;
    private Status status;
    private List<Shipment> shipments;
    private String merchantReference;

    public static class Builder {
        private List<Item> builderItems;
        private ShippingMethod builderShippingMethod;
        private Recipient builderRecipient;
        private String builderMerchantReference;

        public Builder(ShippingMethod shippingMethod, Recipient recipient) {
            builderItems = new ArrayList<>();
            builderShippingMethod = shippingMethod;
            builderRecipient = recipient;
        }

        public Builder addImage(URL url, String sku, int copies) {
            Item item = new Item.Builder(sku, copies).addAsset(url).build();
            builderItems.add(item);
            return this;
        }

        public Builder merchantReference(String merchantReference) {
            builderMerchantReference = merchantReference;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setItems(builderItems);
            order.setShippingMethod(builderShippingMethod);
            order.setRecipient(builderRecipient);
            return order;
        }
    }

    private Order() {}

    @JsonIgnore // don't serialise id...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
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

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    @JsonIgnore // don't serialise...
    public Status getStatus() {
        return status;
    }

    @JsonProperty // ...but allow it to be read
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore // don't serialise...
    public List<Shipment> getShipments() {
        return shipments;
    }

    @JsonProperty // ...but allow it to be read
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @JsonIgnore // don't serialise...
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonProperty // ...but allow it to be read
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @JsonIgnore // don't serialise...
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty // ...but allow it to be read
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public enum ShippingMethod {
        Budget,
        Standard,
        Express,
        Overnight;
    }

    public enum QualityLevel {
        Pro,
        Standard;
    }
}
