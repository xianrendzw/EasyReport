![Wechat](https://github.com/xianrendzw/EasyReport/assets/177784/5efd50b3-588c-4e13-8357-a1e2c7b4257b)EasyReport
==========

A simple and easy to use Web Report System for java

EasyReport是一个简单易用的Web报表工具,它的主要功能是把SQL语句查询出的数据转换成报表页面，
同时支持表格的跨行(RowSpan)与跨列(ColSpan)配置。
同时它还支持报表Excel导出、图表显示及固定表头与左边列的功能。 

欢迎扫码微信进群交流：
![微信][]

* mvn -DskipTests package
* mvn spring-boot:run -pl easyreport-web 

然后就可以通过浏览器localhost:8080查看


## Release(发布说明)
### what's new?(ver2.1)
* 改进图表报表图表生成并增加图表生成配置
* 定时任务功能完成
* 支持大数据产品查询引擎(Hive,Presto,HBase,Drill,Impala等）
* 提供REST API服务接口
* 增加报表权限控制

### what's new?(ver2.0)
* 界面交互调整,前端js代码全部重写,方便向AMD模块化转换
* 报表引擎查询支持CP30、Druid、DBCP2连接池
* JAVA部分代码重构
* 加入用户及权限管理模块
* 数据访问采用mybatis框架,方便二次开发
* 报表展现支持自定义生成模板

## [入门手册][]
## [用户参考][]

[入门手册]: https://github.com/xianrendzw/EasyReport/blob/master/docs/manual/user-guide.md
[用户参考]: https://github.com/xianrendzw/EasyReport/blob/master/docs/manual/version2_0.md
[微信]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/Wechat.jpg
