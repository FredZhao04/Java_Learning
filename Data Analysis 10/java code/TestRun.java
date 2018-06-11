import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by fred on 2018/6/11.
 */
public class TestRun {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("java -jar /Users/fred/Desktop/Data/p1.jar");
        InputStream inputStream = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
    }
}
