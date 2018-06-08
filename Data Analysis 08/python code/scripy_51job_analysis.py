import requests
import time
from bs4 import BeautifulSoup

url = "https://search.51job.com/list/090200,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36'  # your user-Agent here
}

def connect(url):
    html = requests.get(url=url, headers=headers)
    soup = BeautifulSoup(html.text.encode('ISO-8859-1'), 'lxml')
    return soup


def getInfo(url):
    soup = connect(url)
    temps = soup.find('div', {'class':'dw_table'}).find_all('div', {'class':'el'})
    for i in range(1, len(temps)):
        jobTitle = temps[i].select('.t1')[0].text.strip()
        company = temps[i].select('.t2 a')[0]['title']
        location = temps[i].select('.t3')[0].text
        salary = temps[i].select('.t4')[0].text
        date = temps[i].select('.t5')[0].text
        output = jobTitle + '\t' + company + '\t' + location + '\t' + salary + '\t' + date
        print(output)
    # 获取下一页url
    t = soup.select('.bk')[1]
    if (t.select('a')):
        url = t.select('a')[0]['href']
        print(url)
        #递归遍历
        time.sleep(1)
        getInfo(url)

if __name__ == "__main__":
    getInfo(url)

