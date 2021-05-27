package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

public interface NetworkDetailIntranetDataStore {
    void getEnterprises(RepositoryCallback repositoryCallback);
    void getDetailIntranet(OrderDetailIntranet detailIntranet, RepositoryCallback repositoryCallback);
}
