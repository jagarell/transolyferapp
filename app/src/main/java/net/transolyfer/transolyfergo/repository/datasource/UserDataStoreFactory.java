package net.transolyfer.transolyfergo.repository.datasource;

import android.content.Context;

import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkUserDataStore;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkUserDataStoreImpl;

public class UserDataStoreFactory {

    private final Context context;

    public UserDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public NetworkUserDataStore createSource() {
        NetworkUserDataStore dataStore = createNetworkDataStore();
        return dataStore;
    }

    public NetworkUserDataStore createNetworkDataStore() {
        ApiClient restApi = new ApiClient(this.context);
        return new NetworkUserDataStoreImpl(restApi);
    }
}
