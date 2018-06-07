import ch.hsr.geohash.GeoHash;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fred on 2018/6/7.
 */
public class LoadBikeMapInfo {
    static Map<String, String> map = null;
    static {
        map = getBikeMap();
    }

    public static void main(String[] args){

    }

    public static Map<String, String> getBikeMap(){
        Map<String, String> map = new HashMap<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/fred/Desktop/Data/bj-poi-1.csv"), "gbk"));
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/fred/Desktop/Data/repository.tsv"));
                ){
            String line;
            while((line = br.readLine()) != null){
                String logitude = line.split(",")[2];
                String latitude = line.split(",")[3];
                String address = line.split(",")[4];//详细地址
                String city = line.split(",")[5];//市
                String district = line.split(",")[7];//区/县

                //将经纬度转成32位字符串
                String base32 = null;
                try {
                    base32 = GeoHash.withCharacterPrecision(Double.parseDouble(latitude), Double.parseDouble(logitude), 8).toBase32();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    continue;
                }

                //将地址组合起来
                String addr = base32 + "\t" + "北京" + "\t" + city + "\t" + district + "\t" + address;
                //将数据保存在文件中
                bw.write(addr);
                bw.newLine();
                bw.flush();
                //将上述信息封装到map中
                map.put(base32, addr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
