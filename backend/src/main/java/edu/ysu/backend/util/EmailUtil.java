package edu.ysu.backend.util;

import edu.ysu.backend.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Slf4j
@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String captchaServer;

    public Result captchaEmail(String email,String title,String content){
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try{
            helper = new MimeMessageHelper(message);
            helper.setFrom(captchaServer);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content,true);
        } catch (MessagingException e){
            log.error("Operation event recorded");
            return Result.fail("验证邮件发送失败!");
        }
        try{
            javaMailSender.send(message);
            log.info("Operation event recorded");
            return Result.ok("验证邮件发送成功!");
        }catch (Exception e){
            log.error("Operation event recorded");
            return Result.fail("验证邮件发送失败!");
        }
    }
}
