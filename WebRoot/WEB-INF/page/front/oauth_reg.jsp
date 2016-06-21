<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<%@include file="/include/head.jsp"%>
<link rel="stylesheet" href="framework/nice-validator-0.7.3/jquery.validator.css">
<style type="text/css">
.wrap .noacount {
	border-right: 1px solid #eee;
}

.wrap .noacount h5 {
	margin: 10px;
}

.wrap .noacount .content {
	border-top: 1px solid #eee;
}

.wrap .noacount .content form {
	margin: 30px 100px;
}

.wrap .noacount .content form div {
	margin-top: 20px;
}

.wrap .acount {
	
}

.goLogin {
	font-size: 13px;
	color: #00a2ff;
	text-decoration: underline;
}

.userContainer {
	background: #fff;
	border-radius: 3px;
	box-shadow: 2px 2px 2px #d9d9d9;
}

.gd {
	width: 158px;
	float: left;
	border-radius: 3px;
	border: 1px solid #ccc;
}

.gd input {
	border: 0;
	color: #666;
	width: 134px;
	line-height: 38px;
	height: 38px;
	margin-left: 18px;
}

.refurbishBtn {
	width: 20px;
	height: 22px;
	float: left;
	margin: 7px 0px 0px 14px;
	display: inline;
}

.registerLeft {
	width: 607px;
	float: left
}

.ct {
	text-align: center;
	margin-top: 18px;
}

.inputBorder {
	width: 310px;
	height: 38px;
	line-height: 38px;
	border: 1px solid #cccccc;
	border-radius: 3px;
	float: left
}

div.inputBorderH {
	border: 1px solid #00b0ec;
}

.ml145 li {
	margin-top: 32px;
}

.ml145 li.mt24 {
	margin-top: 24px;
}

.ml145 li.mt15 {
	margin-top: 15px;
}

.ml145 {
	margin-left: 145px;
}

.ml145 li i,.rightIcon,.wrongIcon,.pwdBtn,.quickLogin span,.invite em,.wrongIconMax,.pwdBtnOpen
	{
	background: url(images/userV2/icon.png) no-repeat no-repeat;
	margin: 8px 18px 0px 18px;
}

.ml145 li i.registerIcon1 {
	background-position: 0 0;
	width: 16px;
	height: 24px;
	float: left;
}

.ml145 li i.registerIcon2 {
	background-position: 0 -40px;
	width: 19px;
	height: 17px;
	margin-left: 19px
}

.ml145 li i.registerIcon3 {
	background-position: 0 -72px;;
	width: 19px;
	height: 21px;
	margin-left: 19px
}

.ml145 li i.registerIcon4 {
	background-position: 0 -105px;
	width: 19px;
	height: 14px;
}

.input1 {
	width: 250px;
}

.input2 {
	width: 150px;
}

.input3 {
	width: 190px;
}

.input4 {
	width: 250px;
}

.rightIcon {
	background-position: 0 -131px;
	width: 17px;
	height: 12px;
	float: left;
	margin-top: 10px
}

span.wrongIcon {
	background-position: 0 -156px;
	height: 20px;
	margin: 10px 10px 0;
	float: left;
	text-indent: 30px;
	line-height: 20px;
	color: #666;
	width: 128px
}

span.wrongIconMax {
	background-position: 0 -146px;
	height: 30px;
	padding-left: 30px;
	margin-top: 0;
	width: 100px;
	text-indent: 0
}

.codeBtn {
	width: 90px;
	height: 30px;
	line-height: 30px;
	border-radius: 3px;
	background: #e4e4e4;
	color: #999899;
	display: inline-block;
	text-align: center;
	cursor: pointer;
}

.codeBtn:hover {
	background: #00b0ec;
	color: #fff;
}

.pwdBtn {
	background-position: 0 -186px;
	width: 20px;
	height: 16px;
	cursor: pointer;
	float: right;
	margin: 12px 10px 0 0
}

.pwdBtnOpen {
	background-position: 0 -211px;
	width: 20px;
	height: 16px;
	margin: 12px 10px 0 0;
	float: right;
}

.invite {
	font-size: 14px;
	color: #00a0e9
}

.inviteDiv {
	float: left;
	cursor: pointer;
}

.invite em {
	background-position: 0 -292px;
	width: 10px;
	height: 6px;
	margin: 0 5px
}

.invite em.open {
	background-position: 0 -278px;
}



.ml145 li.protocol {
	color: #666;
	margin: 15px 0 48px
}

.ml145 li.protocol input {
	line-height: 0;
	height: auto;
	vertical-align: -2px;
	margin-right: 5px
}

.protocol a {
	text-decoration: underline;
	color: #666;
}

