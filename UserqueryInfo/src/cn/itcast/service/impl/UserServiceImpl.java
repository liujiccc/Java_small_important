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

    /*public static void main(String[] args) {
        User u=new User();
        u.setUsername("zhangsan1");
        u.setPassword("123");

        UserServiceImpl t=new UserServiceImpl();
        User login = t.login(u);
        System.out.println(login);
    }*/
}
