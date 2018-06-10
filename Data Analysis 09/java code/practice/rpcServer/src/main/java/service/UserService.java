package service;

import model.ResultSet;
import model.User;

/**
 * Created by fred on 2018/6/10.
 */
public interface UserService {
    /**
     * 登陆
     * @param user
     * @return
     */
    ResultSet login(User user);
    /**
     * 注册
     */
    ResultSet register(User user);
}
