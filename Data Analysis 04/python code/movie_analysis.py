'''
1.每个用户评分最高的3部电影评分信息
2.每个用户的uid和评分的平均值
3.最大方(评分平均值高)的3个用户的uid和评分平均值
4.最热门的3部电影id和评价次数
5.评价最高的3部电影id和评分均值
'''
import pandas as pd

dic = {}
movie_list = []
rate_list = []
uid_list = []
with open('Data/rating.txt', 'r') as f:
   for line in f.readlines():
       movie = line.split(',')[0].replace('"', '').replace('{', '').split(':')[1].strip()
       rate = line.split(',')[1].replace('"', '').split(':')[1].strip()
       uid = line.split(',')[3].replace('"', '').replace('}', '').split(':')[1].strip()
       movie_list.append(movie)
       rate_list.append(int(rate))
       uid_list.append(uid)


dic = {'movie' : movie_list, 'rate' : rate_list, 'uid' : uid_list}
df = pd.DataFrame(dic)
#1.每个用户评分最高的3部电影评分信息
df_sort_by_rate = df.groupby(df['uid']).apply(lambda x: x.sort_values('rate', ascending=False))
print(df_sort_by_rate)

#2.每个用户的uid和评分的平均值
grouped = df['rate'].groupby(df['uid'])
print(grouped.mean())

#3.最大方(评分平均值高)的3个用户的uid和评分平均值
grouped_mean = df['rate'].groupby(df['uid']).mean()
print(grouped_mean.sort_values(ascending=False))

#4.最热门的3部电影id和评价次数
group_movie_size = df.groupby(df['movie']).size()
print(group_movie_size.sort_values(ascending=False))

#5.评价最高的3部电影id和评分均值
group_movie_rate_mean = df['rate'].groupby(df['movie']).mean()
print(group_movie_rate_mean.sort_values(ascending=False))


