<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> SmartAdmin </title>
		<meta name="description" content="">
		<meta name="author" content="">
			
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
 
		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/font-awesome.min.css">

		<!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/smartadmin-production.css">
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/smartadmin-skins.css">

		<!-- SmartAdmin RTL Support is under construction
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/smartadmin-rtl.css"> -->

		<!-- We recommend you use "your_style.css" to override SmartAdmin
		     specific styles this will also ensure you retrain your customization with each SmartAdmin update.
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/assets/css/your_style.css"> -->

		<!-- FAVICONS -->
		<link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/img/favicon/favicon.ico" type="image/x-icon">
		<link rel="icon" href="<%=request.getContextPath()%>/assets/img/favicon/favicon.ico" type="image/x-icon">

		<!-- GOOGLE FONT -->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

		<!-- Specifying a Webpage Icon for Web Clip 
			 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
		<link rel="apple-touch-icon" href="<%=request.getContextPath()%>/assets/img/splash/sptouch-icon-iphone.png">
		<link rel="apple-touch-icon" sizes="76x76" href="<%=request.getContextPath()%>/assets/img/splash/touch-icon-ipad.png">
		<link rel="apple-touch-icon" sizes="120x120" href="<%=request.getContextPath()%>/assets/img/splash/touch-icon-iphone-retina.png">
		<link rel="apple-touch-icon" sizes="152x152" href="<%=request.getContextPath()%>/assets/img/splash/touch-icon-ipad-retina.png">
		
		<!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
		<!-- Startup image for web apps -->
		<link rel="apple-touch-startup-image" href="<%=request.getContextPath()%>/assets/img/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
		<link rel="apple-touch-startup-image" href="<%=request.getContextPath()%>/assets/img/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
		<link rel="apple-touch-startup-image" href="<%=request.getContextPath()%>/assets/img/splash/iphone.png" media="screen and (max-device-width: 320px)">

	</head>
	<body class="fixed-header fixed-ribbon fixed-navigation smart-style-3">
		<!-- POSSIBLE CLASSES: minified, fixed-ribbon, fixed-header, fixed-width
			 You can also add different skin classes such as "smart-skin-1", "smart-skin-2" etc...-->
		
		<!-- HEADER -->
		<header id="header">
			<div id="logo-group">

				<!-- PLACE YOUR LOGO HERE -->
				<span id="logo"> <img src="<%=request.getContextPath()%>/assets/img/logo-pale.png" alt="SmartAdmin"> </span>
				<!-- END LOGO PLACEHOLDER -->
			</div>

			<!-- end projects dropdown -->

			<!-- pulled right: nav area -->
			<div class="pull-right">

				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->

				<!-- logout button -->
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="login.html" title="Sign Out" data-logout-msg="You can improve your security further after logging out by closing this opened browser"><i class="fa fa-sign-out"></i></a> </span>
				</div>
				<!-- end logout button -->

				<!-- search mobile button (this is hidden till mobile view port) -->
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<!-- end search mobile button -->

				<!-- input: search field -->
				<form action="#ajax/search.html" class="header-search pull-right">
					<input type="text" name="param" placeholder="Find reports and more" id="search-fld">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
					<a href="javascript:void(0);" id="cancel-search-js" title="Cancel Search"><i class="fa fa-times"></i></a>
				</form>
				<!-- end input: search field -->

				<!-- fullscreen button -->
				<div id="fullscreen" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0);" onclick="launchFullscreen(document.documentElement);" title="Full Screen"><i class="fa fa-fullscreen"></i></a> </span>
				</div>
				<!-- end fullscreen button -->

			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->

		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">

			<!-- User info -->
			<div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as it --> 
					
					<a href="javascript:void(0);">
						<img src="<%=request.getContextPath()%>/assets/img/avatars/male.png" alt="me" class="online" /> 
						<span>admin</span>
					</a> 
					
				</span>
			</div>
			<!-- end user info -->

			<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
			<nav>
				<!-- NOTE: Notice the gaps after each icon usage <i></i>..
				Please note that these links work a bit different than
				traditional href="" links. See documentation for details.
				-->
				<ul>
					<li>
						<a href="ajax/myreport.html"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">我的报表</span></a>
					</li>
					<li>
						<a href="ajax/treeview.html"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">报表管理</span></a>
					</li>
					<li>
						<a href="ajax/datasource.html"><i class="fa fa-lg fa-fw fa-files-o"></i> <span class="menu-item-parent">数据源管理</span></a>
					</li>
					<li>
						<a href="ajax/user.html"><i class="fa fa-lg fa-fw fa-group"></i> <span class="menu-item-parent">报表权限管理</span></a>
					</li>
					<li>
						<a href="ajax/task.html"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">报表任务管理</span></a>
					</li>
					<li>
						<a href="ajax/contacts.html"><i class="fa fa-lg fa-fw fa-user"></i> <span class="menu-item-parent">联系人管理</span></a>
					</li>
					<li>
						<a href="ajax/reportcontacts.html"><i class="fa fa-lg fa-fw fa-user-md"></i> <span class="menu-item-parent">报表联系人管理</span></a>
					</li>
					<li>
						<a href="ajax/config.html"><i class="fa fa-lg fa-fw fa-gears"></i> <span class="menu-item-parent">配置管理</span></a>
					</li>
				</ul>
			</nav>
			<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

		</aside>
		<!-- END NAVIGATION -->

		<!-- MAIN PANEL -->
		<div id="main" role="main">

			<!-- RIBBON -->
			<div id="ribbon">

				<span class="ribbon-button-alignment"> <span id="refresh" class="btn btn-ribbon" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true" data-reset-msg="Would you like to RESET all your saved widgets and clear LocalStorage?"><i class="fa fa-refresh"></i></span> </span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<!-- This is auto generated -->
				</ol>
				<!-- end breadcrumb -->

				<!-- You can also add more buttons to the
				ribbon for further usability

				Example below:

				<span class="ribbon-button-alignment pull-right">
				<span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
				<span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
				<span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
				</span> -->
				<div class="skins" id="smartSkins">
					<span class="skins-setting skins-toggle">
						<i class="fa fa-cog fa-lg"></i>
					</span>
					<section class="smart-styles">
						<span class="skin-change-close skins-toggle"><i class="fa fa-times"></i></span>
						<h6 class="skin-change-title">选择皮肤</h6>
						<a href="javascript:void(0);" id="smart-style-3" data-skinlogo="img/logo-pale.png"
					    class="a-skin btn btn-xs btn-block txt-color-white margin-top-5" style="background:#f78c40">
					        <i class="fa fa-check fa-fw" id="skin-checked"></i>Google Skin
					    </a>
					    <a href="javascript:void(0);" id="smart-style-0" data-skinlogo="img/logo.png"
					    class="a-skin btn btn-block btn-xs txt-color-white margin-right-5" style="background-color:#4E463F;">Smart Default</a>
					    <a href="javascript:void(0);" id="smart-style-1" data-skinlogo="img/logo-white.png"
					    class="a-skin btn btn-block btn-xs txt-color-white" style="background:#3A4558;">Dark Elegance</a>
					    <a href="javascript:void(0);" id="smart-style-2" data-skinlogo="img/logo-blue.png"
					    class="a-skin btn btn-xs btn-block txt-color-darken margin-top-5" style="background:#fff;">Ultra Light</a>
					</section>
				</div>
			</div>
			<!-- END RIBBON -->

			<!-- MAIN CONTENT -->
			<div id="content">

			</div>
			<!-- END MAIN CONTENT -->

		</div>
		<!-- END MAIN PANEL -->

		<!--================================================== -->

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)
		<script data-pace-options='{ "restartOnRequestAfter": true }' src="<%=request.getContextPath()%>/assets/js/plugin/pace/pace.min.js"></script>-->

		<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
		<script src="<%=request.getContextPath()%>/assets/js/libs/jquery-2.0.2.min.js"></script>
		<script src="<%=request.getContextPath()%>/assets/js/libs/jquery-ui-1.10.3.min.js"></script>
		<!-- JS TOUCH : include this plugin for mobile drag / drop touch events
		<script src="<%=request.getContextPath()%>/assets/js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->
		<!-- BOOTSTRAP JS -->
		<script src="<%=request.getContextPath()%>/assets/js/bootstrap/bootstrap.min.js"></script>
		<!-- JARVIS WIDGETS -->
		<script src="<%=request.getContextPath()%>/assets/js/smartwidgets/jarvis.widget.min.js"></script>
		<!-- CUSTOM NOTIFICATION -->
		<script src="<%=request.getContextPath()%>/assets/js/notification/SmartNotification.min.js"></script>
		<!--[if IE 7]>
		<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>
		<![endif]-->
		<!-- MAIN APP JS FILE -->
		<script src="<%=request.getContextPath()%>/assets/js/app.js"></script>

	</body>

</html>