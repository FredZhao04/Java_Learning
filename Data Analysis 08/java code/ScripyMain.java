import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fred on 2018/6/8.
 */
public class ScripyMain {

    public static List<Bean> list = new ArrayList<>();

    public static void main(String[] args){
        String urlPath = "https://search.51job.com/list/090200,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        getInfo(urlPath);
        for(Bean b:list){
            System.out.println(b);
        }
    }

    public static void getInfo(String urlPath){
        Document document = connect(urlPath);
        //select后面添加not()，过滤掉标题行
        Elements elements = document.select(".dw_table .el").not(".title");
        for (Element element: elements) {
            String jobTitle = element.select(".t1 span a").get(0).attr("title");
            String company = element.select(".t2 a").get(0).attr("title");
            String location = element.select(".t3").text();
            String salary = element.select(".t4").text();
            String date = element.select(".t5").text();
            //将获取的信息封装到list中
            Bean b= new Bean(jobTitle, company, location, salary, date);
            list.add(b);
        }
        //获取下一页的url
        Elements select = document.select(".bk").get(1).select("a");
        if(select != null && select.size() > 0){
            String nextUrl = select.attr("href");
            //暂停1秒钟，防止被网站屏蔽
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getInfo(nextUrl);
        }else {
            System.out.println("最后一页了");
        }
    }

    //获取url页面信息
    public static Document connect(String urlPath){
        Document document = null;
        try {
            URL url = new URL(urlPath);
            document = Jsoup.parse(url, 4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
