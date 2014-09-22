<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header-inner">
	<table cellpadding="0" cellspacing="0" style="width: 100%;">
		<tr>
			<td rowspan="2" style="width: 20px;"></td>
			<td style="height: 52px;">
				<div style="color: #fff; font-size: 22px; font-weight: bold;">
					<a href="<%=request.getContextPath()%>/index.jsp"
						style="color: #fff; font-size: 22px; font-weight: bold; text-decoration: none">EasyReport</a>
				</div>
				<div style="color: #fff">
					<a href="<%=request.getContextPath()%>/index.jsp" style="color: #fff; text-decoration: none">a simple and easy
						to use Web Report System!</a>
				</div>
			</td>
			<td style="padding-right: 5px; text-align: right; vertical-align: bottom;">
				<div id="topmenu">
					<a href="<%=request.getContextPath()%>/index">首页</a> 
					<a href="<%=request.getContextPath()%>/reporting/index">报表中心</a> 
					<a href="<%=request.getContextPath()%>/ds/index">数据源设置</a>
					<a href="<%=request.getContextPath()%>/task/index">系统设置</a>
			<%-- 		<a href="<%=request.getContextPath()%>/user/index">权限设置</a> 
					<a href="<%=request.getContextPath()%>/task/index">任务管理</a>
			<a href="<%=request.getContextPath()%>/reportcontacts/index">报表与联系关系管理</a> 
					<a href="<%=request.getContextPath()%>/contacts/index">联系人管理</a> 
					<a href="<%=request.getContextPath()%>/config/index">配置管理</a> --%>
					<a href="<%=request.getContextPath()%>/help/index">使用指南</a>
				</div>
			</td>
		</tr>
	</table>
</div>