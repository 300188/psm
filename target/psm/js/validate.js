
var validate = {

    //获取验证码图片
    changeImg : function(){
        $("#image").attr("src","/validateCodeServlet?date="+new Date().valueOf());
    },
    //异步登录
    AJAXLogin:function(validateCode,loginAccount,password,remember){
        $(function () {
            $.post("/loginServlet",{'validateCode':validateCode,'loginAccount':loginAccount,'password':password,'remember':remember}, function(msg){
                // console.log(msg);
                var result = $.parseJSON( msg );
                console.log(result);
                if(result['success']){
                    window.location.href = "/toMainPage"
                }else{
                    layer.msg(result['data'],{icon:5,time:1500});
                }
            });
        })
    },
    //cookie检验
    checkLogin:function(){

        var loginAccount = $.cookie('loginAccount');
        var password = $.cookie('password');
        if(null!=loginAccount&&null!=password&&loginAccount!=""&&password!=""){
            // console.log("yes");
            var loginAccount = $("#loginAccount").val(loginAccount);
            var password = $("#password").val(password);
            $("#remember").prop("checked",true);
        }else{
            $("#remember").attr("checked",false);
        }


    },
    //登录校验
    loginValidate: function () {
            var validateCode = $("#validateCode").val();
            var loginAccount = $("#loginAccount").val();
            var password = $("#password").val();
            var remember = $("#remember").is(':checked').toString();
            // console.log(remember.toString());
            if ( loginAccount== null || loginAccount == "" || loginAccount.length == 0) {
                layer.msg('用户名不能为空!',{icon:5,time:1000});
                return false;
            }
            else if ( password== null || password == "" || password.length == 0) {
                layer.msg('密码不能为空!',{icon:5,time:1000});
                return false;
            }
            else if ( validateCode== null || validateCode == "" || validateCode.length == 0) {
                layer.msg('验证码不能为空!',{icon:5,time:1000});
                return false;
            }else{
                this.AJAXLogin(validateCode,loginAccount,password,remember);
                return false;
            }
    }

}