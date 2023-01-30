package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String id;

    private String sku = "GLOBAL-PHO-4x6";
    private int copies = 1;
    private String sizing = "fillPrintArea";
    private List<Asset> assets;
    private Map<String, String> attributes;
    private String merchantReference;

    public static class Builder {
        private List<Asset> builderAssets;
        private String builderSku;
        private Map<String, String> builderAttributes = new HashMap<>();
        private int builderCopies;
        private String builderMerchantReference;

        public Builder(String sku, int copies) {
            this(sku, copies, null);
        }

        public Builder(String sku, int copies, String merchantReference) {
            this.builderAssets = new ArrayList<>();
            this.builderSku = sku;
            builderAttributes.put("finish", "lustre"); // default
            builderCopies = copies;
            builderMerchantReference = merchantReference;
        }

        public Builder addAsset(URL url) {
            Asset asset = new Asset.Builder(url).build();
            builderAssets.add(asset);
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.setAssets(builderAssets);
            item.setSku(builderSku);
            item.setAttributes(builderAttributes);
            item.setCopies(builderCopies);
            item.setMerchantReference(builderMerchantReference);
            return item;
        }
    }

    private Item() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getSizing() {
        return sizing;
    }

    public void setSizing(String sizing) {
        this.sizing = sizing;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
