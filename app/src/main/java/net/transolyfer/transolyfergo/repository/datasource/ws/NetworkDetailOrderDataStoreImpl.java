package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;
import net.transolyfer.transolyfergo.data.entity.response.ResponseParser;
import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDetailOrderDataStoreImpl implements NetworkDetailDataStore {

    private final ApiClient restApi;

    public NetworkDetailOrderDataStoreImpl(ApiClient restApi) {
        this.restApi = restApi;
    }

    @Override
    public void showDetailOrder(OrderDetail orderDetail, final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<List<DetailOrderResponse>>> call = restApi.getTransolyferInterface().getDetailOrder(orderDetail);
        call.enqueue(new Callback<ResponseParser<List<DetailOrderResponse>>>(){
            @Override
            public void onResponse(Call<ResponseParser<List<DetailOrderResponse>>> call, Response<ResponseParser<List<DetailOrderResponse>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<DetailOrderResponse>> detailResponse = response.body();
                    try {
                        if(detailResponse != null){
                            if (detailResponse.getResultResponse()!=null) {
                                repositoryCallback.onSuccess(detailResponse.getResultResponse());
                            }else{
                                repositoryCallback.onSuccess(detailResponse.getServiceResponse().getMessageError());
                            }
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseParser<List<DetailOrderResponse>>> call, Throwable t) {
                repositoryCallback.onFailure(t);

            }
        });
    }
}
