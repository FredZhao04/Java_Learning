import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fred on 2018/6/11.
 */
public class WordCount {
    public static void main(String[] args){
        StringBuffer sb = new StringBuffer();
        Map<String, Integer> map = new HashMap<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/fred/Desktop/Data/speaking.txt"), "UTF-8"));
                ){
            String line;
            while((line = br.readLine()) != null){
                Pattern p = Pattern.compile("\\b\\w+\\b");
                Matcher matcher = p.matcher(line);
                while(matcher.find()){
                    String word = matcher.group();
                    Integer value = map.getOrDefault(word, 0);
                    value++;
                    map.put(word, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<String> keySet = map.keySet();
        for(String key:keySet){
            sb.append(key + "=" + map.get(key) + "\n");
        }
        System.out.println(sb);
    }
}
