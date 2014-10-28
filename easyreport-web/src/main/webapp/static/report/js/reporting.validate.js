var ReportingValidate = function() {
};

ReportingValidate.configOptions = function() {
	$("#reportingForm").validate({
		rules : {
			dsId : "required",
			layoutType : "required",
			name : {
				required : true,
				maxlength : 50
			},
			dataRange : {
				required : true,
				digits : true,
				maxlength : 3
			},
			sequence : {
				required : true,
				digits : true,
				maxlength : 3
			},
			sqlText : "required"
		},
		messages : {
			dsId : "",
			layoutType : "",
			name : {
				required : "",
				maxlength : ""
			},
			dataRange : {
				required : "",
				digits : "",
				maxlength : ""
			},
			sequence : {
				required : "",
				digits : "",
				maxlength : ""
			},
			sqlText : ""
		}
	});
};

ReportingValidate.queryParamOptions = function() {
	$("#queryParamForm").validate({
		rules : {
			name : "required",
			text : "required"
		},
		messages : {
			name : "",
			text : ""
		}
	});
};