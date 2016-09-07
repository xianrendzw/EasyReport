<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    var EasyReport = {
        env: 'dev',//dev|prod
        ctx: {
            path: '<%=request.getContextPath()%>',
        },
        version: '2.0',
        config: {
            pageSize: 30
        },
        global: {},
        utils: {},
        validate: {},
        temp: {}
    };
</script>
