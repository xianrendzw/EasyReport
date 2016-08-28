<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EasyReport-A simple and easy to use Web Report System</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/xframejs.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/login.css?v=<%=Math.random()%>">
    <script src="<%=request.getContextPath()%>/assets/js/membership/login.js?v=<%=Math.random()%>"></script>
    <script type="text/javascript">
        if (window != top)
            top.location.href = location.href;
    </script>
</head>
<body>
<form id="login-form" name="login-form">
    <div id="login-container">
        <img class="login-bg-image" src="<%=request.getContextPath()%>/assets/images/login_bg_left.gif"/>
        <div id="login-main">
            <div id="login-left">
                <div id="login-left-main">
                    <img id="logo" title="EasyReport" src="<%=request.getContextPath()%>/assets/images/favicon_64.ico"><br/>
                    <p class="system-name"></p>
                </div>
            </div>
            <div id="login-right">
                <div id="login-right-main">
                    <p>
                        <span>用户名:</span><input type="text" id="account" name="account" class="txtinput"
                                                maxlength="100"/>
                    </p>
                    <p>
                        <span>密 码:</span><input type="password" id="password" name="password" class="txtinput"
                                                maxlength="64"/>
                    </p>
                    <!-- <p>
							<span>验证码:</span><input type="text" id="validCode" name="validCode" class="txtinput" />
						</p>
						<p class="login-form-validatecode">
							<img id="imgValidCode" style="border-width: 0px;" onclick="recodeimg();" alt="看不清?点一下"
								src="<%=request.getContextPath()%>/assets/images/GetValidateCode.jpg"><a href="javascript:recodeimg();">看不清?点一下</a>
						</p> -->
                    <p>
                        <span></span><input type="checkbox" id="rememberMe" name="rememberMe"/><label>记住密码</label>
                    </p>
                    <p>
                        <input type="button" id="btnLogin" name="btnLogin" class="login-submit" value="登录"
                               onclick="javascript:MembershipLogin.login();">
                    </p>
                </div>
                <div id="login-message-tips"></div>
            </div>
        </div>
        <img class="login-bg-image" alt="EasyReport" src="<%=request.getContextPath()%>/assets/images/login_bg_right.gif"> <br
            class="clear"/>
    </div>
</form>
</body>
</html>