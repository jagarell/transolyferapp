package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderValidateResponse  implements Serializable {

    @SerializedName("FlagOnline")
    private int onlineFlag;
    @SerializedName("CodigoPedido")
    private String orderCode;

    public int getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(int onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
