package cn.itcast.servlet;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/30 12:08
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //1.获取参数,从list.jsp获取的
        String currentPage=request.getParameter("currentPage");
        String rows=request.getParameter("rows");

        //防止刚进来首页时没值
        if (currentPage==null ||"".equals(currentPage)){
            currentPage="1";
        }
        if (rows==null || "".equals(rows)){
            rows="5";
        }

        //获取复杂条件查询的参数
        Map<String, String[]> condition = request.getParameterMap();


        //2.调用service查询
        UserService service=new UserServiceImpl();
        PageBean<User> pb=service.findUserByPage(currentPage,rows,condition);

//        //为最后一页点下一页出现异常做准备
//        if (Integer.parseInt(currentPage)>=pb.getTotalPage()+1){
//            currentPage= String.valueOf(pb.getTotalPage());
//        }

        System.out.println("pb:"+pb);
        //3.将PageBean存入request，也将condition存入，才可以复杂查询后还回显这些数据再那个搜索框中
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);


        //4.转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
