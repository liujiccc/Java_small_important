package cn.itcast.web.servlet;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/23 11:01
 */
//这里注解的loginServlet要和html跳转的名要一样哦
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
//        //2.获取请求参数
//        //这里getParameter都是根据html的name值的
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        //3.封装user对象
//        User loginUser = new User();
//        loginUser.setUsername(username);
//        loginUser.setPassword(password);


        //下面代码就是替换上面的
        Map<String, String[]> map = request.getParameterMap();
        //3.封装user对象
        User loginUser = new User();
        //3.2使用BeanUtils封装
        try {
            BeanUtils.populate(loginUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //4.调用UserDao的login方法
        UserDao dao=new UserDao();
        User login_flag = dao.login(loginUser);

        //5.判断login_flag如果查到就是用户名和密码正确才会返回login_flag对象，login查不到就返回null的

        if (login_flag==null){
            //登录失败
            request.getRequestDispatcher("/fail").forward(request,response);

        }else {
            //登录成功
            //存储数据,传个login_flag对象给它，succ怎么处理是它的事
            request.setAttribute("user",login_flag);
            //转发
            request.getRequestDispatcher("/succ").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}