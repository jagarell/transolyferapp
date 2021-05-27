package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;
import net.transolyfer.transolyfergo.data.entity.response.ResponseParser;
import net.transolyfer.transolyfergo.data.entity.response.UserResponse;
import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUserDataStoreImpl implements NetworkUserDataStore {


    private final ApiClient restApi;

    public NetworkUserDataStoreImpl(ApiClient restApi) {
        this.restApi = restApi;
    }

    @Override
    public void doLogin(UserData userData, RepositoryCallback repositoryCallback) {

        Call<ResponseParser<List<UserResponse>>> call = restApi.getTransolyferInterface().doLogin(userData);
        call.enqueue(new Callback<ResponseParser<List<UserResponse>>>(){
            @Override
            public void onResponse(Call<ResponseParser<List<UserResponse>>> call, Response<ResponseParser<List<UserResponse>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<UserResponse>> detailResponse = response.body();
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
            public void onFailure(Call<ResponseParser<List<UserResponse>>> call, Throwable t) {
                repositoryCallback.onFailure(t);

            }
        });

    }
}
