package net.transolyfer.transolyfergo.repository;

import net.transolyfer.transolyfergo.data.entity.EnterpriseEntity;
import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.data.mapper.EnterpriseListDataMapper;
import net.transolyfer.transolyfergo.domain.callback.DetailIntranetCallback;
import net.transolyfer.transolyfergo.domain.model.Enterprise;
import net.transolyfer.transolyfergo.domain.repository.DetailIntranetRepository;
import net.transolyfer.transolyfergo.repository.datasource.DetailIntranetDataStoreFactory;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkDetailIntranetDataStore;

import java.util.List;

public class DetailIntranetRepositoryImpl implements DetailIntranetRepository {

    private final DetailIntranetDataStoreFactory detailIntranetDataStoreFactory;
    private final EnterpriseListDataMapper enterpriseListDataMapper;

    public DetailIntranetRepositoryImpl(DetailIntranetDataStoreFactory detailIntranetDataStoreFactory, EnterpriseListDataMapper enterpriseListDataMapper) {
        this.detailIntranetDataStoreFactory = detailIntranetDataStoreFactory;
        this.enterpriseListDataMapper = enterpriseListDataMapper;
    }


    @Override
    public void getEnterprises(DetailIntranetCallback detailIntranetCallback) {
        final NetworkDetailIntranetDataStore networkDetailIntranetDataStore = this.detailIntranetDataStoreFactory.createSource();
        networkDetailIntranetDataStore.getEnterprises(new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                List<EnterpriseEntity> enterpriseEntityList = (List<EnterpriseEntity>) object;
                List<Enterprise> enterpriseList = enterpriseListDataMapper.transformEnterpriseList(enterpriseEntityList);
                detailIntranetCallback.onEnterpriseSuccess(enterpriseList);
            }

            @Override
            public void onFailure(Exception e) {
                detailIntranetCallback.onEnterpriseFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                detailIntranetCallback.onEnterpriseFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                detailIntranetCallback.onMessageError(message);
            }
        });
    }

    @Override
    public void getDetailIntranet(OrderDetailIntranet orderDetailIntranet, DetailIntranetCallback detailIntranetCallback) {
        final NetworkDetailIntranetDataStore networkDetailIntranetDataStore = this.detailIntranetDataStoreFactory.createSource();
        networkDetailIntranetDataStore.getDetailIntranet(orderDetailIntranet,new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                detailIntranetCallback.onReportSuccess(object);
            }

            @Override
            public void onFailure(Exception e) {
                detailIntranetCallback.onReportFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                detailIntranetCallback.onReportFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                detailIntranetCallback.onMessageError(message);
            }
        });
    }
}
