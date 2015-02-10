EasyReport
==========

A simple and easy to use Web Report System for java

EasyReport是一个简单易用的Web报表工具,它的主要功能是把SQL语句查询出的行列结构转换成HTML表格(Table)，并支持表格的跨行(RowSpan)与跨行(ColSpan)。同时它还支持报表Excel导出、图表显示及固定表头与左边列的功能。

##   目录

*[开发环境(Development Environment)](#user-content-1开发环境development-environment)  
*[安装与部署(Installation & Deployment)](#user-content-2安装与部署installation--deployment)  
*	[从源代码安装(From Source Code)](#user-content-21-从源代码安装from-source-code)  
*	[从发布包安装(From Release Packages)](#user-content-22-从发布包安装from-release-packages)  
*[使用说明(User Guide)](#user-content-3使用说明user-guide)  
*	[预备知识(Preface)](#user-content-31-预备知识preface)  
*	[数据源设置(DataSource Configuration)](#user-content-32-数据源设置datasource-configuration)  
*	[配置管理(Configuration)](#user-content-33-配置管理configuration)  
*	[报表设计(Reporting Design)](#user-content-34-报表设计reporting-design)  
*	[示例(Examples)](#user-content-35-示例examples)  
*	[相关参考(Referrence links)](#user-content-36-相关参考referrence-links)  
*[开发者(For Developers)](#user-content-4开发者for-developers)  
*	[报表引擎接口(Reporting Engine API)](#user-content-41-报表引擎接口reporting-engine-api)  
*	[自定义报表开发(Customsized Reporting develop)](#user-content-42-自定义报表开发customsized-reporting-develop)  
*[常见问题(FAQ)](#user-content-5常见问题faq)  

## 1.开发环境(Development Environment)  
[jdk1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
[maven3](http://maven.apache.org/download.cgi)  
[eclipsejee-luna](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1a)  
[tomcat7+](http://tomcat.apache.org/)  

## 2.安装与部署(Installation & Deployment)

### 2.1 从源代码安装(From Source Code)
首先确定安装好jdk1.8与maven3，并配置好maven仓库，然后按如下步骤操作：   
**step1**:git clone https://github.com/xianrendzw/EasyReport.git  
**step2**:cd yourgitrepository/EasyReport/easyreport-web  
**step3**:修改 src\main\resources\${env}\resource.properties 数据库连接，用户与密码  
**step4**:mvn clean package -P${env} (${env}变量说明:dev表示开发环境,prod表示生产，test表示测试)  
**step5**:经过step4之后会在target目录生成easyreport-web.war文件，然后把这个文件部署到tomcat,jboss,jetty等容器中  

### 2.2 从发布包安装(From Release Packages)
直接从[release](https://github.com/xianrendzw/EasyReport/releases)下载war文件，然后修改war文件里WEB-INF\classes\resource.properties中数据库连接字符串，然后把这个文件部署到tomcat,jboss,jetty等容器中.
	
## 3.使用说明(User Guide)

### 3.1 预备知识(Preface)  
简单的说，报表就是用表格、图表等格式来动态显示数据。它是数据可视化的重要部分。尤其在当今大数据泛滥的时代，到处都需要各种各样的报表。在使用该工具之前您应该先了解一下数据仓库、维度、度量、[事实表](http://www.cnblogs.com/wufengtinghai/archive/2013/05/04/3060265.html)等相关概念，这将会对你制作报表有一定的帮助。

本工具只是简单的从数据库(MySQL,Oracle,SQLServer,HBase等)中的事实表读取数据，并转换成HTML表格形式展示。不支持CUBE、钻取、切片等复杂OLAP相关的功能。

### 3.2 数据源设置(DataSource Configuration)
在制作报表前需要先设置数据源，本工具只支持在单一数据源（即数据库）生成报表。![图3-2](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ds-1.png)

### 3.3 配置管理(Configuration) 
配置管理主要于在制作报表时自动匹配一些常用的列名对应的中文描述。如:dt,date（日期）、title(标题）等。![图3-3](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/config-1.png)  

### 3.4 报表设计(Reporting Design)
通常，只要把数据源配置成功就可以开始报表设计了，报表设计主要分两个步骤：基本设置与查询参数设置。且必须先把基本设置保存后方可进行查询参数设置 ，查询参数设置是可选的，主要看报表设计者的意图。  

#### 3.4.1 基本设置(Basic Settings)
![rp-1](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-1.png)
报表的基本设置由4部分组成(如上图所示）：**报表树型列表、报表基本属性、报表SQL查询语句、报表元数据列配置**。
在设计报表之前，先简单介绍几个名词，我们从数据仓库概念了解到维度与度量这两个概念，事实上一条SQL语句查询的结果就是一张二维表格，即由行与列组成的表格，在统计分析时，我们把有些列称为维度列，有些列称为度量列。有时事实表里有好几个维度与度量列，但是SQL查询结果只能是二维表格，它不能把维度层次化，展示方式固定而不能灵活变动，这样在观察与分析数据时多有不便，因此一些报表工具就解决了这些问题。本工具把事实表中的维度列与度量列进行再次划分如下表所示：    

类型      | 子类型
----- | ------
维度列  | 布局维度列、简称布局列
      | 一般维度列、简称维度列
度量列  | 统计列
      | 计算列
 
1. 布局列主要用于报表展示方式上，如果布局列为横向展示，则报表在绘制时会把布局列的内容绘制表报表表头，维度列的内容绘制报表表体的左边;如果布局列为纵向展示，则报表在绘制时会把布局列的内容绘制表报表表体的左边，维度列的内容绘制报表表头。
  
2. 计算列是根据SQL查询结果中列的值再根据其配置的计算表达式动态运算出来的，它不存在于SQL语句或事实表中,其中使用的表达式引擎为[aviator](https://code.google.com/p/aviator/wiki/User_Guide_zh)。

了解了上述基本知识后，我们来看看一张报表的主要设计流程:  
**1.创建报表树型目录列表**     
![rp-2](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-2.png)
**2.点击1新建根节点，也可以在树列表中右键创建子节点**      
![rp-3](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-3.png)  
**3.选择指定的目录,设置基本信息，如报表名称，数据源，布局与统计列展示方式**        
**4.输入报表SQL查询语句**    
**5.执行SQL查询语句并获取报表的列信息**    
**6.配置报表的列**    
**7.新增并保存基本设置信息到数据库**    
![rp-4](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-4.png)
新增成功后，就可以双击树列表中报表名称节点或点击报表预览按钮预览报表。如觉得报表展示的不够有好，可以通过修改布局列与统计列的展示方式来改变报表显示。
![rp-5](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-5.png)
上图是日期为布局列且横向显示的报表预览结果。我们可以修改一下相关配置让报表展示更直观些。
![rp-6](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-6.png)
由于列名dt已经在配置管理设置了默认标题，因此在执行SQL后会自动匹配它的标题，您也可以把其他的列名增加配置管理项中，这样下次设计报表时就会自动匹配默认标题。现在看修改后报表展示。
![rp-7](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-7.png)    

#### 3.4.2 查询参数(Query Parameter)
有时候报表需要根据指定条件动态生成，如要查看不同城市空气质量情况，这个时候，我就需要创建一个查询参数变量。
![rp-8](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-8.png)
其中表单控件用于报表查询参数显示形式，主要有下拉单选框(select)、下单多选框(select mul)、复选框(checkbox)及文本框(textbox)四种。下图1处为查询参数列表。
![rp-9](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-9.png)
当查询参的表单控件为下拉单选或多选时，内容来源有两种不同的形式。  

内容来源 | 内容 | 备注
-------- | ---- | ----
SQL语句  | select col1 as name,col2 as text from table ... | 只包含两列且列名必须为**name**与**text**，name列的值对应下拉框的value属性，text列的值对应下拉框的text属性
文本字符串 | name1,text1\|name2,text2\|... 或name1\|name2\|... | 多个值必须用’\|’分隔，如果name与text值相同则只选择一个并用’\|’分开也可  

#### 3.4.3 内置变量与函数(Build-in variables & functions)

有些常用的查询参数不需要用户每次都创建，因此集成在工具内，这些参数变量称为**内置变量**。

有些报表的SQL语句很复杂，有时需要根据参数动态生成或需要用模板引擎(velocity)生成，因此需要一些能模板引擎中应用的函数，这些函数称为**内置函数**。

1.内置变量(区分大小写）

变量名 | 说明 | 返回值说明
-----  | ---- | --------
startTime|开始日期|2015-02-04(默认结束日期的前七天，这个可以由报表基本设置的显示天数修改)
endTime|结束日期|2015-02-10（默认为当前天）
intStartTime|整型开始日期|20150204
intEndTime|整型结束日期|20150210
utcStartTime|UTC开始日期|2015-02-04（UTC日期，中国为UTC+8区）
utcEndTime|UTC结束日期|2015-02-10（UTC日期）
utcIntStartTime|UTC整型开始日期|20150204
utcIntEndTime|UTC整型结束日期|20150204  

2.内置函数

* 日期函数
![rp-10](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-10.png)
* 字符串函数，请参考org.apache.commons.lang3.StringUtils类   

#### 3.4.4 图表显示（Charting)
![rp-11](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-11.png)
点击报表的图示展示按钮，出现如下界面：
![rp-12](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-12.png)
如果要查看多个城市也可以通过对比来显示。
![rp-13](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-13.png)
如果统计列只有一列时，图表显示就可以支持二个维度同时全部展示
![rp-14](https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-14.png)  

### 3.5 示例(Examples)

### 3.6 相关参考(Referrence links)

* 报表SQL中使用的模板引擎:[velocity](http://velocity.apache.org/engine/releases/velocity-1.5/user-guide.html)  
* 计算列中使用的表达式引擎:[aviator](https://code.google.com/p/aviator/wiki/User_Guide_zh)  
* 所有示例中的数据来源:[pm25.in](http://www.pm25.in)、[aqistudy](http://aqistudy.sinaapp.com/historydata/index.php)  
* 图表控件:[echarts](http://echarts.baidu.com/index.html)、[highcharts](http://www.highcharts.com/)  

## 4.开发者(For Developers)
### 4.1 报表引擎接口(Reporting Engine API)
### 4.2 自定义报表开发(Customsized Reporting develop)
## 5.常见问题(FAQ)
