package src.main.java;

import model.Request;
import model.ResultSet;
import model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by fred on 2018/6/10.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 9999);

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

        Request request = new Request();
        request.setClassName("service.impl.UserServiceImpl");
        request.setMethodName("login");
        request.setParameterType(new Class[] {User.class});

        User user = new User("fred", "123", null);
        request.setParameterValues(new Object[] {user});

        oos.writeObject(request);

        ResultSet resultSet = (ResultSet)ois.readObject();
        System.out.println(resultSet.isFlag());
        System.out.println(resultSet.getDescription());

        ois.close();
        oos.close();
        s.close();
    }
}
