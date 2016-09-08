MembershipLogin = {
    URL: {
        "login": XFrame.getContextPath() + '/membership/authenticate',
        "successUrl": XFrame.getContextPath() + '/home/index'
    },
    init: function () {
        $("#account").focus();
        document.onkeydown = function (e) {
            var evt = e ? e : (window.event ? window.event : null)
            if (evt.keyCode == 13) {
                MembershipLogin.login();
            }
        };
        $("#login-message-tips").hide();
    },
    tipsTimer: null,
    tips: function (type, content) {
        var tipsContainer = $('#login-message-tips');
        tipsContainer.html(content);
        tipsContainer.show();
    },
    login: function () {
        if ($('#login-form').validate().form()) {
            var postData = {
                "account": $("#account").val(),
                "password": $("#password").val(),
                "rememberMe": $("#rememberMe").prop("checked")
            };
            $.post(MembershipLogin.URL.login, postData, function (data) {
                if (!data.success) {
                    MembershipLogin.tips('error', data.msg);
                } else {
                    MembershipLogin.tips('success', '登录成功，正在跳转...');
                    location = MembershipLogin.URL.successUrl;
                }
            }, 'json');
        }
    }
};

$(function () {
    $("#login-form").validate({
        rules: {
            account: {
                required: true,
            },
            password: {
                required: true,
                minlength: 3,
                maxlength: 20
            }
        },
        messages: {
            account: {
                required: ''
            },
            password: {
                required: '',
                minlength: ''
            }
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });
    MembershipLogin.init();
});
