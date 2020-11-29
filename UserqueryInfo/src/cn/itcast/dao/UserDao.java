package cn.itcast.dao;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/28 10:04
 */

import cn.itcast.domain.User;

import java.util.List;

/**
 * 用户操作的DAO
 */
public interface UserDao {
    public List<User> findAll();
    User findUserByUsernameAndPassword(String username,String password);
}