.protocol a:hover {
	color: #00b0ec
}

.bottom {
	color: #999;
	text-align: center;
	margin: 40px 0 30px
}

.ml145 li input.fontTrue {
	color: #333
}

.pos {
	position: relative;
}

.smsDiv {
	font-size: 12px;
	color: #666;
	position: absolute;
	width: 100%;
	letter-spacing: 1px;
	top: 47px;
	left: 1px
}

.smsDiv a {
	color: #00b0ec;
	text-decoration: underline;
}

.smsDiv em {
	font-style: normal;
}

.alertBox {
	width: 500px;
	background: #fffce6;
	display: none;
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: 9999999;
	border-radius: 5px
}

.alertBox h2 {
	font-size: 16px;
	height: 42px;
	line-height: 42px;
	font-weight: 600;
	margin-bottom: 37px;
	text-align: left;
	padding-left: 20px;
	color: #0a0a0a;
	border-bottom: 1px solid #c9c9c9;
	position: relative;
}

.alertBox h2 span {
	background: url(../images/project/check_close.png) no-repeat center
		center;
	height: 42px;
	width: 42px;
	display: inline-block;
	border-left: 1px solid #ececec;
	cursor: pointer;
	position: absolute;
	right: 0px;
	top: 0px;
}

.padding20 {
	padding-bottom: 37px;
	text-align: center;
}

.registerLeft {
	width: 550px;
	float: left
}

.ct {
	text-align: center;
	margin-top: 18px;
}

.inputBorder {
	width: 310px;
	height: 38px;
	line-height: 38px;
	border: 1px solid #cccccc;
	overflow: hidden;
	border-radius: 3px;
	float: left;
	position: relative;
}

.ml145 li {
	margin-top: 32px;
}

.ml145 li.mt24 {
	margin-top: 24px;
}

.ml145 li.mt15 {
	margin-top: 15px;
}

.ml145 {
	margin-left: 145px;
}

.ml145 li i,.rightIcon,.wrongIcon,.resetVal {
	background: url(images/userV2/icon.png) no-repeat no-repeat;
	margin: 8px 18px 0px 18px;
	display: inline;
}

span.resetVal {
	width: 16px;
	height: 16px;
	margin: 0;
	background-position: 0 -304px;
	position: absolute;
	top: 11px;
	right: 11px;
	cursor: pointer;
	display: none
}

span.resetValCode {
	right: 98px;
}

span.resetValPwd {
	right: 40px;
}

.ml145 li i {
	float: left;
}

.ml145 li i.registerIcon1 {
	background-position: 0 0;
	width: 16px;
	height: 24px;
}

.ml145 li i.registerIcon2 {
	background-position: 0 -40px;
	width: 19px;
	height: 17px;
	margin-left: 19px
}

.ml145 li i.registerIcon3 {
	background-position: 0 -72px;;
	width: 19px;
	height: 21px;
	margin-left: 19px
}

.ml145 li i.registerIcon4 {
	background-position: 0 -105px;
	width: 19px;
	height: 14px;
}

.ml145 li input {
	border: 0 none;
	height: 37px;
	line-height: 37px;
	float: left;
	color: #666;
	font-size: 13px;
	padding: 0;
	outline: none;
}

.input1 {
	width: 222px;
}

.input2 {
	width: 135px;
}

.input3 {
	width: 190px;
}

.input4 {
	width: 250px;
}

.rightIcon {
	background-position: 0 -131px;
	width: 17px;
	height: 12px;
	float: left;
	margin-top: 10px
}

.wrongIcon {
	background-position: 0 -156px;
	height: 20px;
	margin-top: 10px;
	float: left;
	text-indent: 30px;
	line-height: 20px;
	color: #666
}

.codeBtn {
	width: 100px;
	height: 30px;
	line-height: 30px;
	border-radius: 3px;
	background: #e4e4e4;
	color: #999899;
	float: left;
	text-align: center;
	cursor: pointer;
	float: right;
	margin: 5px 5px 0 0
}

.codeBtn:hover {
	background: #00b0ec;
	color: #fff;
}

.loginSuccess {
	text-align: center;
	font-size: 14px;
	margin-top: 18px;
	line-height: 24px
}

.registerLeft2 {
	width: 546px;
	float: left
}

.registerLeft2>a {
	margin-left: 152px;
}

a.reglink {
	display: block;
	width: 224px;
	height: 40px;
	line-height: 40px;
	margin-top: 12px;
	background: #00b0ec;
	color: #fff;
	border-radius: 4px;
	text-align: center;
	font-size: 16px;
	letter-spacing: 2px
}

a.reglink:hover {
	background: #33cafd;
}

