package cn.itcast.servlet;

import cn.itcast.service.ProvinceService;
import cn.itcast.service.impl.ProvinceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/12/9 9:31
 */
@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         /* //1.调用service查询，这种是没有用缓存的方式
        ProvinceService service = new ProvinceServiceImpl();
        List<Province> list = service.findAll();
        //2.序列化list为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);*/

        //1.调用service查询
        ProvinceService service=new ProvinceServiceImpl();
        String json = service.findAllJson();

        System.out.println("servlet中的:"+json);
        //3.响应结果
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
