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
}
