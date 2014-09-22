<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
		response.setHeader("Pragma", "no-cache");
	} else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
		response.setHeader("Cache-Control", "no-cache");
	}
	response.setDateHeader("Expires", 0);
%>
