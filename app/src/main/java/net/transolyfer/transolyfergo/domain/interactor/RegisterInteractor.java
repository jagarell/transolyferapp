package net.transolyfer.transolyfergo.domain.interactor;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.domain.callback.RegisterCallback;
import net.transolyfer.transolyfergo.domain.repository.RegisterRepository;

import java.util.List;

/**
 * Created by admin on 05/04/2017.
 */

public class RegisterInteractor {

    private RegisterRepository registerRepository;

    public RegisterInteractor(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public void doRegister(OrderRegister orderRegister, final RegisterCallback registerCallback) {
        registerRepository.doRegister(orderRegister, registerCallback);
    }

    public void doValidate(OrderValidate orderValidate, final RegisterCallback registerCallback) {
        registerRepository.validateOrder(orderValidate, registerCallback);
    }

    public void saveOrderOffline(OrderRegister plantRegisterIntermediate, RegisterCallback registerCallback){
        this.registerRepository.saveOrderOffline(
                plantRegisterIntermediate, registerCallback);
    }

    public void sendPlantsOfflineNotSendedToServer(List<OrderRegister> plantList, final RegisterCallback registerCallback) {
        this.registerRepository.sendPlantsNotSendedToServer(plantList, registerCallback);
    }

    public void getEvents(final RegisterCallback registerCallback) {
        registerRepository.getEvents(registerCallback);
    }

    public void getEnterprises(final RegisterCallback registerCallback) {
        registerRepository.getEnterprises(registerCallback);
    }

    public void getParameters(final RegisterCallback registerCallback) {
        registerRepository.getParameters(registerCallback);
    }

    public void getRegistersOffline(final RegisterCallback registerCallback){
        registerRepository.getRegistersOffline(registerCallback);
    }

    public void validateInfoPendings(final RegisterCallback registerCallback){
        registerRepository.valiteRegistersOffline(registerCallback);
    }

    public void getPlantsOfflineNotSendedToServer(final RegisterCallback registerCallback) {
        registerRepository.getPlantsOfflineToSendToServer(registerCallback);
    }

    public void clearOrderRegister(){
        registerRepository.clearRegisters();
    }
}
