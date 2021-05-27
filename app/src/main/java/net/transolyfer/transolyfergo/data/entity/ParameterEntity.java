package net.transolyfer.transolyfergo.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by josecontreras on 8/7/17.
 */

public class ParameterEntity implements Serializable {
    @SerializedName("CodigoParametro")
    private int parameterCod;

    @SerializedName("Descripcion")
    private String description;

    @SerializedName("ValorParametro")
    private String parameterValue;

    public int getParameterCod() {
        return parameterCod;
    }

    public void setParameterCod(int parameterCod) {
        this.parameterCod = parameterCod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
