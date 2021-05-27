package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.data.entity.response.DetailIntranetResponse;
import net.transolyfer.transolyfergo.data.entity.response.ResponseParser;
import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDetailIntranetDataStoreImpl implements NetworkDetailIntranetDataStore {

    private final ApiClient restApi;

    public NetworkDetailIntranetDataStoreImpl(ApiClient restApi) {
        this.restApi = restApi;
    }

    @Override
    public void getEnterprises(RepositoryCallback repositoryCallback) {
        Call<ResponseParser<List<EnterpriseEntity>>> call = restApi.getTransolyferInterface().getEnterprise();
        call.enqueue(new Callback<ResponseParser<List<EnterpriseEntity>>>() {
            @Override
            public void onResponse(Call<ResponseParser<List<EnterpriseEntity>>> call, Response<ResponseParser<List<EnterpriseEntity>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<EnterpriseEntity>> enterpriseResponse = response.body();
                    try {
                        if(enterpriseResponse != null){
                            repositoryCallback.onSuccess(enterpriseResponse.getResultResponse());
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseParser<List<EnterpriseEntity>>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getDetailIntranet(OrderDetailIntranet detailIntranet, final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<DetailIntranetResponse>> call = restApi.getTransolyferInterface().getDetailOrderIntranet(detailIntranet);
        call.enqueue(new Callback<ResponseParser<DetailIntranetResponse>>() {
            @Override
            public void onResponse(Call<ResponseParser<DetailIntranetResponse>> call, Response<ResponseParser<DetailIntranetResponse>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<DetailIntranetResponse> detailIntranetResponse = response.body();
                    try {
                        if(detailIntranetResponse != null){
                            if (detailIntranetResponse.getResultResponse()!=null) {
                                repositoryCallback.onSuccess(detailIntranetResponse.getResultResponse());
                            }else{
                                repositoryCallback.onSuccess(detailIntranetResponse.getServiceResponse().getMessageError());
                            }
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseParser<DetailIntranetResponse>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });

    }
}
