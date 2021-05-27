package net.transolyfer.transolyfergo.repository.datasource;

import android.content.Context;

import net.transolyfer.transolyfergo.data.rest.ApiClient;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailDataStore;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailOrderDataStoreImpl;

public class DetailOrderDataStoreFactory {

    private final Context context;

    public DetailOrderDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public NetworkDetailDataStore createSource() {
        NetworkDetailDataStore registerDataStore = createNetworkDataStore();
        return registerDataStore;
    }

    public NetworkDetailDataStore createNetworkDataStore() {
        ApiClient restApi = new ApiClient(this.context);
        return new NetworkDetailOrderDataStoreImpl(restApi);
    }
}
