EasyReport
==========

A simple and easy to use Web Report System for java

EasyReport是一个简单易用的Web报表工具,它的主要功能是把SQL语句查询出的行列结构转换成HTML表格(Table)，并支持表格的跨行(RowSpan)与跨行(ColSpan)。同时它还支持报表Excel导出、图表显示及固定表头与左边列的功能。

## 1.开发环境(Development Environment)
	[jdk1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)<br/>
	[maven3](http://maven.apache.org/download.cgi)<br/>
	[eclipse-jee-luna](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1a)<br/>
	[tomcat7+](http://tomcat.apache.org/)<br/>
## 2.安装与部署(Installation & Deployment)
### 2.1 从源代码安装(From Source Code)
	首先确定安装好jdk1.8与maven3，并配置好maven仓库，然后按如下步骤操作：<br/>
	<pre>
		step1:git clone https://github.com/xianrendzw/EasyReport.git
		step2:cd your_git_repository/EasyReport/easyreport-web
		step3:修改 src\main\resources\${env}\resource.properties 数据库连接，用户与密码
		step4:mvn clean package -P${env} (${env}变量说明:dev表示开发环境,prod表示生产，test表示测试)
		step5:经过step4之后会在target目录生成easyreport-web.war文件，<br/>
		      然后把这个文件部署到tomcat,jboss,jetty等容器中
	</pre>
### 2.2 从发布包安装(From Release Packages)
	直接从https://github.com/xianrendzw/EasyReport/releases <br/>
	下载war文件，然后修改war文件里WEB-INF\classes\resource.properties中数据库连接字符串，<br/>
	然后把这个文件部署到tomcat,jboss,jetty等容器中
	
## 3.使用说明(User Guide）

### 3.1 预备知识(Preface)
### 3.2 数据源设置(DataSource Configuration)
### 3.3 配置管理(Configuration)
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
