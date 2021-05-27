package net.transolyfer.transolyfergo.repository.datasource.database;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.util.List;

/**
 * Created by sergio on 9/7/16.
 */
public interface RegisterPlantDatabaseDataStore {
    void savePlant(OrderRegister orderRegister, RepositoryCallback repositoryCallback);
    void getRegistersOffline(RepositoryCallback repositoryCallback);
    List<OrderRegister> getPlantsNotSendedToServer(RepositoryCallback repositoryCallback);
    void clearOrders();


}
