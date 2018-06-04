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
public class HttpAnalysisMain {
    public static void main(String[] args){
        List<HttpBean> httpList = getHttpList();
        List<PhoneBean> phoneList = getPhoneList();
        //1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
        //getHighUrl(httpList);
        //2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
        //getProvinceByStream(httpList, phoneList);
        //3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
        //getVendorByStream(httpList, phoneList);
        //4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
        //getHighPhone(httpList);
        //5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
        getCityByStream(httpList, phoneList);
    }
    //5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
    public static void getCityByStream(List<HttpBean> httpList, List<PhoneBean> phoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            PhoneBean pb = getBeanByPhone(hb.getPhone(), phoneList);
            Long value = map.getOrDefault(pb.getCity(), 0L);
            value += hb.getUp() + hb.getDown();
            map.put(pb.getCity(), value);
        }
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        for(int i = 0; i < Math.min(3, arrayList.size()); i++){
            System.out.println(arrayList.get(i));
        }
    }

    //4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
    public static void getHighPhone(List<HttpBean> list){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:list){
            String phone = hb.getPhone();
            Long value = map.getOrDefault(phone, 0L);
            value += hb.getUp() + hb.getDown();
            map.put(phone, value);
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
        for(int i = 0; i < Math.min(3, arrayList.size()); i++){
            System.out.println(arrayList.get(i));
        }
    }

    //3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
    public static void getVendorByStream(List<HttpBean> httpList, List<PhoneBean> phoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            PhoneBean pb = getBeanByPhone(hb.getPhone(), phoneList);
            Long value = map.getOrDefault(pb.getVendor(), 0L);
            value += hb.getUp() + hb.getDown();
            map.put(pb.getVendor(), value);
        }
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        for(int i = 0; i < Math.min(3, arrayList.size()); i++){
            System.out.println(arrayList.get(i));
        }
    }

    //2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
    public static void getProvinceByStream(List<HttpBean> httpList, List<PhoneBean> phoneList){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:httpList){
            PhoneBean pb = getBeanByPhone(hb.getPhone(), phoneList);
            Long value = map.getOrDefault(pb.getProvince(), 0L);
            value += hb.getUp() + hb.getDown();
            map.put(pb.getProvince(), value);
        }
        Set<Map.Entry<String, Long>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });
        for(int i = 0; i < Math.min(3, arrayList.size()); i++){
            System.out.println(arrayList.get(i));
        }
    }

    //二分法查找
    public static PhoneBean getBeanByPhone(String phone, List<PhoneBean> phoneList){
        int low = 0;
        int high = phoneList.size() - 1;
        int mid = 0;
        while(low <= high){
            mid = (low + high) / 2;
            if(phone.substring(0, 7).equals(phoneList.get(mid).getPhone())){
                return phoneList.get(mid);
            }else if(phone.substring(0, 7).compareTo(phoneList.get(mid).getPhone()) > 0){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return null;
    }

    //1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
    public static void getHighUrl(List<HttpBean> list){
        Map<String, Long> map = new HashMap<>();
        for(HttpBean hb:list){
            String url = hb.getUrl();
            Long value = map.getOrDefault(url, 0L);
            value += hb.getUp() + hb.getDown();
            map.put(url, value);
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
        for (Map.Entry<String, Long> item:arrayList) {
            System.out.println(item);
        }
    }

    public static List<PhoneBean> getPhoneList(){
        List<PhoneBean> list = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/phone.txt"));
                ){
            String line;
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

    public static List<HttpBean> getHttpList(){
        List<HttpBean> list = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/http.log"));
                ){
            String line;
            while((line = br.readLine()) != null){
                String phone = line.split("\t")[0];
                String url = line.split("\t")[1].split(" ")[0].split("//")[1].split("/")[0];
                long up = Long.parseLong(line.split("\t")[1].split(" ")[1]);
                long down = Long.parseLong(line.split("\t")[1].split(" ")[2]);
                HttpBean hb = new HttpBean(phone, url, up, down);
                list.add(hb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
