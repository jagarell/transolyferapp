package net.transolyfer.transolyfergo.data.mapper;

import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.domain.model.Enterprise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose.arellano on 06/07/2017.
 */

public class EnterpriseListDataMapper {

    private static EnterpriseListDataMapper mInstance;

    public Enterprise transformEnterprise(EnterpriseEntity enterpriseEntity) {
        Enterprise enterprise = null;
        if(enterpriseEntity !=null){
            enterprise = new Enterprise();

            if(enterpriseEntity.getEnterpriseId() != 0) enterprise.
                    setEnterpriseId(enterpriseEntity.getEnterpriseId());
            else enterprise.setEnterpriseId(0);

            if(enterpriseEntity.getEnterpriseDescription() != "") enterprise.
                    setEnterpriseDescription(enterpriseEntity.getEnterpriseDescription());
            else enterprise.setEnterpriseDescription("");

            if(enterpriseEntity.getEnterpriseContainer() != "") enterprise.
                    setEnterpriseContainer(enterpriseEntity.getEnterpriseContainer());
            else enterprise.setEnterpriseContainer("");
        }

        return enterprise;
    }

    public List<Enterprise> transformEnterpriseList(List<EnterpriseEntity> enterpriseEntityList) {
        List<Enterprise> enterpriseList = new ArrayList<>();
        if(enterpriseEntityList != null){
            if(enterpriseEntityList.size() > 0){
                try {
                    enterpriseList.add(0,setDefaultElement("Seleccione"));
                    for(EnterpriseEntity enterpriseEntity : enterpriseEntityList){
                        enterpriseList.add(transformEnterprise(enterpriseEntity));
                    }
                } catch (Exception e){

                }
            }
        }
        return enterpriseList;
    }

    public Enterprise setDefaultElement(String defaultElement){
        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(0);
        enterprise.setEnterpriseDescription(defaultElement);
        enterprise.setEnterpriseContainer("");
        return enterprise;
    }

    public static synchronized EnterpriseListDataMapper getInstance() {
        if (mInstance == null) {
            mInstance = new EnterpriseListDataMapper();
        }
        return mInstance;
    }
}
