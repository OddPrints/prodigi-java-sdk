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

    private ShippingMethod shippingMethod;
    private Recipient recipient;
    private List<Item> items;
    private Status status;

    public static class Builder {
        private List<Item> builderItems;
        private ShippingMethod builderShippingMethod;
        private Recipient builderRecipient;

        public Builder(ShippingMethod shippingMethod, Recipient recipient) {
            builderItems = new ArrayList<>();
            builderShippingMethod = shippingMethod;
            builderRecipient = recipient;
        }

        public Builder addImage(String url, String sku, int copies) {
            Item item = new Item.Builder(sku, copies).addAsset(url).build();
            builderItems.add(item);
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

    @JsonIgnore // don't serialise Status...
    public Status getStatus() {
        return status;
    }

    @JsonProperty // ...but allow it to be read
    public void setStatus(Status status) {
        this.status = status;
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
