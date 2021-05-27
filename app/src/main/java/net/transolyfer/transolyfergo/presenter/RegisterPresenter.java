package net.transolyfer.transolyfergo.presenter;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.data.mapper.EnterpriseListDataMapper;
import net.transolyfer.transolyfergo.data.mapper.EventListDataMapper;
import net.transolyfer.transolyfergo.data.mapper.ParamaterListDataMapper;
import net.transolyfer.transolyfergo.domain.callback.RegisterCallback;
import net.transolyfer.transolyfergo.domain.interactor.RegisterInteractor;
import net.transolyfer.transolyfergo.domain.repository.RegisterRepository;
import net.transolyfer.transolyfergo.presenter.view.RegisterView;
import net.transolyfer.transolyfergo.repository.RegisterRepositoryImpl;
import net.transolyfer.transolyfergo.repository.datasource.RegisterDataStoreFactory;

import java.util.List;

/**
 * Created by admin on 05/04/2017.
 */

public class RegisterPresenter implements Presenter<RegisterView>, RegisterCallback {

    private RegisterView registerView;
    private RegisterInteractor registerInteractor;
    private RegisterRepository registerRepository;
    private int register;

    public void registerPlantOffline(OrderRegister plantRegisterIntermediate, int register) {
        this.register = register;
        registerView.showLoading();
        saveOrderToRegister(plantRegisterIntermediate);
    }
    @Override
    public void attachedView(RegisterView view) {
        registerView = view;
        registerRepository = new RegisterRepositoryImpl(new RegisterDataStoreFactory(
                registerView.getContext()), new EventListDataMapper(),
                new EnterpriseListDataMapper(), new ParamaterListDataMapper());
        registerInteractor = new RegisterInteractor(registerRepository);
    }

    @Override
    public void detachView() {
        registerView = null;
    }

    public void doRegister(OrderRegister orderRegister) {
        registerView.showLoading();
        registerInteractor.doRegister(orderRegister, this);
    }

    public void doValidate(OrderValidate orderValidate) {
        registerView.showLoading();
        registerInteractor.doValidate(orderValidate, this);
    }

    public void getEvents(){
        registerView.showLoading();
        registerInteractor.getEvents(this);
    }

    public void getEnterprises(){
        registerView.showLoading();
        registerInteractor.getEnterprises(this);
    }

    public void getParameters(){
        registerView.showLoading();
        registerInteractor.getParameters(this);
    }

    public void getTotalDataOffline(){
        registerInteractor.getRegistersOffline(this);
    }

    public void valitePendings(){
        registerInteractor.validateInfoPendings(this);
    }

    public void getOrdersNotSendedToServer(){
        registerView.showLoading();
        registerInteractor.getPlantsOfflineNotSendedToServer(this);
    }

    @Override
    public void onRegisterSuccess(Object object) {
        registerView.hideLoading();
        registerView.registerSuccess();
        if(!(object.toString().equals(""))) {
            registerView.showMessage(object.toString());
        }
    }

    @Override
    public void onRegisterFailure(Exception e) {
        registerView.hideLoading();
        //registerView.showMessage("Lo sentimos, Ocurrio un error tratar de conectarse con el servidor. " +
        //        "Intente nuevamente");
    }

    @Override
    public void onValidateSuccess(Object object) {
        registerView.hideLoading();
        registerView.validateCodeSucess(object);
    }

    @Override
    public void onValidateFailure(Exception e) {
        registerView.hideLoading();
        registerView.showMessageValidate("El CodigoOnline no existe en la Base de Datos");
    }

    @Override
    public void onNationalitySuccess(Object object) {
        registerView.hideLoading();
        registerView.getNationalitiesSuccess(object);
    }

    @Override
    public void onNationalityFailure(Exception e) {
        registerView.hideLoading();
        registerView.showMessage("Lo sentimos, Ocurrio un error tratar de conectarse con el servidor. " +
                "Intente nuevamente");
    }

    @Override
    public void onEnterpriseSuccess(Object object) {
        registerView.hideLoading();
        registerView.getEnterprisesSuccess(object);
    }

    @Override
    public void onEnterpriseFailure(Exception e) {
        registerView.hideLoading();
        registerView.showMessage("Lo sentimos, Ocurrio un error tratar de conectarse con el servidor. " +
                "Intente nuevamente");
    }

    @Override
    public void onParameterSuccess(Object object) {
        registerView.hideLoading();
        registerView.getParameterSuccess(object);
    }

    @Override
    public void onParameterFailure(Exception e) {
        registerView.hideLoading();
    }

    @Override
    public void onMessageError(String message) {
        registerView.hideLoading();
        registerView.showMessage(message);
    }

    @Override
    public void onSavePlantSuccess() {
        registerView.hideLoading();
    }

    @Override
    public void onTotalRegistersOfflineSuccess(int totalRegistersOffline) {
        registerView.totalRegisterOffline(totalRegistersOffline);
    }

    @Override
    public void onValiteRegistersOfflineSuccess(int totalRegistersOffline) {
        registerView.validateImageView(totalRegistersOffline);
    }

    @Override
    public void onGetPlantNotSendedToServerListSuccess(Object object) {
        List<OrderRegister> plantList = (List<OrderRegister>) object;
        if(plantList.size()!=0) {
            sendPlantsNotSendedToServer(plantList);
        }else{
            registerView.hideLoading();
            registerView.showMessage("No hay pedido(s) pendiente(s) por enviar");
        }
    }

    @Override
    public void onSendAllPlantsToServerSuccess(Object object) {
        registerView.hideLoading();
        clearOrdersDataBase();
        String message = object.toString().replace(";","\n");
        registerView.showMessage(message);
    }

    public void saveOrderToRegister(OrderRegister plantRegisterIntermediate){
        registerInteractor.saveOrderOffline(plantRegisterIntermediate, this);
        registerView.registerSuccess();
    }

    public void sendPlantsNotSendedToServer(List<OrderRegister> plantList){
        registerInteractor.sendPlantsOfflineNotSendedToServer(plantList, this);
    }

    public void clearOrdersDataBase(){
        registerInteractor.clearOrderRegister();
        registerView.syncSuccess();
    }

}
