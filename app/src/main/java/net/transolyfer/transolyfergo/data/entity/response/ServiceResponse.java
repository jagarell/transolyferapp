package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jose.arellano on 02/03/2017.
 */

public class ServiceResponse {

    @SerializedName("Error")
    private boolean error;

    @SerializedName("CodigoError")
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @SerializedName("MensajeError")
    private String messageError;

    @SerializedName("MensajeDetalle")
    private String messageDetail;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }
}
