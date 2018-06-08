##爬虫
爬取51job上关于大数据岗位的信息。
###知识点：
Jsoup库

select后面添加not()，过滤掉包含相同class或id的标签：

```
Elements elements = document.select(".dw_table .el").not(".title");
```
使用递归调用会使得代码简洁。

###Python
当抓取的页面出现乱码时，先查看页面的编码格式:

```
print(html.text.encoding)
```
根据获取的编码格式调整，比如页面格式为'ISO-8859-1'：

```
html = requests.get(url=url, headers=headers)
soup = BeautifulSoup(html.text.encode('ISO-8859-1'), 'lxml')
```

###BeautifulSoup

```
from pprint import pprint
from bs4 import BeautifulSoup

html_content = open('bs_sample3.html') 
# http://dl.dropbox.com/u/49962071/blog/python/resource/bs_sample3.html
soup = BeautifulSoup(html_content) # making soap

pprint(soup.select("title")) # get title tag
pprint(soup.select("body a")) # all a tag inside body
pprint(soup.select("html head title")) # html->head->title
pprint(soup.select("head > title")) # head->title
pprint(soup.select("p > a")) # all a tag that inside p
pprint(soup.select("body > a")) # all a tag inside body
pprint(soup.select(".sister")) # select by class
pprint(soup.select("#link1")) # select by id
pprint(soup.select('a[href="http://example.com/elsie"]')) 
# find tags by attribute value
pprint(soup.select('a[href^="http://example.com/"]'))
# find tags by attribute value, all contains 'http://example.com/'
pprint(soup.select('p[lang|=en]')) # Match language codes
```