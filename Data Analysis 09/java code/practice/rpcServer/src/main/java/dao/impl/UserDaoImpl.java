package dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import dao.UserDao;
import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.Utils;

import java.sql.SQLException;

/**
 * Created by fred on 2018/6/10.
 */
public class UserDaoImpl implements UserDao{

    @Override
    public User getUserByName(String username) {
        QueryRunner runner = Utils.getRunner();
        String sql = "select * from user where username = ?";
        try {
            User user = runner.query(sql, new BeanHandler<User>(User.class), username);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        QueryRunner runner = Utils.getRunner();
        String sql = "insert into user values(null, ?, ?, ?)";
        try {
            runner.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
