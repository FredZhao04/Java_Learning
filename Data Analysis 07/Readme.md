##共享单车GeoHash定位案例
###知识要点
###乱码问题
1、如果读出来的数据有乱码，说明编码格式不是默认的utf8，如果进行编码转换：

```
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/fred/Desktop/Data/bj-poi-1.csv"), "gbk"));
               
```
2、GeoHash

利用GeoHash对经纬度进行解析，并转为32位字符串，以便于比较：

```
String base32 = GeoHash.withCharacterPrecision(Double.parseDouble(localBean.getLatitude()), Double.parseDouble(localBean.getLongitude()), 8).toBase32();
```
3、JSON数据解析

利用互联网获取经纬度所处地址信息，返回的是json数据，对其进行解析：

```
private static String findNet(String latitude, String longitude) throws IOException {
        HttpClient client = new HttpClient();
        String url = "http://gc.ditu.aliyun.com/regeocoding?l=" + latitude + "," + longitude + "&type=010";
        HttpMethod method = new GetMethod(url);
        client.executeMethod(method);
        String s = method.getResponseBodyAsString();
        LocationBean bean = JSON.parseObject(s, LocationBean.class);
        String address = bean.getAddrList()[0].getName();
        String[] split = bean.getAddrList()[0].getAdmName().split(",");
        String string = null;
        if(split.length > 2){
            String province = split[0];
            String city = split[1];
            String district = split[2];
            String base32 = GeoHash.withCharacterPrecision(Double.parseDouble(latitude), Double.parseDouble(longitude), 8).toBase32();
            string = base32 + "\t" + province + "\t" + city + "\t" + district + "\t" + address;

        }
        return string;
    }
```

单词：longitude（经度），latitude（维度）
数据：
  1：单车信息数据，
2：北京市的poi位置信息.
需求需求和流程分析
根据单车信息数据（经纬度信息），确定单车位置

  使用GeoHash算法将poi中的数据转换成对应的geoHash值对应地理位置，
获取所有的单车数据的经纬度对应的地理位置，先从本地的地理仓库位置中获取数据,找到匹配的数据返回，如果没有数据，去官网地图获取数据，将数据存储在本地的地理位置库中
知识点：
json数据解析，HttpClient基本使用，GeoHash原理和简单使用 字符串切割 子串 数据转换

GeoHash算法基本原理
GeoHash算法的步骤
下面以北海公园为例介绍GeoHash算法的计算步骤
根据经纬度计算GeoHash二进制编码(逼近思想)
地球纬度区间是[-90,90]， 北海公园的纬度是39.928167，可以通过下面算法对纬度39.928167进行逼近编码:

1）区间[-90,90]进行二分为[-90,0),[0,90]，称为左右区间，可以确定39.928167属于右区间[0,90]，给标记为1；

2）接着将区间[0,90]进行二分为 [0,45),[45,90]，可以确定39.928167属于左区间 [0,45)，给标记为0；

3）递归上述过程39.928167总是属于某个区间[a,b]。随着每次迭代区间[a,b]总在缩小，并越来越逼近39.928167；

4）如果给定的纬度x（39.928167）属于左区间，则记录0，如果属于右区间则记录1，这样随着算法的进行会产生一个序列1011100，序列的长度跟给定的区间划分次数有关。

组码

通过上述计算，纬度产生的编码为10111 00011，经度产生的编码为11010 01011。偶数位放经度，奇数位放纬度，把2串编码组合生成新串：11100 11101 00100 01111。
最后使用用0-9、b-z（去掉a, i, l, o）这32个字母进行base32编码，首先将11100 11101 00100 01111转成十进制，对应着28、29、4、15，十进制对应的编码就是wx4g。同理，将编码转换成经纬度的解码算法与之相反，具体不再赘述。

GeoHash Base32编码长度与精度 字符串
 xw9843shd   xw984nlj
 
可以看出，当geohash base32编码长度为8时，精度在19米左右，而当编码长度为9时，精度在2米左右，编码长度需要根据数据情况进行选择。


