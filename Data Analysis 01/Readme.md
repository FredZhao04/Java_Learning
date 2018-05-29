数据样例：

```
用户：好友列表（使用逗号分隔）
涛哥:杨哥,远哥,帅帅,叮叮,星哥,小鹏鹏,娜姐
杨哥:涛哥,远哥,星哥,辉哥,杨哥
远哥:叮叮,涛哥,帅帅,行哥
帅帅:涛哥,星哥,叮叮,侃哥
星哥:杨哥,远哥,帅帅,凯哥,侃哥
叮叮:涛哥,杨哥,远哥,帅帅,星哥,小鹏鹏,凯哥
马哥:涛哥,远哥,帅帅,星哥,叮叮
娜姐:涛哥,远哥,帅帅,星哥,小鹏鹏
行哥:涛哥,小鹏鹏
马大姐:杨哥,小鹏鹏
辉哥:涛哥,远哥,帅帅
侃哥:帅帅,星哥,叮叮
凯哥:星哥,叮叮,马哥
小鹏鹏:涛哥,娜姐,行哥,马大姐
国家:涛哥
```

需求：

```
需求1：各个用户（user）的好友数量并排序（top3）

需求2：两两之间的共同好友
```

需求1 分析：

```
1、读取数据
2、数据切分：
	使用冒号（：）切分，第一部分是用户，第二部分是朋友列表，用逗号切分
3、封装到map中，用户为key，朋友列表中朋友的个数为value
4、map排序的实现：
	转成List进行排序
5、将结果输出保存到特定的文件地址
```

###需求1 细分过程：
###1、读取数据

```
import java.io.BufferedReader;
import java.io.FileReader;

public class TestMain4 {

	public static void main(String[] args) {
		
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/friends.txt"));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
在这个过程中，我们看到数据样本中的数据都已经按照要求输出。
###2、数据切分
```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class TestMain4 {

	public static void main(String[] args) {
		
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/friends.txt"));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				System.out.println(usr + ":" + Arrays.asList(friends));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
###3、封装到map中
该过程将切分所得到的数据封装到map中，用户为key，朋友个数为value

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestMain4 {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader("/Users/fred/Desktop/Data/friends.txt"));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				//System.out.println(usr + ":" + Arrays.asList(friends));
				map.put(usr, friends.length);
			}
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
为了便于代码清晰规范，我们将上述过程封装成一个方法getMap()。

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		Map<String, Integer> map = getMap(path);
	}
	
	public static Map<String, Integer> getMap(String path){
		Map<String, Integer> map = new HashMap<>();
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader(path));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				//System.out.println(usr + ":" + Arrays.asList(friends));
				map.put(usr, friends.length);
			}
			//System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
```
###4、map排序
我们知道treemap是默认对key值排序，而对value排序需要将其转换成List，这就要使用到:

```
Set<Entry<String,Integer>> entrySet = map.entrySet();
List<Entry<String,Integer>> list = new ArrayList<>(entrySet);
```
然后使用Collections.sort对list进行排序。

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		Map<String, Integer> map = getMap(path);
		//将map的key和value转成list
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		List<Entry<String,Integer>> list = new ArrayList<>(entrySet);
		//对list进行排序
		Collections.sort(list, new Comparator<Entry<String,Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
			
		});
		System.out.println(list);
	}
	
	public static Map<String, Integer> getMap(String path){
		Map<String, Integer> map = new HashMap<>();
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader(path));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				//System.out.println(usr + ":" + Arrays.asList(friends));
				map.put(usr, friends.length);
			}
			//System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
```
###输出并保存结果

```
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		Map<String, Integer> map = getMap(path);
		//将map的key和value转成list
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		List<Entry<String,Integer>> list = new ArrayList<>(entrySet);
		//对list进行排序
		Collections.sort(list, new Comparator<Entry<String,Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
			
		});
		System.out.println(list);
		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/fred/Desktop/Data/friends_count.txt"));	
				){
			for(Entry<String,Integer> li : list) {
				bw.write(li.toString());
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Integer> getMap(String path){
		Map<String, Integer> map = new HashMap<>();
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader(path));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				//System.out.println(usr + ":" + Arrays.asList(friends));
				map.put(usr, friends.length);
			}
			//System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
```
为便于代码清晰阅读，我们也将上述过程封装成writeToFile方法：

```
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		String outputPath = "/Users/fred/Desktop/Data/friends_count.txt";
		Map<String, Integer> map = getMap(path);
		writeToFile(map, outputPath);
	}
	
	public static void writeToFile(Map<String, Integer> map, String path) {
		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(path));	
				){
			//将map的key和value转成list
			Set<Entry<String,Integer>> entrySet = map.entrySet();
			List<Entry<String,Integer>> list = new ArrayList<>(entrySet);
			//对list进行排序
			Collections.sort(list, new Comparator<Entry<String,Integer>>(){

				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					return o2.getValue() - o1.getValue();
				}
				
			});
			
			for(Entry<String,Integer> li : list) {
				bw.write(li.toString());
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Integer> getMap(String path){
		Map<String, Integer> map = new HashMap<>();
		try (
				//读数据
				BufferedReader br = new BufferedReader(new FileReader(path));
				){
			String line = null;
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				String usr = line.split(":")[0]; //用户名
				String[] friends = line.split(":")[1].split(","); //朋友列表
				//System.out.println(usr + ":" + Arrays.asList(friends));
				map.put(usr, friends.length);
			}
			//System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
```

###需求2 细分过程：
```
1、读取数据
2、数据切分
	对每行数据以冒号切分，第一部分是用户，第二部分再用逗号切分是朋友列表
3、封装到map中，Map<String, List<String>>
4、将用户两两比较，求其value的交集
5、保存结果
```

###读取数据、切分数据、封装到map中

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		String outputPath = "/Users/fred/Desktop/Data/friends_count.txt";
		
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
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
###4、求用户交集

```
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TestMain4 {

	public static void main(String[] args) {
		String path = "/Users/fred/Desktop/Data/friends.txt";
		String outputPath = "/Users/fred/Desktop/Data/friends_count.txt";
		Map<String, List<String>> map = getMap(path);
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
					System.out.println(newList);	
				}
			}
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
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
```
###5、保存结果

```
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
```


