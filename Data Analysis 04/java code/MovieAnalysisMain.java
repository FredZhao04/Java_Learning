import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * 	 1.每个用户评分最高的3部电影评分信息
     2.每个用户的uid和评分的平均值
     3.最大方(评分平均值高)的3个用户的uid和评分平均值
     4.最热门的3部电影id和评价次数
     5.评价最高的3部电影id和评分均值
 * Created by fred on 2018/6/3.
 */
public class MovieAnalysisMain {
    public static void main(String[] args){
        //1.每个用户评分最高的3部电影评分信息
        List<Bean> list = getMovieList();
        //getRateByUid(list);
        //2.每个用户的uid和评分的平均值
        //getAgerageByUid(list);
        //3.最大方(评分平均值高)的3个用户的uid和评分平均值
        //getHighAverageRateByUid(list);
        //4.最热门的3部电影id和评价次数
        //getHotMovie(list);
        //5.评价最高的3部电影id和评分均值
        getHotMovieRate(list);
    }

    //5.评价最高的3部电影id和评分均值
    public static void getHotMovieRate(List<Bean> list){
        Map<String, List<Double>> map = new HashMap<>();
        for(Bean item:list){
            List<Double> list1 = map.getOrDefault(item.getMovie(), new ArrayList<Double>());
            list1.add(item.getRate());
            map.put(item.getMovie(), list1);
        }

        Map<String, Double> newMap = new HashMap<>();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            Double average = newMap.getOrDefault(key, 0.0);
            average = getAverageOfList(map.get(key));
            newMap.put(key, average);
        }

        //按照平均排序
        Set<Map.Entry<String, Double>> entrySet = newMap.entrySet();
        ArrayList<Map.Entry<String, Double>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });

        //打印输出
        for(Map.Entry<String, Double> item : arrayList){
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }

    //4.最热门的3部电影id和评价次数
    public static void getHotMovie(List<Bean> list){
        Map<String, Integer> map = new HashMap<>();
        for(Bean item:list){
            Integer count = map.getOrDefault(item.getMovie(), 0);
            count++;
            map.put(item.getMovie(), count);
        }

        //按照评论次数排序
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Integer>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        //打印输出
        for(Map.Entry<String, Integer> item:arrayList){
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }

    //3.最大方(评分平均值高)的3个用户的uid和评分平均值
    public static void getHighAverageRateByUid(List<Bean> list){
        Map<String, List<Double>> map = new HashMap<>();
        for(Bean item:list){
            List<Double> list1 = map.getOrDefault(item.getUid(), new ArrayList<Double>());
            list1.add(item.getRate());
            map.put(item.getUid(), list1);
        }

        Map<String, Double> newMap = new HashMap<>();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            Double value = newMap.getOrDefault(key, 0.0);
            value = getAverageOfList(map.get(key));
            newMap.put(key, value);
        }

        //对newMap按照评分排序
        Set<Map.Entry<String, Double>> entrySet = newMap.entrySet();
        ArrayList<Map.Entry<String, Double>> arrayList = new ArrayList<>(entrySet);
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue() > o1.getValue() ? 1 : -1;
            }
        });

        for(Map.Entry<String, Double> item : arrayList){
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }

    //2.每个用户的uid和评分的平均值
    public static void getAgerageByUid(List<Bean> list){
        Map<String, List<Double>> map = new HashMap<>();
        for(Bean item:list){
            List<Double> list1 = map.getOrDefault(item.getUid(), new ArrayList<Double>());
            list1.add(item.getRate());
            map.put(item.getUid(), list1);
        }

        Map<String, Double> newMap = new HashMap<>();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            Double value = newMap.getOrDefault(key, 0.0);
            value = getAverageOfList(map.get(key));
            newMap.put(key, value);
        }
        //打印输出
        for(String key:keySet){
            System.out.println(key + "  " + newMap.get(key));
        }
    }

    //计算list的平均值
    public static double getAverageOfList(List<Double> list){
        double sum = 0.0;
        for(Double value:list){
            sum += value;
        }
        return list.size() == 0 ? 0 : sum / list.size();
    }

    //1.每个用户评分最高的3部电影评分信息
    public static void getRateByUid(List<Bean> list){
        Map<String, List<Bean>> map = new HashMap<>();
        Collections.sort(list, new Comparator<Bean>() {
            @Override
            public int compare(Bean o1, Bean o2) {
                return o1.getUid().compareTo(o2.getUid()) == 0 ? (int) Math.ceil(o2.getRate() - o1.getRate()) : o1.getUid().compareTo(o2.getUid());
            }
        });
        for(Bean item:list){
            List<Bean> list1 = map.getOrDefault(item.getUid(), new ArrayList<Bean>());
            if(list1.size() < 3){
                list1.add(item);
            }
            map.put(item.getUid(), list1);
        }
        //打印输出
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            System.out.println(key + " " + map.get(key));
        }
    }

    //解析json文件，获取movie信息
    public static List<Bean> getMovieList(){
        List<Bean> list = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/rating.txt"));
                ){
           String line;
           while((line = br.readLine()) != null){
               Bean bean = JSON.parseObject(line, Bean.class);
               list.add(bean);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
