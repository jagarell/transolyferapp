package net.transolyfer.transolyfergo.domain.repository;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.domain.callback.DetailOrderCallback;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

public interface DetailOrderRepository {
    void showDetailOrder(OrderDetail orderDetail, DetailOrderCallback detailOrderCallback);
}
