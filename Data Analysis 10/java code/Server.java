import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fred on 2018/6/11.
 */
public class Server {
    final static String KEY1 = "天王盖地虎";
    final static String KEY2 = "宝塔镇河妖";
    public static void main(String[] args){
        try {
            //1、建立通信
            ServerSocket ss = new ServerSocket(8888);
            while (true){
                Socket s = ss.accept();
                //2、身份验证
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                String s1 = (String)ois.readObject();
                if(KEY1.equals(s1)){//暗号对，发送回复密码
                    oos.writeObject(KEY2);
                    oos.flush();

                    //3、接收jar存放的路径
                    String path = (String)ois.readObject();

                    //4、接收jar包
                    BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path));
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = ois.read(b)) != -1){
                        fos.write(b, 0, len);
                        fos.flush();
                    }

                    //5、接收命令
                    String command = (String)ois.readObject();
                    System.out.println(command);

                    //6、执行命令
                    Runtime runtime = Runtime.getRuntime();
                    Process process = runtime.exec(command);
                    InputStream inputStream = process.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuffer sb = new StringBuffer();
                    String line;
                    while((line = br.readLine()) != null){
                        sb.append(line + "\n");
                    }

                    //7、返回结果
                    oos.writeObject(sb.toString());
                    oos.flush();

                }else {//暗号不对
                    oos.writeObject("你是谁？我不认识你");
                    oos.flush();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
