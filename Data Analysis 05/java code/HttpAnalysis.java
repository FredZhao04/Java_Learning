import sun.awt.SunGraphicsCallback;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 *   1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
     2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
     3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
     4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
     5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
 * Created by fred on 2018/6/4.
 */
public class HttpAnalysis {
    public static void main(String[] args){
        List<HttpBean> httpList = getHttpList();
        //1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
        //getHighStreamWeb(list);
        //2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
        List<PhoneBean> phoneList = getPhoneList();
        //getProvinceByPhone(httpList, phoneList);
        //3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
        //getVendorByPhone(httpList, phoneList);
        //4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
        //getPhoneByStream(httpList);
        //5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
        getCityByStream(httpList, phoneList);
    }
    //5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
    public static void getCityByStream(List<HttpBean> httpList, List<PhoneBean> PhoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            String phone = hb.getPhone().substring(0, 7);
            PhoneBean pb = binarySearch(phone, PhoneList);
            Long value = map.getOrDefault(pb.getCity(), 0L);
            value += hb.getUpStream() + hb.getDownStream();
            map.put(pb.getCity(), value);
        }
        //按照流量排序
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });

        //打印输出
        for(int i = 0; i < 3; i++){
            System.out.println(arrayList.get(i));
        }
    }

    //4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
    public static void getPhoneByStream(List<HttpBean> httpList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            Long value = map.getOrDefault(hb.getPhone(), 0L);
            value = value + hb.getUpStream() + hb.getDownStream();
            map.put(hb.getPhone(), value);
        }
        //按照访问流量排序
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        //打印输出
        for(int i = 0; i < Math.min(3, arrayList.size()); i++){
            System.out.println(arrayList.get(i));
        }

    }

    //3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
    public static void getVendorByPhone(List<HttpBean> httpList, List<PhoneBean> PhoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            String phone = hb.getPhone().substring(0, 7);
            PhoneBean pb = binarySearch(phone, PhoneList);
            Long value = map.getOrDefault(pb.getVendor(), 0L);
            value += hb.getUpStream() + hb.getDownStream();
            map.put(pb.getVendor(), value);
        }

        //按照流量进行排序
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        //打印输出
        for(Map.Entry<String, Long> item:arrayList){
            System.out.println(item.getKey() + " : " + item.getValue());
        }
    }

    //2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
    public static void getProvinceByPhone(List<HttpBean> httpList, List<PhoneBean> PhoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            String phone = hb.getPhone().substring(0, 7);
            PhoneBean pb = binarySearch(phone, PhoneList);
            Long value = map.getOrDefault(pb.getProvince(), 0L);
            value += hb.getUpStream() + hb.getDownStream();
            map.put(pb.getProvince(), value);
        }

        //按照流量进行排序
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        //打印输出
        for(Map.Entry<String, Long> item:arrayList){
            System.out.println(item.getKey() + " : " + item.getValue());
        }
    }

    //二分法查找，可以提高效率
    public static PhoneBean binarySearch(String phone, List<PhoneBean> list){
        int low = 0;
        int high = list.size() - 1;
        int mid = 0;
        while(low <= high){
            mid = (low + high) / 2;
            if(phone.compareTo(list.get(mid).getPhone()) == 0){
                return list.get(mid);
            } else if(phone.compareTo(list.get(mid).getPhone()) > 0){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return null;
    }

    //获取phone.txt数据，并封装到PhoneBean中，并放入list
    public static List<PhoneBean> getPhoneList(){
        List<PhoneBean> list = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/phone.txt"));
        ){
            String line;
            //不封装第一行的标题段
            br.readLine();
            while((line = br.readLine()) != null){
                String phone = line.split("\t")[1];
                String province = line.split("\t")[2];
                String city = line.split("\t")[3];
                String vendor = line.split("\t")[4];
                PhoneBean pb = new PhoneBean(phone, province, city, vendor);
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
    public static void getHighStreamWeb(List<HttpBean> list){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:list){
            Long value = map.getOrDefault(hb.getUrl(), 0L);
            value += hb.getUpStream() + hb.getDownStream();
            map.put(hb.getUrl(), value);
        }
        //按照访问流量排序
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        //打印输出
        for(Map.Entry<String, Long> item:arrayList){
            System.out.println(item.getKey() + "    " + item.getValue());
        }

    }

    //获取http.log中数据，并封装到bean中，加载到list
    public static List<HttpBean> getHttpList(){
        List<HttpBean> list = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/http.log"));
        ){
            String line;
            while((line = br.readLine()) != null){
                String phone = line.split("\t")[0];
                String url = line.split("\t")[1].split(" ")[0].split("//")[1].split("/")[0];
                long upStream = Long.parseLong(line.split("\t")[1].split(" ")[1]);
                long downStream = Long.parseLong(line.split("\t")[1].split(" ")[2]);
                HttpBean hb = new HttpBean(phone, url, upStream, downStream);
                list.add(hb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
