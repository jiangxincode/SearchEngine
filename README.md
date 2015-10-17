# SearchEngine

SearchEngine is a simple search engine written in Java, JSP, CSS, JS and HTML. This project is just for learning the basic concepts of search engine, thus avoid use it in your real work condition, it just a toy! If you have some questions about this project please contact me, and any pull request is really welcomed! My information is put below.

* Aloy
* jiangxinnju@163.com

This project is protected in GPLv3, you can see it in http://www.gnu.org/licenses/gpl-3.0.txt

If you get this project in other methods except GITHUB, please check the latest version in https://github.com/jiangxincode/SearchEngine


## log:

* Version 0.01
	* 完成框架设计，效率和功能都有待优化。
* Version 0.02
	* 这是一次较大的修改，主要集中在爬虫方面，爬虫的灵活性更大，可以有选择性的爬取网页，效率更高，代码也更容易阅读。并将爬虫部分和html代码解析部分分离。整个程序的各个子程序的耦合性进一步下降。删除了部分冗余代码。并解决了搜索页面链接无效的bug。标记了之前代码中的不良代码，方便以后的进一步修改。

* Version 0.03
	* 更改了Web前端部分，简化了代码，去除了CSS只用无效的样式。优化了界面，更加简洁。删除了部分无用的资源（比如图片）

* Version 0.04
	* 对于用户在搜索框中输入的字符串的分词部分有独立的类（该类采用反向最长匹配算法）实现改为IKAnalyzer实现，从而得到更好地效果，并简化了代码。同时相应的部分相关源文件也有少量修改。

* Version 0.05
	* 修复了Engine部分的重大bug，可以真正的将关键词全部进行检索，然后将包含不同关键词的文本的链接合并。同时改变了排序算法，能够提高效率。

* Version 0.06
	* 去除了大量冗余代码，修复了一些小bug和警告，去除了wordtable.txt文件。对于本README文件，增添了“待实现和完善部分”。

* Version 0.07
	* 去除了目前所有已经发现的冗余代码；更改了所有不合适的文件名，更加能体现其作用；对所有文件添加了文件功能注释，并于对文件功能整体把握；对于提示信息的输出更加合理，有利于进一步的分析研究。已经基本达到课程验收标准。

* Version 0.08
	* 去除了所有无用的第三方jar包，目前仅保留httpclient、htmlparser、IKAnalyzer三种个包。IKAnalyzer包的版本由IKAnalyzer3.2.8更新到IKAnalyzer2012_u6，切词效果更加优秀。

* Version 0.09
	* HttpClient包由Httpclient-3.0.1更新到最新的httpcomponents-client-4.3.3，更新原因是旧包已经停止更新，且存在一些不良特性。由于版本跨越幅度较大，接口变化较大，所以本程序中的Crawler.DownLoadFile.java文件变化较大。

* Version 0.10
	* 实现了CreateIndex的大幅优化，相关部分效率提高近200倍。

* Version 0.11
	* 实现了Crawler的多线程并行，效率提升约30%。但由于采用了线程，同步问题尚未完全解决，所以如果要求爬取100个网页，则实际爬取的网页可能并不是正好100个，但我觉得这个缺陷在实际的爬虫中是没有关系的，没有人会在乎多爬或少爬一个网页。

* Version 0.12
	* 创建AllInOne类，可以将所有步骤一次执行完毕。同时修复了各个子程序中写文件父目录不存在时产生异常的bug。

* Version 0.13
	* 实现了简单的双词交运算查询，利用英文&可以实现交操作，如：中国&美国。默认情况下为并操作。

* Version 0.14
	* 实现了简单的双词差运算查询，利用英文-可以实现差操作，如：中国-美国。默认情况下为并操作。

* Version 0.15
	* 为主程序提供了运行选项，选择是否删除已爬取的内容，是否自定义爬取数目。

* Version 0.20
	* 使用maven组织项目，整理了目录结构。
	* 统一项目编码为UTF-8
	* 添加了工具类

## Unsolved:

* 设置搜索框初始半透明提示文本。
* 争取用自己的分词方法代替第三方类库方法。
* 修复显示结果页面关键词重复的bug。
* 优化结果显示顺序。
* 删除冗余代码。
* 实现搜索结果分页。
* 优化结果页面，比如网址链接蓝色显示，去除“……”。
* 实现PageRank排序算法。
* 修复每次仅返回100个结果的bug。