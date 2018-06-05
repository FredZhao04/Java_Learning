import javax.print.DocFlavor;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fred on 2018/6/5.
 */
public class SessionAnalysisMain {
    public static void main(String[] args){
        Map<String, List<SessionBean>> map = getMap();
        getSession(map);
        //第一问打印输出
        Set<String> keySet = map.keySet();
        /*for(String key:keySet){
            List<SessionBean> beanList = map.get(key);
            for(SessionBean bean:beanList){
                if(bean.getUrl() != null){
                    System.out.println(key + ":" +bean);
                }
            }
        }*/
        //2、将每次session进行汇总，得出用户每次session的浏览起、止页面，每次session会话总时长等
        Map<String, List<SessionBean>> sessionMap = new HashMap<>();
        for(String key : keySet){
            List<SessionBean> beanList = map.get(key);
            for(SessionBean bean:beanList){
                List<SessionBean> beans = sessionMap.getOrDefault(bean.getSessionId(), new ArrayList<SessionBean>());
                beans.add(bean);
                sessionMap.put(bean.getSessionId(), beans);
            }
        }

        //打印输出
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/fred/Desktop/Data/sessionOutput.txt"));)
            {
                Set<String> keySet1 = sessionMap.keySet();
                for(String key:keySet1){
                    SessionBean firstBean = sessionMap.get(key).get(0);
                    SessionBean endBean = sessionMap.get(key).get(sessionMap.get(key).size() - 1);
                    String s = key + " : " + firstBean.getIp() + " : " + dateToString(firstBean.getDate()) + " : " + dateToString(endBean.getDate()) + " : " + firstBean.getUrl() + " : " + endBean.getUrl() + " : " + (endBean.getDate().getTime() - firstBean.getDate().getTime());
                    System.out.println(s);
                    bw.write(s);
                    bw.newLine();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //修改时间显示的格式
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //1、需要为从访问日志中梳理出每一个session（如果一个用户两次相邻请求之间的时间差<30分钟，则该两次请求都属于同一个session，
    //否则分属不同的session），并为session中的历次请求打上序号
    public static void getSession(Map<String, List<SessionBean>> map){
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            List<SessionBean> beanList = map.get(key);
            if(beanList.size() == 1){
                String sessionId = UUID.randomUUID().toString();
                beanList.get(0).setSessionId(sessionId);
                beanList.get(0).setOrder(1);
            }else {
                for(int i = 0; i < beanList.size() - 1; i++){
                    boolean flag = isSameSession(beanList.get(i), beanList.get(i + 1));
                    if(flag){
                        if(beanList.get(i).getSessionId() == null){
                            String sessionId = UUID.randomUUID().toString();
                            beanList.get(i).setSessionId(sessionId);
                            beanList.get(i).setOrder(1);
                            beanList.get(i + 1).setSessionId(sessionId);
                            beanList.get(i + 1).setOrder(2);
                        }else {
                            beanList.get(i + 1).setSessionId(beanList.get(i).getSessionId());
                            beanList.get(i + 1).setOrder(beanList.get(i).getOrder() + 1);
                        }
                    }

                }
            }
        }
    }

    //判断是否是同一个session
    public static boolean isSameSession(SessionBean bean1, SessionBean bean2){
        if(bean2.getDate().getTime() - bean1.getDate().getTime() <= 1800000){
            return true;
        }else {
            return false;
        }
    }

    //获取数据，并封装到map
    public static Map<String, List<SessionBean>> getMap(){
        Map<String, List<SessionBean>> map = new HashMap<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/access.log.fensi"));
        ){
            String line;
            while((line = br.readLine()) != null){
                String ipRegex = "(\\d+\\.){3}\\d+";
                String dateRegex = "\\[.+\\d+\\]";
                String urlRegex = "(POST|GET){1}\\s(\\S)*\\s";

                String ip = getDataByRegex(line, ipRegex);
                String d = getDataByRegex(line, dateRegex);
                String url = getDataByRegex(line, urlRegex);
                Date date = parseStringDate(d);//转成Date格式
                SessionBean sb = new SessionBean(null, ip, date, url, 0);

                List<SessionBean> list = map.getOrDefault(ip, new ArrayList<SessionBean>());
                list.add(sb);
                map.put(ip, list);
            }
            //对map进行排序，按照时间先后顺序
            Set<String> keySet = map.keySet();
            for(String key:keySet){
                List<SessionBean> list = map.get(key);
                Collections.sort(list, new Comparator<SessionBean>() {
                    @Override
                    public int compare(SessionBean o1, SessionBean o2) {
                        return o1.getDate().after(o2.getDate()) ? 1 : -1;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    //字符串转时间Date
    public static Date parseStringDate(String date){
        Date date1 = null;
        String d = date.substring(1, date.length() - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyy:hh:mm:ss");
        try {
            date1 = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
    //数据切分
    public static String getDataByRegex(String line, String regex){
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(line);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
