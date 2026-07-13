package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CustomerService {

    Result getCustomerPaged(String keyword,String round,int pageNum,int pageSize);

    Result addCheckin(Customer customer);

    Result deleteCheckin(Customer customer);

    Result getCustomerPosition(Integer uid);

    Result changeCustomerCheckin(Customer customer);

    Result getCheckoutPaged(String keyword,int pageNum,int pageSize);

    Result addCheckoutRecord(Checkout checkout);

    Result deleteCheckout(Checkout checkout);

    Result approveCheckout(Checkout checkout);

    Result changeCheckout(Checkout checkout);

    Result getCustomerEgressPaged(String keyword,int pageNum,int pageSize);

    Result approveEgress(List<Egress> egresses);

    Result deleteEgress(Egress egress);

    Result addEgressRecord(Egress egress);

    Result changeEgress(Egress egress);

    Result addEgressReallyRecord(Egress egress);

    Result getCustomerInfoRecordPaged(String keyword,int pageNum,int pageSize);

    Result getFamilyRecordPaged(int uid,int pageNum);

    Result getEmergencyRecordPaged(int uid,int pageNum);

    Result getButlerRecordPaged(int uid,int pageNum);

    Result getSupportRecordPaged(int uid,int pageNum);

    Result addFamilyRecord(Family family);

    Result deleteFamilyRecord(Family family);

    Result changeFamilyRecord(Family family);

    Result addEmergencyRecord(Emergency emergency);

    Result deleteEmergencyRecord(Emergency emergency);

    Result changeEmergencyRecord(Emergency emergency);

    Result addButlerRecord(User user);
    Result addSupportRecord(User user);

    Result deleteButlerRecord(User user);

    Result deleteSupportRecord(User user);

}
