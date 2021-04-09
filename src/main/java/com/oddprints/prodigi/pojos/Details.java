package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {
    private String downloadAssets;
    private String allocateProductionLocation;
    private String printReadyAssetsPrepared;
    private String inProduction;
    private String shipping;

    private Details() {}

    public String getDownloadAssets() {
        return downloadAssets;
    }

    public void setDownloadAssets(String downloadAssets) {
        this.downloadAssets = downloadAssets;
    }

    public String getAllocateProductionLocation() {
        return allocateProductionLocation;
    }

    public void setAllocateProductionLocation(String allocateProductionLocation) {
        this.allocateProductionLocation = allocateProductionLocation;
    }

    public String getPrintReadyAssetsPrepared() {
        return printReadyAssetsPrepared;
    }

    public void setPrintReadyAssetsPrepared(String printReadyAssetsPrepared) {
        this.printReadyAssetsPrepared = printReadyAssetsPrepared;
    }

    public String getInProduction() {
        return inProduction;
    }

    public void setInProduction(String inProduction) {
        this.inProduction = inProduction;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
