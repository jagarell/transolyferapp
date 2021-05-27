package net.transolyfer.transolyfergo.data.entity.raw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderValidate implements Serializable {

    @SerializedName("Id_Empresa")
    private int idEnterprise;

    @SerializedName("CodigoPedido")
    private String orderNumber;

    public int getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(int idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
