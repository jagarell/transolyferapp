package net.transolyfer.transolyfergo.domain.model;

import java.io.Serializable;

/**
 * Created by jose.arellano on 06/07/2017.
 */

public class Enterprise implements Serializable {

    private int enterpriseId;

    private String enterpriseDescription;

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
