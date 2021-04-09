package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
    private String printArea = "default";
    private URL url;

    public static class Builder {
        private URL builderUrl;

        public Builder(URL url) {
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
