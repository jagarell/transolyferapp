package net.transolyfer.transolyfergo.data.storage.db;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;

import java.util.List;


/**
 * Created by sergio on 7/7/16.
 *
 * Clase de manejo de eventos de la base de datos
 */
public class DaoFactory {

    private DatabaseHelper db;
    private Dao<OrderRegister, Integer> orderRegisterDAO;

    public DaoFactory(Context context) {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(context);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public Dao<OrderRegister, Integer> getPlantRegisterDAO() throws java.sql.SQLException {
        if (orderRegisterDAO == null) {
            orderRegisterDAO = db.getDao(OrderRegister.class);
        }
        return orderRegisterDAO;
    }

    public void createUpdatePlantRegister(OrderRegister plantRegister) {
        try {
            orderRegisterDAO.createOrUpdate(plantRegister);
        }  catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public int showTotalRegistersOffline(){
        int qount = 0;
        try {
            qount =   (int)orderRegisterDAO.countOf();
            GenericRawResults<String[]> rawResults = orderRegisterDAO
                    .queryRaw("SELECT * FROM orderregister");
            List<String[]> results = rawResults.getResults();
            Log.i("Register Offline",results.toString());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return qount;
    }

    public List<OrderRegister> getAllPlantsNotSendedToServer() {
        try {
            return orderRegisterDAO.queryBuilder().query();
        } catch (java.sql.SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }

    public void clearOrderDataBase(){
        db.clearOrderRegisterDatabase();
    }
}
