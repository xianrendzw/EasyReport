<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            if(xhr.status === 401){
                return window.location.href='<%=request.getContextPath()%>/error/unauthorized'
            }
            var sessionStatus = xhr.getResponseHeader('sessionstatus');
            if (sessionStatus == 'timeout') {
                return window.location.reload();
            }
            if (sessionStatus == 'csrf') {
                console.log(xhr)
            }
        }
    });
</script>