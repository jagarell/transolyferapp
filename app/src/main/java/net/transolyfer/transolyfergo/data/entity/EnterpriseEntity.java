package net.transolyfer.transolyfergo.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jose.arellano on 06/07/2017.
 */

public class EnterpriseEntity implements Serializable {

    @SerializedName("IdEmpresa")
    private int enterpriseId;

    @SerializedName("Descripcion")
    private String enterpriseDescription;

    @SerializedName("Contenedor")
    private String enterpriseContainer;

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseDescription() {
        return enterpriseDescription;
    }

    public void setEnterpriseDescription(String enterpriseDescription) {
        this.enterpriseDescription = enterpriseDescription;
    }

    public String getEnterpriseContainer() {
        return enterpriseContainer;
    }

    public void setEnterpriseContainer(String enterpriseContainer) {
        this.enterpriseContainer = enterpriseContainer;
    }
}
