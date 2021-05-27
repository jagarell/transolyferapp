package net.transolyfer.transolyfergo.domain.interactor;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.domain.callback.DetailIntranetCallback;
import net.transolyfer.transolyfergo.domain.repository.DetailIntranetRepository;

public class ReportIntranetInteractor {

    private DetailIntranetRepository detailIntranetRepository;

    public ReportIntranetInteractor(DetailIntranetRepository detailIntranetRepository) {
        this.detailIntranetRepository = detailIntranetRepository;
    }

    public void getEnterprises(final DetailIntranetCallback detailIntranetCallback) {
        detailIntranetRepository.getEnterprises(detailIntranetCallback);
    }

    public void getReportIntranet(OrderDetailIntranet orderDetailIntranet, final DetailIntranetCallback detailIntranetCallback) {
        detailIntranetRepository.getDetailIntranet(orderDetailIntranet, detailIntranetCallback);
    }
}
