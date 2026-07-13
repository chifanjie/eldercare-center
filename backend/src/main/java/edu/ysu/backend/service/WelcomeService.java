package edu.ysu.backend.service;

import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Welcome;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WelcomeService {

    Result sendCaptcha(String email);

    Result userRegister(Welcome welcome);

    Result userLogin(Welcome welcome);

    Result isCountExist(Welcome welcome);

    Result forgetPassword(Welcome welcome);



}
