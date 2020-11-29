package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;

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

    /*public static void main(String[] args) {
        User u=new User();
        u.setUsername("zhangsan1");
        u.setPassword("123");

        UserServiceImpl t=new UserServiceImpl();
        User login = t.login(u);
        System.out.println(login);
    }*/
}
