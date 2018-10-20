package online.exam.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 11:19
 * @Author: Mr.Zhang
 * @Description:登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("loginTeacher") != null) {
            return true;
        } else {
            //------------日志
            System.out.println("检测到未登录访问后台内容操作");
            //如果没有登录，跳转至登录页面
            response.sendRedirect("admin/login.jsp");

            return false;
        }
    }
}
