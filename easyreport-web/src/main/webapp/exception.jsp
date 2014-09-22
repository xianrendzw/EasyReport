<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isErrorPage="true" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.StringReader"%>
<%@ page import="java.io.StringWriter"%>
  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统错误</title>
</head>

<body>
<p>对不起,系统错误 </p>
<%
		if (exception == null || exception.getMessage() == null) {
			out.print("系统错误！");
		} else {
			String message = exception.getMessage();
			if (message.lastIndexOf("Exception:") > 0) {
				out.print(message.substring(message.lastIndexOf("Exception:") + 10));
			} else {
				out.print(message);
			}

			StringWriter outWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(outWriter));

			StringBuffer buffer = new StringBuffer();
			BufferedReader in = new BufferedReader(new StringReader(outWriter.toString()));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				buffer.append(line);
				buffer.append("<br>");
				if (buffer.length() > 3900)
					break;
			}

			out.print(buffer.toString());
		}
	%>
</body>
</html>