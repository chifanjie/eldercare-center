package edu.ysu.backend.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RandomCaptcha {
    public static String randomCode(){
        Random random=new Random();
        StringBuilder captcha= new StringBuilder();
        for (int i=0;i<6;i++){
            captcha.append(random.nextInt(10));
        }
        log.info("Operation event recorded");
        return captcha.toString();
    }
}
