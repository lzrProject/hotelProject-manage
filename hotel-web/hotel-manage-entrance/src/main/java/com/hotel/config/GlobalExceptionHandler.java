package com.hotel.config;

import com.alibaba.fastjson.JSON;

import entity.Result;
import entity.StatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 全局异常处理类
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public void error404(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        if(ServletUtil.isAjax(httpServletRequest)){
            Result result = new Result(false, 404, "页面不存在");
            ServletUtil.write(JSON.toJSONString(result));
        }else{
            httpServletResponse.sendRedirect("http://localhost:8001/error/404");
        }

    }


}
