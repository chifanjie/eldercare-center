package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Exchange;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BedService {

    Result getBedRecordPaged(String keyword,int pageNum,int pageSize);

    Result getCustomerBedRecord(String uid);

    Result exchangeCustomerBedRecord(Exchange exchange);

    Result resetCustomerRecord(Exchange exchange);

    Result getBedDesignRecord(String round,String keyword);

    Result getBedCustomer(Integer uid);

}
