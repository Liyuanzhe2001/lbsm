package com.lyz.filter;

import com.lyz.pojo.User;
import com.lyz.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class uploadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //判断user是否上传过excel
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        Short isUpload = user.getIsUpload();

        if (isUpload == 1) {
            filterChain.doFilter(request, response);
        } else {
            //跳转至上传页面
            response.sendRedirect(request.getContextPath() + "/home/toUpload");
        }

    }

    @Override
    public void destroy() {

    }
}
