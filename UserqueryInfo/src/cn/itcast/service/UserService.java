package cn.itcast.service;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/28 10:14
 */

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

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

    /**
     * @description:  添加联系人
     * @param: * @param: null
     * @return:
     * @author QLBF
     * @date: 2020/11/29 17:05
     */
    void addUser(User user);


    //删除一行
    void deleteUser(String id);

    //修改一行时，回显要用的
    User findUserById(String id);

    //修改一行时，提交时用
    void updateUser(User user);

    //删除选中
    void delSelectedUser(String[] ids);

    /**
     * 分页条件查询
     * @param
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
