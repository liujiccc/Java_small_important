package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        //根据删除一行
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

    @Override
    public int findTotalCount(Map<String, String[]> condition) {

        //1.定义模板初始化sql,查询数据库总的记录条数，分页时要用+复杂查询也要用，所以得用动态sql
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        System.out.println("StringbUILDER中的sb :"+sb);//select count(*) from user where 1 = 1
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合,不然等下value没发取
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数，因为findUserByPageServlet通过getParamerter也会获取currentPage和rows，这里写sql，所以要疲敝掉
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];//以为getparamater返回的是string[],所以每个输入框返回[0]
            //System.out.println("value数组："+condition.get(key));//value数组：[Ljava.lang.String;@303b36f1
                                                        /*            value数组：[Ljava.lang.String;@3efcde86
                                                                    value数组：[Ljava.lang.String;@3ac35d81*/
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值,动态地添加到sql中，记得加个空格，否则很容易sql连在一起就不好
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        //System.out.println("sb的tostring："+sb.toString());//sb的tostring：select count(*) from user where 1 = 1  and name like ?  and address like ?
        System.out.println("params的值:"+params);//params的值:[%李%, %陕西%]
        System.out.println("params的toarray:"+params.toArray());//params的toarray:[Ljava.lang.Object;@7fc1d4bd

        //因为queryForObject后面要的是数组，所以这里要params.toArray()转为数组
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());


    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {

        String sql = "select * from user  where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数，因为findUserByPageServlet通过getParamerter也会获取currentPage和rows，这里写sql，所以要疲敝掉
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值 动态地添加到sql中，记得加个空格，否则很容易sql连在一起就不好
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        System.out.println("下面的sql没转前:"+sql);//select * from user  where 1 = 1
        sql = sb.toString();//Stringbuild转为string要加tostring的
        //System.out.println("下面的sql:"+sql);//select * from user  where 1 = 1  limit ?,?
        //System.out.println("下面的参数:"+params);//[%李%, %陕西%, 0, 5]


        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}
