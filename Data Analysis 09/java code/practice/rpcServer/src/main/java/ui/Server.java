package ui;

import model.Request;
import model.ResultSet;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fred on 2018/6/10.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(9999);
        Socket s = ss.accept();
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

        Request request = (Request)ois.readObject();

        String className = request.getClassName();
        String methodName = request.getMethodName();
        Class[] parameterType = request.getParameterType();
        Object[] parameterValues = request.getParameterValues();

        Class<?> c = Class.forName(className);
        Object o = c.newInstance();
        Method method = c.getMethod(methodName, parameterType);
        ResultSet resultSet = (ResultSet)method.invoke(o, (User) parameterValues[0]);

        oos.writeObject(resultSet);
        oos.close();
        ois.close();
    }
}
