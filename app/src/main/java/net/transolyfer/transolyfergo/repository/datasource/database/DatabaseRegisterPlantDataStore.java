package net.transolyfer.transolyfergo.repository.datasource.database;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;
import net.transolyfer.transolyfergo.data.storage.db.DaoFactory;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 9/7/16.
 */
public class DatabaseRegisterPlantDataStore implements RegisterPlantDatabaseDataStore {

    private DaoFactory daoFactory;

    public DatabaseRegisterPlantDataStore(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public void savePlant(OrderRegister orderRegister, RepositoryCallback repositoryCallback) {
        try {
            daoFactory.getPlantRegisterDAO();
            daoFactory.createUpdatePlantRegister(orderRegister);
            repositoryCallback.onSuccess(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRegistersOffline(RepositoryCallback repositoryCallback) {
        try {
            daoFactory.getPlantRegisterDAO();
            int totalRegistersOffline = daoFactory.showTotalRegistersOffline();
            repositoryCallback.onSuccess(totalRegistersOffline);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderRegister> getPlantsNotSendedToServer(RepositoryCallback repositoryCallback) {
        List<OrderRegister> plantRegisterList = new ArrayList<>();
        try {
            daoFactory.getPlantRegisterDAO();
            plantRegisterList = daoFactory.getAllPlantsNotSendedToServer();
            repositoryCallback.onSuccess(plantRegisterList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(plantRegisterList == null){
            return new ArrayList<>();
        }
        return plantRegisterList;
    }

    @Override
    public void clearOrders() {
        try {
            daoFactory.getPlantRegisterDAO();
            daoFactory.clearOrderDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