a#hfnotice {
	display: block;
	width: 224px;
	height: 40px;
	line-height: 40px;
	color: #00baff;
	text-decoration: underline;
}

.ctimgbox {
	width: 510px;
	height: 290px;
	margin: 0 auto
}

.registerRight2 {
	width: 362px;
	height: 436px;
	float: left;
	margin-left: 62px;
	padding-top: 18px;
}

.registerLeft2 a.projectlink {
	margin-top: 22px
}

.myindex03 .tg a {
	float: none;
	color: #269fd2
}

.chargeAdBox {
	width: 775px;
	height: 54px;
}

.myindex_right .yinker_rechargeable {
	margin-top: 5px;
}

.myindex_right .yinker_rechargeable .card_1 .paybtn a {
	width: 222px;
	height: 40px;
	border-radius: 3px;
	letter-spacing: 2px;
	float: left;
	line-height: 40px;
	font-size: 16px;
}

.myindex_right .yinker_rechargeable .card_1 .paybtn a.golist {
	background: none;
	width: auto;
	font-size: 12px;
	color: #666;
	letter-spacing: 0;
	text-decoration: underline;
	margin-left: 18px;
}

.myindex_right .yinker_rechargeable .card_1 .paybtn a.golist:hover {
	color: #00b8ff
}

.paybtn {
	height: 40px;
}

.myindex_right .yinker_rechargeable .card_1 .p4 {
	height: 38px;
	line-height: 38px;
}

.myindex_right .yinker_rechargeable .card_1 .p4 input {
	height: 36px;
	line-height: 36px;
	border: 1px solid #2fa8d7;
	width: 260px;
	border-radius: 3px;
	padding: 0;
	padding-left: 2px;
}

.hint_2 {
	margin-left: 60px;
	margin-top: 14px;
	color: #333333;
	font-size: 12px;
}
</style>
</head>

<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wrap">
		<div class="clearfix w950 mt20 mb20">
			<div class="clearfix userContainer">
				<div class="clearfix">
					<div class="registerLeft">

						<div class="loginSuccess">
							<div>
								<font style="color: #2ea4d2;">${nickname}</font>
								，您好，请完善您的个人信息，以后就可以直接登录三好资本了！<br> 已有三好资本账户，点击<a
									
									style="color: #00afec; text-decoration: underline;cursor: pointer;"  data-toggle="modal" 
   data-target="#myModal">快速绑定</a>。
							</div>
						</div>

	<form  method="post" autocomplete="off" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
						<ul class="ml145">
							<li class="clearfix mt24">
								<div class="inputBorder">
									<i class="registerIcon1"></i> <input type="text"
										class="input1 formInput" tabindex="1" maxlength="11" data-rule="手机号:required;mobile;remote[ajaxNewCheckRegister.mht];" data-ok=""  name="paramMap.username" id="cellphone" placeholder="请输入您的手机号"> 
								</div> 
								<span class="msg-box n-right" for="cellphone"></span>
							</li>
							
							<li class="clearfix">
								<div class="inputBorder">
									<i class="registerIcon3"></i> <input type="password"
										class="input3 pwdInput" tabindex="2"  name="paramMap.password" data-ok="" maxlength="16" id="password" tabindex="2" data-rule="密码:required;password" placeholder="请输入您的登录密码"
										id="pwdText" > 
								</div>
								<span class="msg-box n-right" for="password"></span>
							</li>
							
							<li class="clearfix">
								<div class="inputBorder">
									<i class="registerIcon3"></i> <input  type="password"
										class="input3 pwdInput" tabindex="3" name="paramMap.confirmPassword" maxlength="16" data-ok="" id="confirmPassword" data-rule="确认密码:required;password;match(paramMap.password);" placeholder="请再次输入登录密码" > 
								</div>
								<span class="msg-box n-right" for="confirmPassword"></span>
							</li>
							<!-- 添加验证码 -->
							<li class="clearfix">
								<div class="bg gd">
									<input type="text" name="loanCommentCode" class="f_zcb"
										id="loanCommentCode" tabindex="4"  placeholder="请输入验证码" data-ok="" maxlength="4"  data-rule="验证码:required;">

								</div> <img alt="验证码" title="验证码" id="loanCommentCodeId"  width="88px" 
								height="38px"
								style="margin-left:16px; border:1px solid #999798; float:left; cursor:pointer;"
								src="imageCode.mht?pageId=otherreg&time=<%=new java.util.Date().getTime() %>" onclick="switchCode()"> <a
								height="13px" width="16px" class="refurbishBtn"
								onclick="switchCode()"> <img
									src="images/userV2/freshVerifyCode.jpg" style="cursor: pointer;">
							</a> 
							<span class="msg-box n-right" for="loanCommentCode"></span>
							</li>
							<li class="clearfix invite"><div class="inviteDiv">
									<u>邀请人</u>（选填）<em></em>
								</div></li>
							<li class="clearfix mt15"  id="invite">
								<div class="inputBorder">
									<i class="registerIcon4"></i> <input type="text"
										class="input4 formInput" placeholder="请输入邀请人用户名"tabindex="5" data-rule="remote[queryValidRecommer.mht];" name="paramMap.refferee" data-ok="" id="refferee" > 
								</div> 
								<span class="msg-box n-right" for="refferee"></span>
							</li>
							<li class="clearfix"><button href="javascript:void(0);"
								class="login_button w250 submitFun">确定</button></li>
							<li class="clearfix protocol"><label><input
									type="checkbox" checked="" disabled="disabled" id="agree">同意三好资本</label> <a
								href="querytips.mht" id="querytip1" class="fancybox.ajax" target="_black">《使用条款》</a>、<a
								href="querytip.mht" id="querytip2" class="fancybox.ajax"  target="_black">《隐私条款》</a>
							</li>
						</ul>
						</form>
					</div>
					<div id="divArea" class="fl">
						<p></p>
						<p></p>
						<p></p>
						<p></p>
						<p></p>
						<p>
							<br>
						</p>
						<p>
							<br>
						</p>
						<p>
							<br>
						</p>
						<p>
							<img
								src="images/log_pic.png" width="381px"
								alt="" border="0"> 
						</p>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<!-- 模态框（Modal） -->
