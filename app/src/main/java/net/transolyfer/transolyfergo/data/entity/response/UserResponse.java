package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse implements Serializable {

    @SerializedName("FlagLogin")
    private int flagLogin;
    @SerializedName("NombreCompleto")
    private String fullName;

    public int getFlagLogin() {
        return flagLogin;
    }

    public void setFlagLogin(int flagLogin) {
        this.flagLogin = flagLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
