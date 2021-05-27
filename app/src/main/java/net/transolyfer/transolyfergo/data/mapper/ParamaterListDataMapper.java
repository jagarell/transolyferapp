package net.transolyfer.transolyfergo.data.mapper;

import net.transolyfer.transolyfergo.data.entity.ParameterEntity;
import net.transolyfer.transolyfergo.domain.model.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josecontreras on 8/7/17.
 */

public class ParamaterListDataMapper {

    private static ParamaterListDataMapper mInstance;

    public Parameter transformParameter(ParameterEntity parameterEntity) {
        Parameter parameter = null;
        if(parameterEntity !=null){
            parameter = new Parameter();

            if(parameterEntity.getParameterCod() != 0) parameter.
                    setParameterCod(parameterEntity.getParameterCod());
            else parameter.setParameterCod(0);

            if(parameterEntity.getDescription() != "") parameter.
                    setDescription(parameterEntity.getDescription());
            else parameter.setDescription("");

            if(parameterEntity.getParameterValue() != "") parameter.
                    setParameterValue(parameterEntity.getParameterValue());
            else parameter.setParameterValue("");
        }

        return parameter;
    }

    public List<Parameter> transformParameterList(List<ParameterEntity> parameterEntityList) {
        List<Parameter> parameterList = new ArrayList<>();
        if(parameterEntityList != null){
            if(parameterEntityList.size() > 0){
                try {
                    for(ParameterEntity parameterEntity : parameterEntityList){
                        parameterList.add(transformParameter(parameterEntity));
                    }
                } catch (Exception e){

                }
            }
        }
        return parameterList;
    }

    public static synchronized ParamaterListDataMapper getInstance() {
        if (mInstance == null) {
            mInstance = new ParamaterListDataMapper();
        }
        return mInstance;
    }
}
