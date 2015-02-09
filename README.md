EasyReport
==========

A simple and easy to use Web Report System for java

EasyReport是一个简单易用的Web报表工具,它的主要功能是把SQL语句查询出的行列结构转换成HTML表格(Table)，并支持表格的跨行(RowSpan)与跨行(ColSpan)。同时它还支持报表Excel导出、图表显示及固定表头与左边列的功能。

## 1.开发环境(Development Environment)
	[jdk1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
	[maven3](http://maven.apache.org/download.cgi)
	[eclipsejee-luna](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1a)
	[tomcat7+](http://tomcat.apache.org/)
## 2.安装与部署(Installation & Deployment)
### 2.1 从源代码安装(From Source Code)
	首先确定安装好jdk1.8与maven3，并配置好maven仓库，然后按如下步骤操作：
	<pre>
		step1:git clone https://github.com/xianrendzw/EasyReport.git
		step2:cd yourgitrepository/EasyReport/easyreport-web
		step3:修改 src\main\resources\${env}\resource.properties 数据库连接，用户与密码
		step4:mvn clean package -P${env} (${env}变量说明:dev表示开发环境,prod表示生产，test表示测试)
		step5:经过step4之后会在target目录生成easyreport-web.war文件，然后把这个文件部署到tomcat,jboss,jetty等容器中
	</pre>
### 2.2 从发布包安装(From Release Packages)
	直接从https://github.com/xianrendzw/EasyReport/releases <br/>
	下载war文件，然后修改war文件里WEB-INF\classes\resource.properties中数据库连接字符串，然后把这个文件部署到tomcat,jboss,jetty等容器中
	
## 3.使用说明(User Guide）

### 3.1 预备知识(Preface)
	简单的说，报表就是用表格、图表等格式来动态显示数据。它是数据可视化的重要部分。
尤其在当今大数据泛滥的时代，到处都需要各种各样的报表。在使用该工具之前您应该先了解一下数据仓库、维度、度量、[事实表](http://www.cnblogs.com/wufengtinghai/archive/2013/05/04/3060265.html)等相关概念，这将会对你制作报表有一定的帮助。
	本工具只是简单的从数据库(MySQL,Oracle,SQLServer,HBase等)中的事实表读取数据，并转换成HTML表格形式展示。不支持CUBE、钻取、切片等复杂OLAP相关的功能。
### 3.2 数据源设置(DataSource Configuration)
	在制作报表前需要先设置数据源，本工具只支持在单一数据源（即数据库）生成报表。
	![](https://github.com/xianrendzw/EasyReport/blob/master/docs/assets/imgs/ds-1.png)  
### 3.3 配置管理(Configuration)
	配置管理主要于在制作报表时自动匹配一些常用的列名对应的中文描述。如:dt,date（日期）、title(标题）等。
	![](https://github.com/xianrendzw/EasyReport/blob/master/docs/assets/imgs/config-1.png)  
### 3.4 报表设计(Reporting Design)
#### 3.4.1 基本设置(Basic Settings)
#### 3.4.2 查询参数(Query Parameter)
#### 3.4.3 内置变量与函数(Build-in variables & functions)
#### 3.4.4 报表预览（Reporting preview)
#### 3.4.5 图表显示（Charting)
### 3.5 示例(Examples)
### 3.6 相关参考(Referrence links)

## 4.开发者(For Developers)
### 4.1 报表引擎接口(Reporting Engine API)
### 4.2 自定义报表开发(Customsized Reporting develop)

## 5.常见问题(FAQ)
