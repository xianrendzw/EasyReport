<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp-views/frame/pageInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>数据源管理</title>
<%@ include file="/WEB-INF/jsp-views/frame/pageHeader.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/report/js/datasource.js?v=<%=Math.random()%>"></script>
</head>
<body class="easyui-layout" style="text-align: left">
	<!-- 右边tabs -->
	<div region="center" border="false">
		<div id="datasourceDiv" style="width: 100%; height: 98%">
			<div id="datasourceGrid"></div>
		</div>
	</div>
	<!-- 新增与修改数据源dialog  -->
	<div id="datasourceDlg" style="width: 500px; height:300px; padding: 10px 20px";>
		<form id="datasourceForm" method="post" >
			<table cellpadding="0" class="formStyle" cellpadding="5"  cellspacing="5" style="width: 99%;">
			<tr>
				<td  class="text_r blueside" style="font-size:15px">数据源名称:</td><td><input name="name" class="easyui-validatebox" required="true"  style="width: 300px;"/></td>
			</tr>
			<tr>
				<td class="text_r blueside" style="font-size:15px">用户名:</td><td><input id="configUser" name="user" class="easyui-validatebox" required="true"  style="width: 300px;"/></td>
			</tr>
			<tr >
				<td class="text_r blueside" style="font-size:15px">密码:</td><td><input  id="configPassword"  name="password" class="easyui-validatebox" required="true"  style="width: 300px;"/> <input id="datasourceId"
					type="hidden" name="id" value="0" /> <input id="datasourceAction" type="hidden" name="action" /></td>
			</tr>
			
					<tr>
						<td class="text_r blueside top"  width="22%" style="font-size:15px">JDBC连接字符串:</td>
						<td><textarea id="configJdbcUrl" name="jdbcUrl" style="width: 99%; height: 100px;" class="easyui-validatebox" required="true" ></textarea></td>
					</tr>
				</table>
		</form>
	</div>
	
	<div id="checkDlg" title="数据源详细信息" style=" width:400px ; height:400px ;padding: 10px 20px">
		<div style="margin-top: 8px"></div>
		  <form id="checkFrm" method="post">
		<table cellpadding="0" class="formStyle" cellpadding="5"  cellspacing="5" style="width: 99%;">
			<tr >
				<td class="text_r blueside">数据源名称:</td><td><input type="text" id="checkname" name="name" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>
			<tr >
				<td class="text_r blueside">数据源唯一ID:</td><td><input type="text" id="checkuid" name="uid" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>
			<tr >
				<td class="text_r blueside">用户名:</td><td><input type="text" id="checkuser" name="user" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>
			<tr >
				<td class="text_r blueside">密码:</td><td><input type="text" id="checkPassword" name="password" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>
			<tr >
				<td class="text_r blueside">创建时间:</td><td><input type="text" id="checkcreateTime" name="createTime" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>
			<tr >
				<td class="text_r blueside">更新时间:</td><td><input type="text" id="checkupdateTime" name="updateTime" readonly="readonly" disabled="true"
					style="width: 200px;" /></td>
			</tr>

					<tr >
						<td class="text_r blueside top" width="34%">JDBC连接字符串:</td>
						<td><textarea id="checkJdbcUrl" name="jdbcUrl" style="width: 99%; height: 100px;" disabled="true"></textarea></td>
					</tr>
			</table>
		</form>
	</div>
</body>
</html>