'''
1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
'''
import pandas as pd

def getHttpData(httpPath):
    phone_list = []
    url_list = []
    up_stream_list = []
    down_stream_list = []
    with open(httpPath, 'r') as f:
        for line in f.readlines():
            phone = line.split('\t')[0].strip()
            url = line.split('\t')[1].split(' ')[0].split('//')[1].split('/')[0].strip()
            up_stream = line.split('\t')[1].split(' ')[1].strip()
            down_stream = line.split('\t')[1].split(' ')[2].strip()
            phone_list.append(phone)
            url_list.append(url)
            up_stream_list.append(up_stream)
            down_stream_list.append(down_stream)

    dic = {'phone_old' : phone_list, 'url' : url_list, 'up_stream' : up_stream_list, 'down_stream' : down_stream_list}

    df_http = pd.DataFrame(dic)
    return df_http


httpPath = 'Data/http.log'
phonePath = 'Data/phone.txt'
df_http = getHttpData(httpPath)
#获取phone.txt中的数据
df_phone = pd.read_table(phonePath, sep='\t')

#1.根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
#将string转为int
df_http['down_stream'] = df_http['down_stream'].astype('int')
df_http['up_stream'] = df_http['up_stream'].astype('int')
#group1 = (df_http['down_stream'] + df_http['up_stream']).groupby(df_http['url'])
#print(group1.sum().sort_values(ascending=False))

#2.根据给的手机号段归属地规则，计算出总流量最高的省份Top3
#截取phone的前7位数字
df_http['phone'] = df_http['phone_old'].str.slice(0, 7)
#phone列的号码转为int
df_http['phone'] = df_http['phone'].astype('int')
df_all = pd.merge(df_http, df_phone, how='left', on='phone')

#group2 = (df_all['down_stream'] + df_all['up_stream']).groupby(df_all['province'])
#print(group2.sum().sort_values(ascending=False))

#3.根据给的手机号段运营商规则，计算出总流量最高的运营商Top3
#group3 = (df_all['down_stream'] + df_all['up_stream']).groupby(df_all['isp'])
#print(group3.sum().sort_values(ascending=False))

#4.根据给的用户上网日志记录数据，计算出总流量最高的手机号Top3
#group4 = (df_http['down_stream'] + df_http['up_stream']).groupby(df_all['phone_old'])
#print(group4.sum().sort_values(ascending=False))

#5.根据给的手机号段归属地规则，计算出总流量最高的市Top3
group5 = (df_all['down_stream'] + df_all['up_stream']).groupby(df_all['city'])
print(group5.sum().sort_values(ascending=False))






