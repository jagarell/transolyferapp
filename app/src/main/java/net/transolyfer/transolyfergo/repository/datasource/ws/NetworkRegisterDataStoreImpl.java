package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.data.entity.EventEntity;
import net.transolyfer.transolyfergo.data.entity.ParameterEntity;
import net.transolyfer.transolyfergo.data.entity.raw.DataListRaw;
import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.data.entity.response.OrderValidateResponse;
import net.transolyfer.transolyfergo.data.entity.response.RegisterResponse;
import net.transolyfer.transolyfergo.data.entity.response.ResponseParser;
import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 21/03/2017.
 */

public class NetworkRegisterDataStoreImpl implements NetworkRegisterDataStore {

    private final ApiClient restApi;

    public NetworkRegisterDataStoreImpl(ApiClient restApi) {
        this.restApi = restApi;
    }

    @Override
    public void doRegister(OrderRegister orderRegister, final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<RegisterResponse>> call = restApi.getTransolyferInterface().doRegister(orderRegister);
        call.enqueue(new Callback<ResponseParser<RegisterResponse>>() {
            @Override
            public void onResponse(Call<ResponseParser<RegisterResponse>> call, Response<ResponseParser<RegisterResponse>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<RegisterResponse> registerResponse = response.body();
                    try {
                        if(registerResponse != null){
                            String message = registerResponse.getResultResponse().getMessage();
                            repositoryCallback.onSuccess(message);
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseParser<RegisterResponse>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void doValidate(OrderValidate orderValidate, RepositoryCallback repositoryCallback) {
        Call<ResponseParser<List<OrderValidateResponse>>> call = restApi.getTransolyferInterface().validateCode(orderValidate);
        call.enqueue(new Callback<ResponseParser<List<OrderValidateResponse>>>() {
            @Override
            public void onResponse(Call<ResponseParser<List<OrderValidateResponse>>> call, Response<ResponseParser<List<OrderValidateResponse>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<OrderValidateResponse>> validateResponse = response.body();
                    try {
                        if(validateResponse != null){
                            String message = validateResponse.getResultResponse().get(0).getOrderCode();
                            repositoryCallback.onSuccess(message);
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseParser<List<OrderValidateResponse>>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void doMassiveRegister(DataListRaw dataListRaw, final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<RegisterResponse>> call = restApi.getTransolyferInterface().doMasiveRegister(dataListRaw);
        call.enqueue(new Callback<ResponseParser<RegisterResponse>>() {
            @Override
            public void onResponse(Call<ResponseParser<RegisterResponse>> call, Response<ResponseParser<RegisterResponse>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<RegisterResponse> registerResponse = response.body();
                    try {
                        if(registerResponse != null){
                            String message = registerResponse.getResultResponse().getMessage();
                            repositoryCallback.onSuccess(message);
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseParser<RegisterResponse>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getEvents(final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<List<EventEntity>>> call = restApi.getTransolyferInterface().getEventList();
        call.enqueue(new Callback<ResponseParser<List<EventEntity>>>() {
            @Override
            public void onResponse(Call<ResponseParser<List<EventEntity>>> call, Response<ResponseParser<List<EventEntity>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<EventEntity>> nationalityResponse = response.body();
                    try {
                        if(nationalityResponse != null){
                            repositoryCallback.onSuccess(nationalityResponse.getResultResponse());
                        }
                    }catch (Exception e){
                        repositoryCallback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseParser<List<EventEntity>>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getEnterprises(final RepositoryCallback repositoryCallback) {
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
    public void getParameters(final RepositoryCallback repositoryCallback) {
        Call<ResponseParser<List<ParameterEntity>>> call = restApi.getTransolyferInterface().getParameters();
        call.enqueue(new Callback<ResponseParser<List<ParameterEntity>>>() {
            @Override
            public void onResponse(Call<ResponseParser<List<ParameterEntity>>> call, Response<ResponseParser<List<ParameterEntity>>> response) {
                boolean success = response.isSuccessful();
                if(success){
                    ResponseParser<List<ParameterEntity>> enterpriseResponse = response.body();
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
            public void onFailure(Call<ResponseParser<List<ParameterEntity>>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }
}
