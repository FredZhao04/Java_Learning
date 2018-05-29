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


public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		String outputPath = "/Users/fred/Desktop/Data/friends_shared.txt";
		Map<String, List<String>> map = getMap(path);
		writeToFile(map, outputPath);
		
	}
	
	public static void writeToMysql(Map<String, List<String>> map) {
		
	}
	
	public static void writeToFile(Map<String, List<String>> map, String path) {
		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(path));	
				){
			//获取所有的用户，以便于比较
			Set<String> keySet = map.keySet();
			//将keySet转成list以便于利用下标进行比较
			ArrayList<String> list = new ArrayList<>(keySet);
			for(int i = 0; i < list.size() -1; i++) {
				String usrI = list.get(i); //第i个用户
				for(int j = i + 1; j < list.size(); j++) {
					String usrJ = list.get(j); //第j个用户
					//map.get(usrI).retainAll(map.get(usrJ)); //不能利用该方法求交集，因为添加到map的朋友列表list是以asList转化的，而通过asList取得的list不能增删
					List<String> newList = new ArrayList<>(map.get(usrI));
					newList.retainAll(map.get(usrJ));
					if(newList != null && newList.size() > 0) {	
						bw.write(usrI + "和" + usrJ + "的共同好友是: " + newList.toString());
						bw.newLine();
						bw.flush();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static Map<String, List<String>> getMap(String path){
		Map<String, List<String>> map = new HashMap<>();
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader(path));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				map.put(usr, Arrays.asList(friends));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
