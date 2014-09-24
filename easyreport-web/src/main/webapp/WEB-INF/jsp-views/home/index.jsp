<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp-views/frame/pageInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords"
	content="reporting,web reporting,easy report,big data,data analysis" />
<meta name="description"
	content="A simple and easy to use Web Report System ." />
<title>EasyReport - a simple and easy to use Web Report System</title>
<%@ include file="/WEB-INF/jsp-views/frame/pageHeader.jsp"%>
<script type="text/javascript">
	$(function() {
		function open1(plugin) {
			if ($('#tt').tabs('exists', plugin)) {
				$('#tt').tabs('select', plugin);
			} else {
				$('#tt').tabs('add', {
					title : plugin,
					href : plugin + '.php',
					closable : true,
					extractor : function(data) {
						data = $.fn.panel.defaults.extractor(data);
						var tmp = $('<div></div>').html(data);
						data = tmp.find('#content').html();
						tmp.remove();
						return data;
					}
				});
			}
		}
	});
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div region="north" border="false"
		style="background: #666; text-align: center">
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
							<a href="<%=request.getContextPath()%>/index.jsp"
								style="color: #fff; text-decoration: none">a simple and easy
								to use Web Report System!</a>
						</div>
					</td>
					<td
						style="padding-right: 5px; text-align: right; vertical-align: bottom;">
						<div id="topmenu">
							<a href="<%=request.getContextPath()%>/index">首页</a> <a
								href="<%=request.getContextPath()%>/reporting/index">报表中心</a> <a
								href="<%=request.getContextPath()%>/ds/index">数据源设置</a> <a
								href="<%=request.getContextPath()%>/task/index">系统设置</a>
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
	</div>
	<div region="center">
		<div id="tt" class="easyui-tabs" fit="true" border="false"
			plain="true">
			<div title="使用指南" href=""></div>
		</div>
	</div>
</body>
</html>