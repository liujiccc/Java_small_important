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

    //添加联系人
    void add(User user);

    //根据id删除一行
    void delete(int id);

    //修改一行时，回显要用的
    User findById(int id);


    //修改一行时，提交时用
    void update(User user);

}
