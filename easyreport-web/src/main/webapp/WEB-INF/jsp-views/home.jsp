<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp-views/frame/pageInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>报表中心</title>
<%@ include file="/WEB-INF/jsp-views/frame/pageHeader.jsp"%>
<script>
	function addTab(title, url) {
		if ($('#content').tabs('exists', title)) {
			$('#content').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
					+ '" style="width:100%;height:100%;"></iframe>';
			$('#content').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div id="west" region="west" split="true" title="菜单" style="width: 150px;">
		<ul>
			<li><a href="javascript:void(0)" onclick="addTab('报表设计','<%=request.getContextPath()%>/report/designer')">报表设计</a></li>
			<li><a href="javascript:void(0)" onclick="addTab('数据源管理','<%=request.getContextPath()%>/report/ds')">数据源管理</a></li>
			<li><a href="javascript:void(0)" onclick="addTab('配置管理','<%=request.getContextPath()%>/report/config')">配置管理</a></li>
		</ul>
	</div>
	<div id="content" region="center" class="easyui-tabs"></div>
</body>
</html>