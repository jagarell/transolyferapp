package net.transolyfer.transolyfergo.domain.repository;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.domain.callback.RegisterCallback;

import java.util.List;

/**
 * Created by admin on 05/04/2017.
 */

public interface RegisterRepository {

    void doRegister(OrderRegister orderRegister, RegisterCallback registerCallback);
    void validateOrder(OrderValidate orderValidate, RegisterCallback registerCallback);
    void getEvents(RegisterCallback registerCallback);
    void getEnterprises(RegisterCallback registerCallback);
    void getParameters(RegisterCallback registerCallback);
    void saveOrderOffline(OrderRegister orderRegister, RegisterCallback registerCallback);
    void sendPlantsNotSendedToServer(List<OrderRegister> plantList,RegisterCallback registerCallback);
    void getRegistersOffline(RegisterCallback registerCallback);
    void valiteRegistersOffline(RegisterCallback registerCallback);
    void clearRegisters();
    void getPlantsOfflineToSendToServer(RegisterCallback plantListCallback);
}
