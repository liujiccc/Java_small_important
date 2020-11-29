package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/28 10:05
 */
public class UserDaoImpl implements UserDao{
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
    /**
     * @description:  如果查到就返回一个User对象的列表
     * @param: * @param:
     * @return: java.util.List<cn.itcast.domain.User>
     * @author QLBF
     * @date: 2020/11/28 10:09
     */
        //使用JDBC操作数据库...
        //1.定义sql，若sql查不到就会抛出异常，不用担心
        String sql="select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        /** 
         * @description: 登录验证，看密码是否正确 查到就返回，查不到就返回null
         * @param: * @param: username
 * @param: password 
         * @return: cn.itcast.domain.User 
         * @author QLBF
         * @date: 2020/11/29 11:09
         */ 
        try{
            String sql="select * from user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        //添加联系人
        //定义sql，id，密码和用户名先设置为null
        String sql="insert into user values(null,?,?,?,?,?,?,null,null)";
        //执行sql，下面的get是对应实体类的
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }

    @Override
    public void delete(int id) {
        //删除一行
        String sql="delete from user where id=?";
        template.update(sql,id);
    }

    @Override
    public User findById(int id) {
        //修改一行时，回显要用的
        String sql="select * from user where id=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void update(User user) {
        //修改一行时，提交时用
        String sql="update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id=?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }
}
