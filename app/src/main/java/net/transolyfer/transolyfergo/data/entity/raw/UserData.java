package net.transolyfer.transolyfergo.data.entity.raw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData implements Serializable {

    @SerializedName("User")
    private String orderNumber;

    @SerializedName("Password")
    private String password;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
