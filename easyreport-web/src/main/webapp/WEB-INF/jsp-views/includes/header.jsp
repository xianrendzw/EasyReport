<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/img/favicon.ico"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/frames/easyui/themes/metro/easyui.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/frames/easyui/themes/icon.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css"/>
<script src="<%=request.getContextPath()%>/assets/js/frames/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/frames/easyui/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/frames/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/plugins/jquery.extension.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/libs/artTemplate.js"></script>
<script>
    //设置jquery ajax全局设置
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (xhr, textStatus) {
            var sessionStatus = xhr.getResponseHeader('sessionstatus');
            if (sessionStatus == 'timeout') {
                return window.location.reload();
            }
        }
    });

    //设置站点Context路径，即WebApp的路径
    var WebAppRequest = {
        setContextPath: function (path) {
            WebAppRequest._contextPath = path;
        },
        getContextPath: function () {
            return WebAppRequest._contextPath;
        }
    };
    WebAppRequest.setContextPath('<%=request.getContextPath()%>');
</script>