package net.transolyfer.transolyfergo.data.entity.raw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jose.arellano on 12/07/2017.
 */

public class DataListRaw implements Serializable {

    @SerializedName("DataList")
    private List<OrderRegister> listDataRaw;

    public List<OrderRegister> getListDataRaw() {
        return listDataRaw;
    }

    public void setListDataRaw(List<OrderRegister> listDataRaw) {
        this.listDataRaw = listDataRaw;
    }
}
