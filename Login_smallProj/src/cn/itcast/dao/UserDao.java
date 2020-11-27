package cn.itcast.dao;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/11/23 10:38
 */

import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 操作数据库中User表的类
 */
public class UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 登录方法
     * @param loginUser 只有用户名和密码
     * @return user包含用户全部数据,没有查询到，返回null给loginservlet进行逻辑处理
     */
    public User login(User loginUser){
        try {
            //下面sql中的username和password对应你的数据库的表列名
            String sql="select * from user where username=? and password=?";
            //2.调用query方法,不懂可以看之前的博客template
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(), loginUser.getPassword());
            return user;
        }catch (DataAccessException e) {
            e.printStackTrace();//记录日志
            return null;
        }
    }

}
