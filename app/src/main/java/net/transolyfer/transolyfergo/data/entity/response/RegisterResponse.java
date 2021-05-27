package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 21/03/2017.
 */

public class RegisterResponse implements Serializable {

    @SerializedName("Mensaje")
    private String message;

    @SerializedName("Retorno")
    private boolean retorno;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRetorno() {
        return retorno;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }
}
