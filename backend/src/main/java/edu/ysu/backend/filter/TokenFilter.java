package edu.ysu.backend.filter;

import edu.ysu.backend.entity.pojo.OnlineContext;
import edu.ysu.backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TokenFilter implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        try {
            log.info("Operation event recorded");
            if (JwtUtil.isTokenExpired(token)){
                log.info("Operation event recorded");
                response.setStatus(401);
                return false;
            }else {
                String email = JwtUtil.parseJWT(token).getSubject();
                log.info("Operation event recorded");
                OnlineContext.setCurrent(token);
                return true;
            }
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
