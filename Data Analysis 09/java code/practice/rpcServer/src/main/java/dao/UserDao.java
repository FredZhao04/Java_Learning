package dao;

import model.User;

/**
 * Created by fred on 2018/6/10.
 */
public interface UserDao {
    /**
     * 通过用户名查找，并返回User
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * 保存到数据库
     */
    boolean save(User user);
}
