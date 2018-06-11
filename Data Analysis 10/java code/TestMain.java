import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by fred on 2018/6/11.
 */
public class TestMain implements Callable<String>{
    final static String KEY1 = "天王盖地虎";
    final static String KEY2 = "宝塔镇河妖";

    private String path;
    private String localPath;
    private String command;

    public TestMain(String path, String localPath, String command) {
        this.path = path;
        this.localPath = localPath;
        this.command = command;
    }

    public TestMain() {
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String path = "/Users/fred/Desktop/Data/p1.jar";
        String localPath = "/Users/fred/Desktop/practice.jar";
        String command = "java -jar /Users/fred/Desktop/Data/p1.jar";

        String path2 = "/Users/fred/Desktop/Data/p2.jar";
        String localPath2 = "/Users/fred/Desktop/practice.jar";
        String command2 = "java -jar /Users/fred/Desktop/Data/p2.jar";

        TestMain t1 = new TestMain(path, localPath, command);
        FutureTask<String> f1 = new FutureTask<String>(t1);
        Thread tt1 = new Thread(f1);
        tt1.start();

        TestMain t2 = new TestMain(path2, localPath2, command2);
        FutureTask<String> f2 = new FutureTask<String>(t2);
        Thread tt2 = new Thread(f2);
        tt2.start();

        String s1 = f1.get();
        System.out.println(s1);
        String s2 = f2.get();
        System.out.println("----------------");
        System.out.println(s2);
    }

    @Override
    public String call() throws Exception {
        String result = null;
        try {
            //1、建立通信
            Socket s = new Socket("localhost", 8888);
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
                BufferedInputStream fis = new BufferedInputStream(new FileInputStream(localPath));
                byte[] b = new byte[1024];
                int len;
                while((len = fis.read(b)) != -1){
                    oos.write(b, 0, len);
                    oos.flush();
                }

                //5、发送执行命令
                oos.writeObject(command);
                oos.flush();

                //6、接收服务端执行命令后返回的结果
                result = (String)ois.readObject();
                //System.out.println(result);
            }else {//密码错误
                System.out.println(s1 + "密码错误");
            }

            s.shutdownInput();
            s.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
