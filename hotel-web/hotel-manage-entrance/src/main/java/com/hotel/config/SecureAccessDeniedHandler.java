package com.hotel.config;

import com.alibaba.fastjson.JSON;
import entity.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import servlet.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户暂无权限处理类
 * */
@Component
public class SecureAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        if(ServletUtil.isAjax(httpServletRequest)){
            Result result = new Result(false, 403, "暂无权限");
            ServletUtil.write(JSON.toJSONString(result));
        }else{
            httpServletResponse.sendRedirect("http://localhost:8001/error/403");
        }
    }
}
