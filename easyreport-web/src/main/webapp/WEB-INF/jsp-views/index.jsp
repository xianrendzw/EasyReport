<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp-views/frame/pageInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="reporting,web reporting,easy report,big data,data analysis" />
<meta name="description" content="A simple and easy to use Web Report System ." />
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
	<div region="north" border="false" style="background: #666; text-align: center">
		<%@ include file="/WEB-INF/jsp-views/frame/pageHeaderInner.jsp"%>
	</div>
	<div region="center">
		<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
			<div title="使用指南" href=""></div>
		</div>
	</div>
</body>
</html>