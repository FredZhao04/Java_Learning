#获取access.log中的ip地址信息
def getIpList():
    ip_list = []
    with open('Data/access.log', 'rb') as f:
        lines = [l.decode('utf8', 'ignore') for l in f.readlines()]
        for line in lines:
            #获取IP
            ip_list.append(line.split('|')[1])
    return ip_list

#将access.log中的ip地址转换成十进制ip
def transformToDecimal(ipList):
    ip_decimal_list = []
    for ip in ipList:
        #print(int(ip.split('.')[0]) * pow(256, 3) + int(ip.split('.')[1]) * pow(256, 2) + int(ip.split('.')[2]) * 256 + int(ip.split('.')[3]))
        ip_decimal_list.append(int(ip.split('.')[0]) * pow(256, 3) + int(ip.split('.')[1]) * pow(256, 2) + int(ip.split('.')[2]) * 256 + int(ip.split('.')[3]))
    return ip_decimal_list

#获取ip.txt中ip段的起始地址及对应的省份
def getIpTable():
    ip_table = []
    with open('Data/ip.txt') as f:
        for line in f.readlines():
            ip_info = []
            ip_info.append(line.split('|')[2])
            ip_info.append(line.split('|')[3])
            ip_info.append(line.split('|')[6])

            ip_table.append(ip_info)
    return ip_table

def getResult(ip_decimal_list, ip_table):
    result = {}
    for ip in ip_decimal_list:
        low = 0
        high = len(ip_table) - 1
        while (low <= high):
            mid = int((low + high) / 2)
            if ((int(ip) >= int(ip_table[mid][0])) & (int(ip) <= int(ip_table[mid][1]))):
                count = result.get(ip_table[mid][2], 0)
                count += 1
                result[ip_table[mid][2]] = count
                break
            elif (int(ip) < int(ip_table[mid][0])):
                high = mid - 1
            else:
                low = mid + 1
    return result

if __name__ == '__main__':

    ipList = getIpList()
    ip_decimal_list = transformToDecimal(ipList)
    ip_table = getIpTable()
    result = getResult(ip_decimal_list, ip_table)

    print(result)