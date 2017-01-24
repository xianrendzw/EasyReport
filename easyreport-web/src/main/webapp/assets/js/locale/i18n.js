var app = angular.module("i18n", []);
app.controller('translate',function($scope) {
	var userLang = navigator.language || navigator.userLanguage;
	var lang='en_US';
	if(userLang.indexOf('zh')>=0){
		lang='zh_CN';
	}
	$scope.info = null;
	jQuery.i18n.properties({
		name : 'messageResources', 
		language : lang, 
		path : EasyReport.ctxPath +'/assets/js/locale/', 
		mode : 'map', 
		callback : function() {
			$scope.info = $.i18n.map;
		}
	});
});