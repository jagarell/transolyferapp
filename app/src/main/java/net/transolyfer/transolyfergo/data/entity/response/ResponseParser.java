package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jose.arellano on 02/03/2017.
 */

public class ResponseParser<T> {

    @SerializedName("Result")
    private T resultResponse;

    @SerializedName("RespuestaServicio")
    private ServiceResponse serviceResponse;

    public T getResultResponse() {
        return resultResponse;
    }

    public void setResultResponse(T resultResponse) {
        this.resultResponse = resultResponse;
    }

    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public boolean isSuccessful(){
        return !serviceResponse.isError();
    }

}
