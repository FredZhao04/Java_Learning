给定的access.log是电信运营商的用户上网数据，第一个字段是时间，第二个字段是ip地址，
第三更字段是访问的网站，其他字段可以忽略不计。

ip.txt是ip地址和归属地的规则数据，里面的数据是根据ip地址的十进制从高到低排序。
第一个字段是网段的起始IP地址，第二个字段是网段的结束IP地址，
第三个字段是网段的起始IP地址对应的十进制，第四个字段是网段的结束IP地址对应的十进制，
第五个字段代表洲，第六个代表国家，第七个代表省，第八个代表城市，其他字段可以忽略不计。

要求：通过计算access.log中的用户行为数据，
统计出各个省份访问量（一次请求记作一次独立的访问量），
并按照各个省份的访问量的从高到低进行排序