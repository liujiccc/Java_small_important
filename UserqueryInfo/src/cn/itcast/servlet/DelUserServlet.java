package cn.itcast.servlet;

import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/29 19:10
 */
@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取list.jsp的id
        String id = request.getParameter("id");
        //2.调用service删除
        UserService service=new UserServiceImpl();
        service.deleteUser(id);

        //3.跳转到查询所有Servlet，重定向
        response.sendRedirect(request.getContextPath()+"/userListServlet");

        //这里因为没设置上面键值，所以用转发也行的
        //request.getRequestDispatcher(request.getContextPath()+"/userListServlet").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
