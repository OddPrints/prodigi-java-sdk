package com.oddprints.prodigi.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionsResponse extends Outcome {
    private Action cancel;
    private Action changeRecipientDetails;
    private Action changeShippingMethod;
    private Action changeMetaData;

    private ActionsResponse() {}

    public Action getCancel() {
        return cancel;
    }

    public void setCancel(Action cancel) {
        this.cancel = cancel;
    }

    public Action getChangeRecipientDetails() {
        return changeRecipientDetails;
    }

    public void setChangeRecipientDetails(Action changeRecipientDetails) {
        this.changeRecipientDetails = changeRecipientDetails;
    }

    public Action getChangeShippingMethod() {
        return changeShippingMethod;
    }

    public void setChangeShippingMethod(Action changeShippingMethod) {
        this.changeShippingMethod = changeShippingMethod;
    }

    public Action getChangeMetaData() {
        return changeMetaData;
    }

    public void setChangeMetaData(Action changeMetaData) {
        this.changeMetaData = changeMetaData;
    }
}
