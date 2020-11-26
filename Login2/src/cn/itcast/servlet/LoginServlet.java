package cn.itcast.servlet;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/26 10:50
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置request编码
        request.setCharacterEncoding("utf-8");
        //2.获取参数，这里的username是jsp页面标签设定的name属性，除了验证码
        String checkCode = request.getParameter("checkCode");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");

        //3.封装user对象，替换上面的
        Map<String, String[]> map = request.getParameterMap();
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

        //3.先获取生成的验证码
        HttpSession session = request.getSession();
        //转为字符串(先把CheckCodeServlet的checkCode_session的session值拿过来比较)
        String checkCode_session = (String)session.getAttribute("checkCode_session");
        //删除session中存储的验证码(防止登录成功后返回上一级随便输入都能进),下面的checkCode_session是CheckCodeServlet的键，而不是上面的字符串
        session.removeAttribute("checkCode_session");
        //4.先判断验证码是否正确，看你html写的验证码跟它生成的一样不？
        if (checkCode_session!=null && checkCode_session.equalsIgnoreCase(checkCode)){
            //忽略大小写比较
            //验证码正确
            //判断用户名和密码是否一致
            if (login_flag!=null){
                //需要调用UserDao查询数据库
                //登录成功
                //存储信息，用户信息,因为是重定向是浏览器两次请求，所以得用session来存储
                //System.out.println("密码正确");
                session.setAttribute("user",login_flag.getUsername());
                //重定向到success.jsp，重定向就是一输入浏览器网址1到这就自动跳到网址2
                response.sendRedirect(request.getContextPath()+"/success.jsp");
                //System.out.println("跳转进成功页面!");
            }else {
                //登录失败
                //存储提示信息到request

                request.setAttribute("login_error","用户名或密码错误");
                //转发到登录页面
                request.getRequestDispatcher("/login.jsp").forward(request,response);
                //System.out.println("密码错误！！！重新跳转到登录页面");
            }
        }else {
            //验证码不一致
            //存储提示信息到request

            request.setAttribute("cc_error","验证码错误");
            //转发到登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
