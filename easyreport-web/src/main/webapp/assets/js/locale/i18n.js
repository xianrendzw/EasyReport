var app = angular.module("i18n", []);
app.controller('translate',function($scope) {
	$scope.info = null;
	jQuery.i18n.properties({
		name : 'messageResources', 
		language : 'en_US', 
		path : EasyReport.ctxPath +'/assets/js/locale/', 
		mode : 'map', 
		callback : function() {
			$scope.info = $.i18n.map;
		}
	});
});