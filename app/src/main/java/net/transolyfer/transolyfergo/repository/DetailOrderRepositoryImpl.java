package net.transolyfer.transolyfergo.repository;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.domain.callback.DetailOrderCallback;
import net.transolyfer.transolyfergo.domain.repository.DetailOrderRepository;
import net.transolyfer.transolyfergo.repository.datasource.DetailOrderDataStoreFactory;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailDataStore;

public class DetailOrderRepositoryImpl implements DetailOrderRepository {

    private final DetailOrderDataStoreFactory detailOrderDataStoreFactory;

    public DetailOrderRepositoryImpl(DetailOrderDataStoreFactory detailOrderDataStoreFactory) {
        this.detailOrderDataStoreFactory = detailOrderDataStoreFactory;
    }

    @Override
    public void showDetailOrder(OrderDetail orderDetail,final DetailOrderCallback detailOrderCallback) {
        final NetworkDetailDataStore networkDetailDataStore = this.detailOrderDataStoreFactory.createSource();
        networkDetailDataStore.showDetailOrder(orderDetail, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                detailOrderCallback.onDetailOrderSuccess(object);
            }

            @Override
            public void onFailure(Exception e) {
                detailOrderCallback.onDetailOrderFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                detailOrderCallback.onDetailOrderFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                detailOrderCallback.onMessageError(message);
            }
        });
    }
}
