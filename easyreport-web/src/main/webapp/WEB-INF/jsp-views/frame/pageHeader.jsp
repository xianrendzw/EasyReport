<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/report/icons/favicon_32.ico">
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/static/frame/easyui/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/static/frame/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/report/css/common.css?v=<%=Math.random()%>"  />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frame/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frame/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frame/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery.xframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/utils/XFrameUtils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery-validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/report/js/common.js?v=<%=Math.random()%>"></script>
<script type="text/javascript">
		var XFrame = {
				setContextPath :function(path) {
					XFrame._contextPath = path;
				},
				getContextPath : function() {
					return XFrame._contextPath;
				}
		};
		XFrame.setContextPath('<%=request.getContextPath()%>');
				$.ajaxSetup({
					contentType : "application/x-www-form-urlencoded;charset=utf-8",
					complete : function(xhr, textStatus) {
						//session timeout
						if (xhr.status == 911) {
							return window.parent.location = '';
						}
					}
		});
	</script>