使用手册
================================================


### 1 功能介绍
本工具从数据库(MySQL,Oracle,SQLServer,HBase等)的表中读取数据，转换成报表。支持多级索引以及复杂的自定义配置。![raw-table][]

比如可以把上面的数据库表的内容，免开发配置出如下报表页面：![report-ex1][]

此外，本工具还支持复杂的自定义条件筛选、排序、数据量计算表达式等。
### 2 数据源设置
首先要配置好数据来源，然后基于数据源做出报表。![ds-1][]

### 3 报表设计
数据源配置成功就可以开始报表设计了
首先写一个SQL查询语句，把想要展示的信息查询出来，比如：

select area, year(dt), quality, pm25, pm10, o3 from fact_air_cn where area='北京市' or area='上海市' or area='三亚市' 
![design-1][]

点击执行SQL，查询出的内容要分成两类，一类是“索引”，一类是数值。索引可以显示在左侧或者上侧，可以有多级索引。比如我想分地区、年份、空气质量类型来查看pm2.5, pm10, o3的数值：
![design-2][]
地区和年份的索引显示在左侧，空气质量类型显示在上侧。

保存后点击预览报表即可显示：![design-3][]

这样，一份报表就做好了。


[jdk1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jre1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[maven3]: http://maven.apache.org/download.cgi
[idea]: https://www.jetbrains.com/idea/
[eclipsejee]: http://www.eclipse.org/downloads/eclipse-packages/
[lombok]: https://projectlombok.org/download.html
[tomcat8+]: http://tomcat.apache.org/
[MySQL5+]: http://dev.mysql.com/downloads/mysql/
[velocity]: http://velocity.apache.org/engine/1.7/user-guide.html
[aviator]: https://code.google.com/p/aviator/wiki/User_Guide_zh
[aviator-doc]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/files/Aviator-2.3.0用户指南.pdf
[org.apache.commons.lang3.StringUtils]: http://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html
[pm25.in]: http://www.pm25.in
[aqistudy]: http://aqistudy.sinaapp.com/historydata/index.php
[echarts]: http://echarts.baidu.com/index.html
[highcharts]: http://www.highcharts.com/
[tablesorter]: http://mottie.github.io/tablesorter/docs/
[DataTables]: http://www.datatables.net/
[release]: https://github.com/xianrendzw/EasyReport/releases
[ds-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ds-1.png
[config-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/config-1.png
[rp-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-1.png
[rp-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-2.png
[rp-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-3.png
[rp-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-4.png
[rp-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-5.png
[rp-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-6.png
[rp-7]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-7.png
[rp-8]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-8.png
[rp-9]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-9.png
[rp-10]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-10.png
[rp-11]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-11.png
[rp-12]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-12.png
[rp-13]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-13.png
[rp-14]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-14.png
[dev-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/dev-1.png
[ex-src-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-1.png
[ex-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-1.png
[ex-src-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-2.png
[ex-param-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-param-2.png
[ex-2-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-1.png
[ex-2-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-2.png
[ex-2-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-3.png
[ex-2-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-4.png
[ex-src-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-3.png
[ex-3-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-1.png
[ex-3-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-2.png
[ex-3-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-3.png
[ex-3-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-4.png
[ex-src-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-4.png
[ex-4-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-4-1.png
[ex-src-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-5.png
[ex-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-5.png
[ex-src-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-6.png
[ex-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-6.png
[ex-7-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-7-1.png
[ex-7-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-7-2.png
[mysql.zip]: https://github.com/xianrendzw/EasyReport/blob/master/docs/db/mysql.zip?raw=true
[raw-table]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/raw-table.jpg
[report-ex1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/report-ex1.jpg
[design-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-1.jpg
[design-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-2.jpg
[design-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-3.jpg