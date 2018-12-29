package com.test.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2017-10-10.
 */
public class Interceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
          HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
      throws Exception {
      Boolean flag = true;
      String servletPath = httpServletRequest.getServletPath();
      System.out.println(servletPath);
      //智慧DM登录用户信息
      //EmployeeLoginDTO user = (EmployeeLoginDTO) httpServletRequest.getSession().getAttribute("user");
      //平乐和中国区登录用户
      //PlUserDTO zgqUser = (PlUserDTO) httpServletRequest.getSession().getAttribute("zgqUser");
      
      //两个都为空表示未登录
//      if (user==null && zgqUser==null) {
//          httpServletResponse.sendRedirect("home");
//          flag = false;
//      }else {
//          flag = true;
//      }
//      return flag;
      return true;
  }

  @Override
  public void postHandle(
          HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
          ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(
          HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
          Exception e) throws Exception {

  }
}
