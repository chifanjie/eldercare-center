package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MenueService {
    Result getMenue();

    Result roleMenue();

    Result getTreeMenue();

    Result getNurseMenue();

    Result getBuildingMenue();

    Result getCustomerMenue(String keyword);

    Result getStaffMenue(String keyword);

    Result getTagMenue();

    Result getTypeMenue();

    Result getMealMenue();

    Result getMealsTagMenue();

    Result getLevelMenue();

    Result getButlerMenue(String keyword);

    Result getSupportMenue(String keyword);

    Result getServiceMenue(String keyword);

    Result customCustomerServiceMenue(String keyword);

}
