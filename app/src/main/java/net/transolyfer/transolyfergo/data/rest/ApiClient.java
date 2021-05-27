package net.transolyfer.transolyfergo.data.rest;

import android.content.Context;

import net.transolyfer.transolyfergo.BuildConfig;
import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.data.entity.EventEntity;
import net.transolyfer.transolyfergo.data.entity.ParameterEntity;
import net.transolyfer.transolyfergo.data.entity.raw.DataListRaw;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.data.entity.response.DetailIntranetResponse;
import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;
import net.transolyfer.transolyfergo.data.entity.response.OrderValidateResponse;
import net.transolyfer.transolyfergo.data.entity.response.RegisterResponse;
import net.transolyfer.transolyfergo.data.entity.response.ResponseParser;
import net.transolyfer.transolyfergo.data.entity.response.UserResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 21/03/2017.
 */

public class ApiClient {

    private final Context context;

    public ApiClient(Context context) {
        if (context == null ) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public TransolyferInterface getTransolyferInterface() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(getBasicClientInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TransolyferInterface transolyferInterface = retrofit.create(TransolyferInterface.class);
        return transolyferInterface;
    }

    public interface TransolyferInterface {

        @POST("InsertPedidoDetalle")
        Call<ResponseParser<RegisterResponse>> doRegister(@Body OrderRegister orderRegister);

        @POST("InsertPedidoDetalleMasivo")
        Call<ResponseParser<RegisterResponse>> doMasiveRegister(@Body DataListRaw registerRawList);

        @POST("ListarEvento")
        Call<ResponseParser<List<EventEntity>>> getEventList();

        @POST("ListarEmpresa")
        Call<ResponseParser<List<EnterpriseEntity>>> getEnterprise();

        @POST("ListarParametro")
        Call<ResponseParser<List<ParameterEntity>>> getParameters();

        @POST("DetallePedido")
        Call<ResponseParser<List<DetailOrderResponse>>> getDetailOrder(@Body OrderDetail orderDetail);

        @POST("ValidarLogin")
        Call<ResponseParser<List<UserResponse>>> doLogin(@Body UserData userData);

        @POST("DetallePedidoIntranet")
        Call<ResponseParser<DetailIntranetResponse>> getDetailOrderIntranet(@Body OrderDetailIntranet orderDetailIntranet);

        @POST("ValidarCodigoPedido")
        Call<ResponseParser<List<OrderValidateResponse>>> validateCode(@Body OrderValidate orderValidate);
    }

    public static OkHttpClient getBasicClientInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        builder.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        return client;
    }
}
