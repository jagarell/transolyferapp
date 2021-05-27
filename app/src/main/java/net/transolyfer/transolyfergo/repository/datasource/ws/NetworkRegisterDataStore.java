package net.transolyfer.transolyfergo.repository.datasource.ws;


import net.transolyfer.transolyfergo.data.entity.raw.DataListRaw;
import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.entity.raw.OrderValidate;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

/**
 * Created by Admin on 21/03/2017.
 */

public interface NetworkRegisterDataStore {
    void doRegister(OrderRegister orderRegister, RepositoryCallback repositoryCallback);
    void doValidate(OrderValidate orderValidate, RepositoryCallback repositoryCallback);
    void doMassiveRegister(DataListRaw dataListRaw, RepositoryCallback repositoryCallback);
    void getEvents(RepositoryCallback repositoryCallback);
    void getEnterprises(RepositoryCallback repositoryCallback);
    void getParameters(RepositoryCallback repositoryCallback);
}
