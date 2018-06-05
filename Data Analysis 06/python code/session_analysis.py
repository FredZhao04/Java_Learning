'''
1、需要为从访问日志中梳理出每一个session（如果一个用户两次相邻请求之间的时间差<30分钟，
则该两次请求都属于同一个session，否则分属不同的session），并为session中的历次请求打上序号

2、将每次session进行汇总，得出用户每次session的浏览起、止页面，每次session会话总时长等
'''
import re
import datetime
import pandas as pd
import time
import uuid

ip_pattern = re.compile(r'(\d+\.){3}\d+')
date_pattern = re.compile(r'([\[]{1}).+(]{1})')
url_pattern = re.compile(r'(GET|POST){1}\s(\S)*\s')
ip_list = []
date_list = []
url_list = []
with open('Data/access.log.fensi', 'r') as f:
    for line in f.readlines():
        ip = ip_pattern.match(line).group(0)
        date_str = date_pattern.search(line).group(0).replace('[', '').replace(']', '').split(' ')[0]
        date = datetime.datetime.strptime(date_str, '%d/%b/%Y:%H:%M:%S')
        url_search = url_pattern.search(line)
        if(url_search != None):
            url = url_search.group(0)
        else:
            url = None
        ip_list.append(ip)
        date_list.append(date)
        url_list.append(url)

df = pd.DataFrame({'ip' : ip_list, 'date' : date_list, 'url' : url_list})

#组内排序并打上序号
df['order'] = df['date'].groupby(df['ip']).rank(ascending=0,method='dense')
#先按照ip排序，讲相同ip集中在一起
df = df.sort_values('ip', ascending=True)
#datetime转化时间戳
ds = []
for d in df['date']:
    ds.append(time.mktime(d.timetuple()))
df['new_date'] = ds

#分组操作
for name, group in df.groupby('ip'):
    print(name)
    #上下两行相减
    temp = group.sort_values('date', ascending=True)
    temp['new_date_1'] = temp['new_date'].shift(1)
    temp['diff'] = temp['new_date'] - temp['new_date_1']
    temp = temp.fillna(0)
    #生成随机数
    u_id = uuid.uuid4()
    temp['session_id'] = temp['diff'].apply(lambda x: u_id if x < 1800 else uuid.uuid4())
    #print(temp)
    print('-------------------------------------------')
    #每个session的起止时间间隔
    #print(temp.groupby('session_id')['new_date'].max() - temp.groupby('session_id')['new_date'].min())
    #每个session开始的url
    print(temp.groupby('session_id')['url'].apply(lambda i:i.iloc[0]))
    #每个session结束的url
    print(temp.groupby('session_id')['url'].apply(lambda i: i.iloc[-1]))




