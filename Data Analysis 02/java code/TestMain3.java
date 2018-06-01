package com.study.xiaoniu.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestMain3 {

	public static void main(String[] args) throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "insert into app values(null, ?, ?, ?, ?, ?, ?)";
		
		Map<String, List<String>> map = getMap();
		//对map中的value进行排序
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			Collections.sort(map.get(key));
		}
		
		//按照时间进行排序
		ArrayList<String> arrayList = new ArrayList<String>(map.keySet());
		Collections.sort(arrayList);
		for(String item : arrayList) {
			map.get(item);
			//遍历输出
			int len = map.get(item).size();
			if(len > 1) {
				System.out.println(item.split(",")[0]);
				System.out.println(item + "," + map.get(item).get(0) + "," + map.get(item).get(len - 1));
				runner.update(sql, item.split(",")[0], item.split(",")[1], item.split(",")[2], item.split(",")[3], map.get(item).get(0), map.get(item).get(len - 1));
			}
		}
	}
	
	public static Map<String, List<String>> getMap(){
		Map<String, List<String>> map = new HashMap<>();
		try (
				BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/app1.txt"));
				){
			
			String line;
			while((line = br.readLine()) != null) {
				String date = line.split(",")[0];
				String userName = line.split(",")[1];
				String appName = line.split(",")[2];
				String source = line.split(",")[3];
				String version = line.split(",")[5];
				
				//将前四项合并组成一个String，作为map的key，version作为map的value的组成元素
				String key = date + "," + userName + "," + appName +"," + source;
				if(!map.containsKey(key)) {
					List<String> value = new ArrayList<>();
					value.add(version);
					map.put(key, value);
				}
				else {
					//剔除重复数据
					if(!map.get(key).contains(version)) {
						map.get(key).add(version);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
