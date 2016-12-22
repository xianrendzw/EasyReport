<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>404 Error</title>
    <link href="${ctxPath}/assets/css/error.css?v=${version}" rel="stylesheet" type="text/css" media="screen"/>
	<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
</head>
<body>
<div class="body-404">
    <h1>
        <span>404 (Page Not Found) Error</span>
    </h1>
    <div class="info-container">
        <div ng-app="i18n" ng-controller="translate" class="inner-border clear-fix">
            <h2 class="info-top">{{info.error_404_pageNotFound}}</h2>
    </div>
</div>
</body>
</html>