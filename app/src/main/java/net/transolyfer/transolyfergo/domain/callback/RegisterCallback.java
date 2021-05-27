package net.transolyfer.transolyfergo.domain.callback;

/**
 * Created by admin on 05/04/2017.
 */

public interface RegisterCallback<T> {
    void onRegisterSuccess(T object);
    void onRegisterFailure(Exception e);
    void onValidateSuccess(T object);
    void onValidateFailure(Exception e);
    void onNationalitySuccess(T object);
    void onNationalityFailure(Exception e);
    void onEnterpriseSuccess(T object);
    void onEnterpriseFailure(Exception e);
    void onParameterSuccess(T object);
    void onParameterFailure(Exception e);
    void onMessageError(String message);
    void onSavePlantSuccess();
    void onTotalRegistersOfflineSuccess(int totalRegistersOffline);
    void onValiteRegistersOfflineSuccess(int totalRegistersOffline);
    void onGetPlantNotSendedToServerListSuccess(T object);
    void onSendAllPlantsToServerSuccess(T object);

}
