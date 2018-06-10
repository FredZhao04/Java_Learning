package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.ResultSet;
import model.User;
import service.UserService;

/**
 * Created by fred on 2018/6/10.
 */
public class UserServiceImpl implements UserService{

    @Override
    public ResultSet login(User user) {
        UserDao userDao = new UserDaoImpl();
        User userByName = userDao.getUserByName(user.getUsername());
        ResultSet resultSet = new ResultSet();
        if(user.getUsername().equals(userByName.getUsername())){
            if(user.getPassword().equals(userByName.getPassword())){
                resultSet.setFlag(true);
                resultSet.setDescription("登陆成功");
            }else {
                resultSet.setFlag(false);
                resultSet.setDescription("用户名或密码不正确");
            }
        }else {
            resultSet.setFlag(false);
            resultSet.setDescription("用户名不存在");
        }
        return resultSet;
    }

    @Override
    public ResultSet register(User user) {
        UserDao userDao = new UserDaoImpl();
        ResultSet resultSet = new ResultSet();
        User userByName = userDao.getUserByName(user.getUsername());
        if(userByName == null){
            boolean b = userDao.save(user);
            if(b){
                resultSet.setFlag(true);
                resultSet.setDescription("注册成功");
            }else {
                resultSet.setFlag(false);
                resultSet.setDescription("未知错误");
            }
        }else {
            resultSet.setFlag(false);
            resultSet.setDescription("用户名已存在");
        }
        return resultSet;
    }
}
