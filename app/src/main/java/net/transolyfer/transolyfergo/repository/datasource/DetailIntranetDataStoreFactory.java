package net.transolyfer.transolyfergo.repository.datasource;

import android.content.Context;

import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailIntranetDataStore;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailIntranetDataStoreImpl;

public class DetailIntranetDataStoreFactory {

    private final Context context;

    public DetailIntranetDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public NetworkDetailIntranetDataStore createSource() {
        NetworkDetailIntranetDataStore registerDataStore = createNetworkDataStore();
        return registerDataStore;
    }

    public NetworkDetailIntranetDataStore createNetworkDataStore() {
        ApiClient restApi = new ApiClient(this.context);
        return new NetworkDetailIntranetDataStoreImpl(restApi);
    }
}
