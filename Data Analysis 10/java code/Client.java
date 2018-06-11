import java.io.*;
import java.net.Socket;

/**
 * Created by fred on 2018/6/11.
 */
public class Client {
    final static String KEY1 = "天王盖地虎";
    final static String KEY2 = "宝塔镇河妖";

    public static void main(String[] args){
        String path = "/Users/fred/Desktop/Data/p1.jar";
        String localPath = "/Users/fred/Desktop/practice.jar";
        try {
            //1、建立通信
            Socket s = new Socket("localhost", 9999);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            //2、身份验证
            oos.writeObject(KEY1);
            oos.flush();
            //接收返回密码信息
            String s1 = (String)ois.readObject();
            if(KEY2.equals((s1))){//密码正确
                //3、发送jar的路径
                oos.writeObject(path);
                oos.flush();
                //4、发送jar包
                //为什么用BufferedInputStream就不行呢？
                FileInputStream fis = new FileInputStream(localPath);
                byte[] b = new byte[1024];
                int len;
                while((len = fis.read(b)) != -1){
                    oos.write(b, 0, len);
                    oos.flush();
                }

                //5、发送执行命令
                String command = "java -jar /Users/fred/Desktop/Data/p1.jar";
                oos.writeObject(command);
                oos.flush();

                //6、接收服务端执行命令后返回的结果
                String result = (String)ois.readObject();
                System.out.println(result);
            }else {//密码错误
                System.out.println(s1 + "密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
