package com.study.xiaoniu.social;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 输出两个人的共同好友
 * @author fred
 *
 */
public class TestMain3 {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		writeToFile(getMap(), "/Users/fred/Desktop/Data/friends_sort_cout.txt");
	}
	
	public static Map<String, List<String>> getMap(){
		Map<String, List<String>> map = new HashMap<>();
		try (
				BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/friends.txt"));	
				){
			String line = null;
			while((line = br.readLine()) != null) {
				String usr = line.split(":")[0];
				String[] friends = line.split(":")[1].split(",");
				map.put(usr, Arrays.asList(friends));  //aslist转化的list不可增删
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void writeToFile(Map<String, List<String>> map, String path) {
		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(path));
				){
			Set<String> keySet = map.keySet();
			//将用户转换为List，方便之后以下标遍历
			ArrayList<String> list = new ArrayList<String>(keySet);
			for(int i = 0; i < list.size() - 1; i++) {
				String usrI = list.get(i);
				for(int j = i + 1; j < list.size(); j++) {
					String usrJ = list.get(j);
					@SuppressWarnings("unchecked")
					ArrayList<String> arrayList = new ArrayList<>(map.get(usrI));
					arrayList.retainAll(map.get(usrJ)); //取交集
					if(arrayList != null && arrayList.size() > 0) {
						String str = usrI + "和" + usrJ + "的共同好友：" + arrayList.toString();
						System.out.println(str);
						bw.write(str);
						bw.newLine();
						bw.flush();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
