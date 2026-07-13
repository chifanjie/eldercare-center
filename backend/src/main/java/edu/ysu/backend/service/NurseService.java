package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Transactional
public interface NurseService {

    Result getNurseLevelPaged(String keyword,int pageNum,int pageSize);

    Result getNurseContentPaged(String keyword,int pageNum,int pageSize);

    Result getNurseContentSetPaged(String keyword,int pageNum,int pageSize);

    Result addNewNurseLevel(Level level);

    Result changeNurseLevel(Level level);

    Result deleteNurseLevel(Level level);

    Result addNewNurseContent(Content content);

    Result changeNurseContent(Content content);

    Result deleteNurseContent(Content content);

    Result addNurseContentSet(LevelContent levelContent);

    Result deleteNurseContentSet(LevelContent levelContent);

    Result changeNurseContentSet(LevelContent levelContent);

    Result getCustomerPaged(String keyword,int pageNum,int pageSize);

    Result getNurseRecordPaged(String keyword,int pageNum,int pageSize);

    Result addTempNurseRecord(NurseRecord nurseRecord);

    void requestRecordSheet(HttpServletResponse response, String begin, String end);

    Result addGroupNurseRecord(NurseRecord nurseRecord);

    Result getCustomerBuyServicePaged(String keyword, int pageNum, int pageSize);

    Result deleteCustomerBuyService(Service service);

    Result changeCustomerBuyService(Service service);
}
