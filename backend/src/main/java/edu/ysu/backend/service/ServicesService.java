package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Service;
import edu.ysu.backend.entity.vo.Staff;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ServicesService {

    Result getServiceCustomerPaged(String keyword,int pageNum,int pageSize);

    Result getServicePaged(String keyword,int pageNum,int pageSize);

    Result addServicePurchaseRecord(Service service);

    Result alertServiceRecord(Service service);

    Result addServiceRecordPurchaseRecord(Service service);

    Result cleanServiceAlert();

    Result getHealthCareData(Integer uid);

    Result getCustomerRecord(Integer uid);

    Result getServiceSetPaged(String keyword,int pageNum,int pageSize);

    Result deleteServiceSet(Service service);

    Result getServiceSetInfo(Integer sid);

    Result changeServiceSet(Staff staff);


}
