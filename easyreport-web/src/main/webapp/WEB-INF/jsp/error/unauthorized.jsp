<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>没有权限</title>
<link rel="stylesheet" href="${ctxPath}/assets/css/error.css?v=${version}" />
</head>
<body>
	<section ng-app="i18n" ng-controller="translate" class="center">
		<article>
			<h1 class="header">{{info.error_authorized_reject}}</h1>
			<p class="error">{{info.error_authorized_unaccessable}}</p>
			</article>
		</article> 
	</section>
</body>
</html>