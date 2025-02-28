package cn.itcast.web.servlet;

import cn.itcast.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/23 11:43
 */
@WebServlet("/succ")
public class SuccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取request域中共享的user对象,得对应LoginServlet的键，向下转型，原本是object的
        User user = (User) request.getAttribute("user");

        if (user!=null){
            //给页面写一句话
            //设置编码
            response.setContentType("text/html;charset=utf-8");
            //输出
            response.getWriter().write("登录成功！"+user.getUsername()+",欢迎您");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
