import ch.hsr.geohash.GeoHash;
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.*;
import java.time.chrono.IsoChronology;
import java.util.List;
import java.util.Map;

/**
 * Created by fred on 2018/6/7.
 */
public class AnalysisMain {
    public static void main(String[] args){
        //加载自行车位置对照表
        Map<String, String> map = LoadBikeMapInfo.map;

        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/bikes.log"));
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/fred/Desktop/Data/repository.tsv", true));
        ){
            String line;
            while((line = br.readLine()) != null){
                LocalBean localBean = JSON.parseObject(line, LocalBean.class);
                String base32 = GeoHash.withCharacterPrecision(Double.parseDouble(localBean.getLatitude()), Double.parseDouble(localBean.getLongitude()), 8).toBase32();
                //先在本地库里找
                String result = findLocalMap(base32, map);
                if(result == null){
                    result = findNet(localBean.getLatitude(), localBean.getLongitude());

                }
                if(result != null){
                    bw.write(result);
                    bw.newLine();
                    bw.flush();

                    System.out.println(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String findNet(String latitude, String longitude) throws IOException {
        HttpClient client = new HttpClient();
        String url = "http://gc.ditu.aliyun.com/regeocoding?l=" + latitude + "," + longitude + "&type=010";
        HttpMethod method = new GetMethod(url);
        client.executeMethod(method);
        String s = method.getResponseBodyAsString();
        LocationBean bean = JSON.parseObject(s, LocationBean.class);
        String address = bean.getAddrList()[0].getName();
        String[] split = bean.getAddrList()[0].getAdmName().split(",");
        String string = null;
        if(split.length > 2){
            String province = split[0];
            String city = split[1];
            String district = split[2];
            String base32 = GeoHash.withCharacterPrecision(Double.parseDouble(latitude), Double.parseDouble(longitude), 8).toBase32();
            string = base32 + "\t" + province + "\t" + city + "\t" + district + "\t" + address;

        }
        return string;
    }

    private static String findLocalMap(String position, Map<String, String> map) {
        String s = map.get(position);
        return s;
    }
}