<div class="modal fade h" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               绑定三好资本帐号
            </h4>
         </div>
         <div class="modal-body">
				<form id="relationAcount" class="text-center"   method="post" autocomplete="off" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
					<div>用  户  名：<input id="username" data-ok="" data-rule="用户名/手机号码/邮箱:required;" placeholder="请输入您的用户名/手机/邮箱" class="input w200" type="text" /></div>
					<br/>
					<div>登录密码：<input id="password"  data-ok="" data-rule="登录密码:required;" placeholder="请输入您的登录密码" class="input w200"  type="password" /></div>
				</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
            <button type="button"  onclick="relationAcount()" class="btn btn-danger">
               绑定帐号
            </button>
         </div>
      </div><!-- /.modal-content -->
</div>
	</div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="framework/nice-validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="framework/nice-validator-0.7.3/local/zh_CN.js"></script>
<script type="text/javascript">
$('form').on('valid.form', function(e, form){
    register();
});
var options ={
	fitToView	: false,
	width		: '90%',
	height		: '90%',
	autoSize	: false,
	closeClick	: false,
	openEffect	: 'none',
	closeEffect	: 'none'
};
$("#querytip1").fancybox(options);
$("#querytip2").fancybox(options);


		function relationAcount() {
			var form = $("#relationAcount");
			var param = {};
			var request = GetRequest();
			param["paramMap.username"] = form.find("#username").val();
			param["paramMap.password"] = form.find("#password").val();
			$.get("relationAccount.mht", param, function(data) {
				if (data == 1) {
					window.location.href = "home.mht";
				} else if(data==3){
					alert("绑定失败，参数丢失，请重新操作");
				}else{
					alert("绑定失败，请检查您输入的帐号跟密码是否有误，再重新绑定。");
				}
			})
		}
		
		function register(){
			param["paramMap.username"]=$("#cellphone").val();
			param["paramMap.password"]=$("#password").val();
			param["paramMap.confirmPassword"]=$("#confirmPassword").val();
			param["paramMap.code"]=$("#loanCommentCode").val();
			param["paramMap.ref"]=$("#refferee").val();
			$.post("registerOther.mht", param, function(data) {
				if (data==11) {
					show("请您输入正确的手机号码")
				}else if (data==12) {
					show("请您输入正确的密码")
				}else if (data==13) {
					show("请输入确认密码")
				}else if (data==14) {
					show("两次密码不一致，请重新输入")
				} else if (data==15) {
					show("授权信息已过期，请重新登录")
				} else if (data==16) {
					show("验证码有误，请重新输入")
				} else if (data==17) {
					show("注册失败，请联系客服人员")
				}else if (data==18) {
					show("推荐人不存在，请重新输入")
				}else if (data==19) {
					show("您输入的手机号已存在，请重新输入")
				} else if (data==3) {
					show("授权信息已过期，请重新登录")
				} else {
					show("恭喜您，注册成功",function(){window.location.href="home.mht"});
				}
			})
		}

		
		//初始化
		function switchCode(){
			var timenow = new Date();
			$("#loanCommentCodeId").attr("src","admin/imageCode.mht?pageId=otherreg&d="+timenow);
		}
</script>

</body>
</html>
