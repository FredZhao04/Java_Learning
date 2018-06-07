import pandas as pd
import requests


#df = pd.read_csv('Data/bj-poi-1.csv', encoding='gbk')

#根据经纬度获取详细地址信息
def getPositionInfo(latitude, longitude):
    url = "http://gc.ditu.aliyun.com/regeocoding?l=" + latitude + "," + longitude + "&type=010";
    res = requests.get(url)
    try:
        result = res.json()
        return result
    except:
        print('error')
        return None

#json格式
#bike_info = getPositionInfo(latitude, longitude)
#获取并整理想要的地址信息
def getJsonInfo(bike_info):
    address = bike_info['addrList'][0]['name']
    split = bike_info['addrList'][0]['admName'].split(',')
    if(len(split) > 2):
        province = split[0]
        city = split[1]
        district = split[2]
        s = province + '\t' + city + '\t' + district + '\t' + address
        print(s)

with open('/Users/fred/Desktop/Data/bikes.log', 'r') as f:
    for line in f.readlines():
        try:
            latitude = line.split(',')[5].split(':')[1]
            longitude = line.split(',')[4].split(':')[1]
            result = getPositionInfo(latitude, longitude)
            if(result != None):
                getJsonInfo(result)
        except:
            print(('error'))