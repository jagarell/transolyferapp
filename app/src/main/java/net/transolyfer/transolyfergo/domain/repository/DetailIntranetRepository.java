package net.transolyfer.transolyfergo.domain.repository;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.domain.callback.DetailIntranetCallback;

public interface DetailIntranetRepository {
    void getEnterprises(DetailIntranetCallback detailIntranetCallback);
    void getDetailIntranet(OrderDetailIntranet orderDetailIntranet, DetailIntranetCallback detailIntranetCallback);
}
