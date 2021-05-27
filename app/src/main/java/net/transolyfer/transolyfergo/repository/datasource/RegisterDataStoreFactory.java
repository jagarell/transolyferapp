package net.transolyfer.transolyfergo.repository.datasource;

import android.content.Context;

import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.data.storage.db.DaoFactory;
import net.transolyfer.transolyfergo.repository.datasource.database.DatabaseRegisterPlantDataStore;
import net.transolyfer.transolyfergo.repository.datasource.database.RegisterPlantDatabaseDataStore;
import net.transolyfer.transolyfergo.repository.datasource.preferences.ConfigurationsPreferencesDataStore;
import net.transolyfer.transolyfergo.repository.datasource.preferences.ConfigurationsPreferencesDataStoreImpl;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkRegisterDataStore;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkRegisterDataStoreImpl;

/**
 * Created by Admin on 21/03/2017.
 */

public class RegisterDataStoreFactory {

    private final Context context;

    public RegisterDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public NetworkRegisterDataStore createSource() {
        NetworkRegisterDataStore registerDataStore = createNetworkDataStore();
        return registerDataStore;
    }

    public RegisterPlantDatabaseDataStore createPlantDatabase(){
        RegisterPlantDatabaseDataStore registerPlantDatabaseDataStore = new DatabaseRegisterPlantDataStore(
                new DaoFactory(context));
        return registerPlantDatabaseDataStore;
    }


    public ConfigurationsPreferencesDataStore createPreferences() {
        ConfigurationsPreferencesDataStore configurationsPreferencesDataStore = new ConfigurationsPreferencesDataStoreImpl(context);
        return configurationsPreferencesDataStore;
    }

    public NetworkRegisterDataStore createNetworkDataStore() {
        ApiClient restApi = new ApiClient(this.context);
        return new NetworkRegisterDataStoreImpl(restApi);
    }
}
