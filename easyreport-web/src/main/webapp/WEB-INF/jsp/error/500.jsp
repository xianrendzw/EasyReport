<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>500 Error</title>
    <link href="<%=request.getContextPath()%>/assets/css/error.css" rel="stylesheet" type="text/css" media="screen"/>
</head>
<body>
<div class="body-500">
    <h1>
        <span>500 Error</span>
    </h1>
    <div class="info-container">
        <div class="inner-border clear-fix">
            <h2 class="info-top">服务器内部错误</h2>
            <div class="site-owner-404">
                <ol>
                    <li>
                        <%
                            if (exception == null || exception.getMessage() == null) {
                                out.print("系统错误！");
                            } else {
                                StringWriter outWriter = new StringWriter();
                                PrintWriter printWriter = new PrintWriter(outWriter);
                                exception.printStackTrace(new PrintWriter(printWriter));
                                out.print(outWriter.toString());
                                printWriter.close();
                                outWriter.close();
                        %>
                    </li>
                </ol>
            </div>
        </div>
    </div>
</div>
</body>
</html>