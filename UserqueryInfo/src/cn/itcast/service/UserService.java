package cn.itcast.service;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/28 10:14
 */

import cn.itcast.domain.User;

import java.util.List;

/**
 * 用户管理的业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 登录方法
     * @param user
     * @return
     */
    User login(User user);

}
