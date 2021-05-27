package net.transolyfer.transolyfergo.repository;

import android.util.Log;

import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.data.entity.EventEntity;
import net.transolyfer.transolyfergo.data.entity.ParameterEntity;
import net.transolyfer.transolyfergo.data.entity.raw.DataListRaw;
import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.data.mapper.EnterpriseListDataMapper;
import net.transolyfer.transolyfergo.data.mapper.EventListDataMapper;
import net.transolyfer.transolyfergo.data.mapper.ParamaterListDataMapper;
import net.transolyfer.transolyfergo.domain.callback.RegisterCallback;
import net.transolyfer.transolyfergo.domain.model.Enterprise;
import net.transolyfer.transolyfergo.domain.model.Event;
import net.transolyfer.transolyfergo.domain.model.Parameter;
import net.transolyfer.transolyfergo.domain.repository.RegisterRepository;
import net.transolyfer.transolyfergo.repository.datasource.RegisterDataStoreFactory;
import net.transolyfer.transolyfergo.repository.datasource.database.RegisterPlantDatabaseDataStore;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkRegisterDataStore;

import java.util.List;

/**
 * Created by admin on 05/04/2017.
 */

public class RegisterRepositoryImpl implements RegisterRepository {

    private final RegisterDataStoreFactory registerDataStoreFactory;
    private final EventListDataMapper eventListDataMapper;
    private final EnterpriseListDataMapper enterpriseListDataMapper;
    private final ParamaterListDataMapper paramaterListDataMapper;

    public RegisterRepositoryImpl(RegisterDataStoreFactory registerDataStoreFactory, EventListDataMapper eventListDataMapper,
                                  EnterpriseListDataMapper enterpriseListDataMapper,ParamaterListDataMapper paramaterListDataMapper) {
        this.registerDataStoreFactory = registerDataStoreFactory;
        this.eventListDataMapper = eventListDataMapper;
        this.enterpriseListDataMapper = enterpriseListDataMapper;
        this.paramaterListDataMapper = paramaterListDataMapper;
    }

    @Override
    public void doRegister(OrderRegister orderRegister, final RegisterCallback registerCallback) {
        final NetworkRegisterDataStore networkRegisterDataStore = this.registerDataStoreFactory.createSource();
        networkRegisterDataStore.doRegister(orderRegister, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onRegisterSuccess(object);
            }

            @Override
            public void onFailure(Exception e) {
                registerCallback.onRegisterFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                registerCallback.onRegisterFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                registerCallback.onMessageError(message);
            }
        });
    }

    @Override
    public void validateOrder(OrderValidate orderValidate, RegisterCallback registerCallback) {
        final NetworkRegisterDataStore networkRegisterDataStore = this.registerDataStoreFactory.createSource();
        networkRegisterDataStore.doValidate(orderValidate, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onValidateSuccess(object);
            }
            @Override
            public void onFailure(Exception e) {
                registerCallback.onValidateFailure(e);
            }
            @Override
            public void onFailure(Throwable throwable) {
                registerCallback.onValidateFailure(null);
            }
            @Override
            public void onMessageError(String message) {
                registerCallback.onMessageError(message);
            }
        });
    }

    @Override
    public void getEvents(final RegisterCallback registerCallback) {
        final NetworkRegisterDataStore networkRegisterDataStore = this.registerDataStoreFactory.createSource();
        networkRegisterDataStore.getEvents(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                List<EventEntity> eventEntityList = (List<EventEntity>) object;
                List<Event> eventList = eventListDataMapper.transformNationalityList(eventEntityList);
                registerCallback.onNationalitySuccess(eventList);
            }

            @Override
            public void onFailure(Exception e) {
                registerCallback.onNationalityFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                registerCallback.onNationalityFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                registerCallback.onMessageError(message);
            }
        });
    }

    @Override
    public void getEnterprises(final RegisterCallback registerCallback) {
        final NetworkRegisterDataStore networkRegisterDataStore = this.registerDataStoreFactory.createSource();
        networkRegisterDataStore.getEnterprises(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                List<EnterpriseEntity> enterpriseEntityList = (List<EnterpriseEntity>) object;
                List<Enterprise> enterpriseList = enterpriseListDataMapper.transformEnterpriseList(enterpriseEntityList);
                registerCallback.onEnterpriseSuccess(enterpriseList);
            }

            @Override
            public void onFailure(Exception e) {
                registerCallback.onEnterpriseFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                registerCallback.onEnterpriseFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                registerCallback.onMessageError(message);
            }
        });
    }

    @Override
    public void getParameters(final RegisterCallback registerCallback) {
        final NetworkRegisterDataStore networkRegisterDataStore = this.registerDataStoreFactory.createSource();
        networkRegisterDataStore.getParameters(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                List<ParameterEntity> parameterEntityList = (List<ParameterEntity>) object;
                List<Parameter> parameterList = paramaterListDataMapper.transformParameterList(parameterEntityList);
                registerCallback.onParameterSuccess(parameterList);
            }

            @Override
            public void onFailure(Exception e) {
                registerCallback.onParameterFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                registerCallback.onParameterFailure(null);
            }

            @Override
            public void onMessageError(String message) {
            }
        });
    }

    @Override
    public void saveOrderOffline(OrderRegister orderRegister, final RegisterCallback registerCallback) {
        final RegisterPlantDatabaseDataStore registerPlantDataStore = this.registerDataStoreFactory.createPlantDatabase();
        registerPlantDataStore.savePlant(orderRegister, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onSavePlantSuccess();
            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onMessageError(String message) {

            }
        });
    }

    @Override
    public void sendPlantsNotSendedToServer(List<OrderRegister> plantList, final RegisterCallback registerCallback) {
        final NetworkRegisterDataStore plantListDataStore = this.registerDataStoreFactory.createSource();
        DataListRaw dataListRaw = new DataListRaw();
        dataListRaw.setListDataRaw(plantList);
        plantListDataStore.doMassiveRegister(dataListRaw, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onSendAllPlantsToServerSuccess(object);

            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onMessageError(String message) {

            }
        });
    }

    @Override
    public void getRegistersOffline(final RegisterCallback registerCallback) {
        final RegisterPlantDatabaseDataStore registerPlantDataStore = this.registerDataStoreFactory.createPlantDatabase();
        registerPlantDataStore.getRegistersOffline(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onTotalRegistersOfflineSuccess((int)object);
            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onMessageError(String message) {

            }
        });
    }

    @Override
    public void valiteRegistersOffline(final RegisterCallback registerCallback) {
        final RegisterPlantDatabaseDataStore registerPlantDataStore = this.registerDataStoreFactory.createPlantDatabase();
        registerPlantDataStore.getRegistersOffline(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                registerCallback.onValiteRegistersOfflineSuccess((int)object);
            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onMessageError(String message) {

            }
        });
    }

    @Override
    public void clearRegisters() {
        final RegisterPlantDatabaseDataStore registerPlantDataStore = this.registerDataStoreFactory.createPlantDatabase();
        registerPlantDataStore.clearOrders();
    }

    @Override
    public void getPlantsOfflineToSendToServer(final RegisterCallback plantListCallback) {
        final RegisterPlantDatabaseDataStore plantsListDatabaseDataStore = this.registerDataStoreFactory.createPlantDatabase();
        plantsListDatabaseDataStore.getPlantsNotSendedToServer(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                List<OrderRegister> plantList =
                        (List<OrderRegister>) object;
                plantListCallback.onGetPlantNotSendedToServerListSuccess(plantList);
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("log",e.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onMessageError(String message) {
                Log.i("log",message);

            }
        });
    }
}
