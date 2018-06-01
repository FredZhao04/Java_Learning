package ip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ipCaculate {
	public static void main(String[] args) {
		String ipPath = "/Users/fred/Desktop/Data/ip.txt";
		String accessPath = "/Users/fred/Desktop/Data/access.log";
		
		List<IpBean> ipTable = getIp(ipPath);
		List<Long> ipList = getAccessLog(accessPath);
		
		Map<String, Integer> map = new HashMap<>();
		for(Long ip : ipList) {
			String province = getProvince(ip, ipTable);
			int count = map.getOrDefault(province, 0);
			count++;
			map.put(province, count);
		}
		
		//遍历输出
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			System.out.println(key + ":" + map.get(key));
		}	
	}
	
	//获取ip地址所对应的省份
	public static String getProvince(long ip, List<IpBean> ipTable) {
		int low = 0;
		int high = ipTable.size() -1;
		int mid = 0;
		while(low <= high) {
			mid = (high + low) / 2;
			if(ip >= ipTable.get(mid).getStartDecimalIp() && ip <= ipTable.get(mid).getEndDecimalIp()) {
				return ipTable.get(mid).getProvince();
			}else if(ip < ipTable.get(mid).getStartDecimalIp()){
				high = mid - 1;
			}else {
				low = mid + 1;
			}
		}
		return null;
	}
	
	/**
	 * 获取访问Ip的十进制地址，并放入list
	 * @return
	 */
	public static List<Long> getAccessLog(String accessPath) {
		List<Long> list = new ArrayList<>();
		try (
				BufferedReader br = new BufferedReader(new FileReader(accessPath));
				){
			String line;
			while((line = br.readLine()) != null) {
				String ipOld = line.split("\\|")[1];
				long ip = transformToDecimal(ipOld);
				list.add(ip);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static long transformToDecimal(String ip) {
		return (long)(Integer.parseInt(ip.split("\\.")[3]) 
				+ Integer.parseInt(ip.split("\\.")[2]) * 256 
				+ Integer.parseInt(ip.split("\\.")[1]) * Math.pow(256, 2) 
				+ Integer.parseInt(ip.split("\\.")[0]) * Math.pow(256, 3));
	}
	
	/**
	 * 将Ip对应表导入list，以便于之后对照查找
	 * @return
	 */
	public static List<IpBean> getIp(String ipPath) {
		List<IpBean> list = new ArrayList<>();
		try (
				BufferedReader br = new BufferedReader(new FileReader(ipPath));
				){
			String line;
			while((line = br.readLine()) != null) {
				String startIp = line.split("\\|")[0];
				String endIp = line.split("\\|")[1];
				long startDecimalIp = Long.parseLong(line.split("\\|")[2]);
				long endDecimalIp = Long.parseLong(line.split("\\|")[3]);
				String province = line.split("\\|")[6];
				String vendor = line.split("\\|")[9];
				list.add(new IpBean(startIp, endIp, startDecimalIp, endDecimalIp, province, vendor));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
