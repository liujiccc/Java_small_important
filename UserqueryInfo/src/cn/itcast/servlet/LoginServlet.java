package cn.itcast.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
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
 * @date 2020/11/29 12:55
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置request编码
        request.setCharacterEncoding("utf-8");
        //2.获取参数，这里的username是login.jsp页面标签设定的name属性，除了验证码
        //获取用户填写验证码
        String verifycode = request.getParameter("verifycode");


        Map<String, String[]> map = request.getParameterMap();
        User u=new User();

        try {
            BeanUtils.populate(u,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //4.调用Service查询
        UserService service=new UserServiceImpl();
        User loginUser = service.login(u);

        //5.验证码校验,先获取系统生成的验证码
        HttpSession session = request.getSession();
        //转为字符串(先把CheckCodeServlet的checkCode_session的session值拿过来比较)
        //系统生成的验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //删除session中存储的验证码(防止登录成功后返回上一级随便输入都能进)
        session.removeAttribute("CHECKCODE_SERVER");



        //4.先判断验证码是否正确，看你html写的验证码跟它生成的一样不？
        if (checkcode_server!=null && checkcode_server.equalsIgnoreCase(verifycode)){
            //6.判断是否登录成功,忽略大小写比较,验证码正确了
            if (loginUser !=null){
                //登录成功（账号密码正确，这里前提都是验证码正确了，因为上面如果验证码错误就会return停止，都不会来这代码了）
                //将用户存入session
                session.setAttribute("user",loginUser);
                //跳转页面,重定向到index.jsp
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }else {
                //登录失败，密码有误
                //提示信息
                //和验证码错误设一样的键

                request.setAttribute("login_msg","用户名或密码错误！");
                //跳转登录页面(转发)
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }else {
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误!");
            //跳转登录页面(转发)
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
