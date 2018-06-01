package com.study.xiaoniu.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TestMain4 {

	public static void main(String[] args) {
		Map<App, List<String>> map = getMap();
		Set<App> keySet = map.keySet();
		//对版本version进行排序
		for(App key : keySet) {
			Collections.sort(map.get(key));
		}
	
		//遍历输出
		for(App item : keySet) {
			int len = map.get(item).size();
			if(len > 1) {
				System.out.println(item + "=" + map.get(item).get(0) + "," + map.get(item).get(len - 1));
			}
		}
		
	}
	
	public static Map<App, List<String>> getMap() {
		Map<App, List<String>> map = new TreeMap<>();
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
				
				App key = new App(date, userName, appName, source);
				
				if(!map.containsKey(key)) {
					List<String> list = new ArrayList<>();
					list.add(version);
					map.put(key, list);
				}else {
					if(!map.get(key).contains(version))
						map.get(key).add(version);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
