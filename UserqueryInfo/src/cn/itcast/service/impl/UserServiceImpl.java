package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/28 10:15
 */
public class UserServiceImpl implements UserService{
    private UserDao dao=new UserDaoImpl();


    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {
        /**
         * @description: 调用dao层的 findUserByUsernameAndPassword登录验证账号密码
         * @param: * @param: user
         * @return: cn.itcast.domain.User
         * @author QLBF
         * @date: 2020/11/29 12:52
         */
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void addUser(User user) {
        /**
         * @description: 添加联系人
         * @param: * @param: user
         * @return: void
         * @author QLBF
         * @date: 2020/11/29 17:05
         */

        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        //删除一行，因为实体类的id是包装类，所以这里要把String转为int
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        //修改一行时，回显要用的
        return dao.findById(Integer.parseInt(id));

    }

    @Override
    public void updateUser(User user) {
        //修改一行时，提交时用
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        /**
         * @description: 删除选中
         * @param: * @param: ids
         * @return: void
         * @author QLBF
         * @date: 2020/11/30 8:49
         */
        //防止空指针异常
        if (ids!=null&&ids.length>0){
            //遍历数组
            for (String id : ids) {
                //调用dao的删除方法
                dao.delete(Integer.parseInt(id));
            }
        }
    }



    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        //分页查询，在这里封装PageBean对象
        //把string转为int
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);

        //为第一页点上一页出现异常做准备
        if (currentPage<=0){
            currentPage=1;
        }


        //1.创建空的PageBean对象
        PageBean<User> pb=new PageBean<User>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.调用dao查询总记录数
        int totalCount=dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        //4.调用dao查询的List集合+分页
        //计算开始的记录索引
        int start=(currentPage-1)*rows;
        List<User> list=dao.findByPage(start,rows,condition);
        pb.setList(list);


        //5.计算总页码
        int totalPage=(totalCount % rows)==0?totalCount/rows:(totalCount/rows)+1;
        pb.setTotalPage(totalPage);

        //为最后一页点下一页出现异常做准备
//        if (currentPage>=pb.getTotalPage()+1){
//            currentPage=pb.getTotalPage();
//        }

        return pb;

    }

    /*public static void main(String[] args) {
        User u=new User();
        u.setUsername("zhangsan1");
        u.setPassword("123");

        UserServiceImpl t=new UserServiceImpl();
        User login = t.login(u);
        System.out.println(login);
    }*/
}
