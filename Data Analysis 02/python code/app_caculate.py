
dic = {}
#读取文件内容
with open('Data/app.txt', 'r') as f:
    for line in f.readlines():
        key = line.split(',')[0] + ',' + line.split(',')[1] + ',' + line.split(',')[2] + ',' + line.split(',')[3]
        value = dic.get(key, [])
        value.append(line.split(',')[5].strip())
        dic[key] = value

##对版本排序，并获取有升级的键值对
new_dic = {}
for K, V in dic.items():
    V = list(set(V))
    V.sort()
    new_V = []
    size = len(V)
    if(size > 1):
        new_V.append(V[0])
        new_V.append(V[size - 1])
        new_dic[K] = new_V

##按照升级时间排序
result = sorted(new_dic.items(), key=lambda new_dic:new_dic[0])

#打印结果
for r in result:
    print(r)
