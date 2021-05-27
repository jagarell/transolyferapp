package net.transolyfer.transolyfergo.data.entity.raw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetailIntranet implements Serializable {

    @SerializedName("NroPedido")
    private String orderNumber;

    @SerializedName("Id_Empresa")
    private String enterpriseId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
