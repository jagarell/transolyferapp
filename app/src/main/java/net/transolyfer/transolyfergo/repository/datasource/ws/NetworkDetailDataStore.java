package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.domain.callback.DetailOrderCallback;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

public interface NetworkDetailDataStore {
    void showDetailOrder(OrderDetail orderDetail, RepositoryCallback detailOrderCallback);
}
