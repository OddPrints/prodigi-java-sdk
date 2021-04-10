package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {
    private Detail downloadAssets;
    private Detail allocateProductionLocation;
    private Detail printReadyAssetsPrepared;
    private Detail inProduction;
    private Detail shipping;

    private Details() {}

    public Detail getDownloadAssets() {
        return downloadAssets;
    }

    public void setDownloadAssets(Detail downloadAssets) {
        this.downloadAssets = downloadAssets;
    }

    public Detail getAllocateProductionLocation() {
        return allocateProductionLocation;
    }

    public void setAllocateProductionLocation(Detail allocateProductionLocation) {
        this.allocateProductionLocation = allocateProductionLocation;
    }

    public Detail getPrintReadyAssetsPrepared() {
        return printReadyAssetsPrepared;
    }

    public void setPrintReadyAssetsPrepared(Detail printReadyAssetsPrepared) {
        this.printReadyAssetsPrepared = printReadyAssetsPrepared;
    }

    public Detail getInProduction() {
        return inProduction;
    }

    public void setInProduction(Detail inProduction) {
        this.inProduction = inProduction;
    }

    public Detail getShipping() {
        return shipping;
    }

    public void setShipping(Detail shipping) {
        this.shipping = shipping;
    }

    public enum Detail {
        NotStarted,
        InProgress,
        Complete,
        Error
    }
}
