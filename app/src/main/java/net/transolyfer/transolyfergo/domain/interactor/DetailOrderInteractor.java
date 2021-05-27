package net.transolyfer.transolyfergo.domain.interactor;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.domain.callback.DetailOrderCallback;
import net.transolyfer.transolyfergo.domain.repository.DetailOrderRepository;

public class DetailOrderInteractor {

    private DetailOrderRepository detailOrderRepository;

    public DetailOrderInteractor(DetailOrderRepository detailOrderRepository) {
        this.detailOrderRepository = detailOrderRepository;
    }

    public void showDetail(OrderDetail orderDetail, final DetailOrderCallback detailOrderCallback) {
        detailOrderRepository.showDetailOrder(orderDetail, detailOrderCallback);
    }
}
