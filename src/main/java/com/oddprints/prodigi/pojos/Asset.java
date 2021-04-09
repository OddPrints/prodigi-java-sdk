package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
    private String printArea = "default";
    private String url = "https://www.oddprints.com/images/header-dogcat.jpg";

    public static class Builder {
        private String builderUrl;

        public Builder(String url) {
            this.builderUrl = url;
        }

        public Asset build() {
            Asset asset = new Asset();
            asset.setUrl(builderUrl);
            return asset;
        }
    }

    private Asset() {}

    public String getPrintArea() {
        return printArea;
    }

    public void setPrintArea(String printArea) {
        this.printArea = printArea;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
