<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>三好资本</title>
<meta name="renderer" content="webkit"><%--360极速渲染模式 --%>
<meta name="keywords" content="三好资本,三好资本,P2P理财,P2P网贷,个人理财,个人贷款,信贷,投资理财平台,华融金控,华融金融,华融金融集团,华融融资担保,兴华融网络科技,伦顿资本投资,易付通投资管理,华融金融超市,sanhaodai,sanhao capital,HUARONG,Huarong Payment,Huarong Financial,seaou,seaoudesign,Huarong Guarantee," />
<meta name="description" content="三好资本,基金,私募,债权,为中小企业及个人客户提供专业,可信赖的投融资，理财，贷款，信贷服务,助您财富实现增值！" />
<meta name="copyright" content="三好资本(深圳市华融金融服务有限公司）" />
<link rel="stylesheet" href="css/public.css" />
<link rel="stylesheet" href="css/index.css" />
<link rel="stylesheet" href="css/swiper.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/swiper.min.js"></script>
<script src="js/marquee.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/page/fund/include/top.jsp"/>
<div class="main_banner">

<div class="swiper-container">
    <div class="swiper-wrapper">
    	<s:iterator value="#request.bannerList" var="banner" status="index">			
			<s:if test="#index.index<4">
				<div class="swiper-slide" style="background: url(./${banner.companyImg}) center top no-repeat;"><a href="${banner.companyURL}"></a></div>
			</s:if>
		</s:iterator>
    </div>
    <div class="swiper-pagination"></div>
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
</div>
<div class="login_box" >
<s:if test="#session.user == null">
<h3>证监会批准的独立基金销售机构</h3>
<p><input name="id" type="text" id="email" placeholder="用户名/手机号/邮箱"></p>
<p><input name="pwd" type="password" id="password" placeholder="登录密码"></p>
<p><input name="code" style="width: 44%;" type="text" id="code_top" placeholder="验证码"  onkeydown="keylogin(event);"><img onclick="switchCode()" id="codeNum" style="width: 40%;"  alt="" src="imageCode.mht?pageId=userlogin&time=<%=new java.util.Date().getTime() %>"></p>
<p onclick="login();" ><span class="login_btn" id="btn_login">立即登录</span></p>
<p><a href="forgetpassword.mht">忘记密码</a><a href="reg.mht">免费注册</a></p>
</s:if>
<s:else>
<div style="margin-top:20px;">
<p style="text-align: left;font-size: 22px;padding-left: 10px;">欢迎回来</p>
<p style="text-align: left;font-size: 16px;padding-left: 10px;">用户名：${user.userName}</p>
<p style="text-align: left;font-size: 16px;padding-left: 10px;">可用余额：<span style="color: #e87371;font-size: 20px;">${paramMap.usableSum}</span></p>
<p><a href="home.mht"><span class="login_btn">我的账户</span></a></p>
<p><a href="rechargeInit.mht" style="text-align: left;font-size: 15px;margin-top: 15px;margin-left:0px;">我要充值</a><a href="withdrawLoad.mht" style="text-align: left;font-size: 15px;margin-top: 15px;">我要提现</a></p>
</div>
</s:else>
</div>

</div>

<div class="news_bar">
<div class="w1180">
<span class="news_icon"></span>
<div class="news_list">
<ul>
<s:iterator  value="#request.newsList" var="new"	status="sts">
<li>
  <a href="frontNewsDetails.mht?id=${new.id}">${new.title}</a>
</li>    
</s:iterator>   
</ul>
</div>
</div>
</div>

<div class="benefit_wrap">

<div class="w1180">
<ul>
<li>
<a href="#.html">
<span class="b_bank"></span>
民生银行托管
</a>
</li>
<li>
<a href="#.html">
<span class="b_gov"></span>
国企战略参股
</a>
</li>
<li>
<a href="#.html">
<span class="b_security"></span>
证监会批准
</a>
</li>

</ul>

<dl>
<dt>运营时间：</dt><dd><span id="dayt"></span>天</dd>
<dt>累计成交：</dt><dd>${investMap.totalInvestment }</dd>
<dt>注册会员：</dt><dd>${investMap.regAcount}&nbsp;人</dd>
</dl>
</div>

</div>


<div class="good_funds">
<div class="good_funds_title">
<h2>优选产品</h2>
</div>

<div class="good_funds_con">

<ul>

<li>
<h3>${pageBeanBest[0].borrowTitle}</h3>
<p><span class="tags">借款金额：${pageBeanBest[0].borrowAmount}</span></p>
<h2><span>年化利率</span>${pageBeanBest[0].annualRate}%</h2>
<dl>
<dt>可投金额</dt>
<dd>${pageBeanBest[0].investNum}</dd>
</dl>
<dl>
<dt>投资期限</dt>
<dd><i>${pageBeanBest[0].deadline}个月</i></dd>
</dl>
<p><a href="financeDetail.mht?id=${pageBeanBest[0].id}" class="invest_btn">立即抢购</a></p>
<span class="pd_label">债权</span>
</li>


<li>
<h3>平安大华保本混合型  700004</h3>
<p><span class="tags">单日投资无限额</span></p>
<h2><span>最新净值</span>1.2570</h2>
<dl>
<dt>申购费率</dt>
<dd>0.08%</dd>
</dl>
<dl>
<dt>近三个月收益</dt>
<dd>1.58%</dd>
</dl>
<p><a href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes" class="invest_btn"   target="_blank">预约该产品</a></p>
<span class="pd_label">公募</span>
</li>
<li>
<h3>伦顿资本定增1号</h3>
<p class="p2"><span class="tags">定增</span></p>
<dl>
<dt>购买起点</dt>
<dd><i>100万元</i></dd>
</dl>
<dl>
<dt>产品期限</dt>
<dd>1年投资期</dd>
</dl>
<p>把握历史机遇，坐享高折价优质上市公司</p>
<p><a href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes" class="invest_btn"   target="_blank">预约该产品</a></p>
<span class="pd_label">私募</span>
</li>
</ul>

</div>

</div>

<div class="clear"></div>
<div class="ad_banner" style="display: block !important">
<a href="paytreasuredetail.mht"><img src="images/index/zxb_banner.jpg"/></a>
</div>

<div class="pd_wrap">
<div class="nav">
<ul>
<li><a href="#con02">优质债权</a></li>
<li><a class="current" href="#con01">公募基金</a></li>
<li><a href="#con03">高端理财</a></li>
</ul>
</div>
<div class="pd_cons">

<div class="con" id="con01">
<ul>
<li>
<a class="invest_btn"  href="public_funds_detail.mht">立即投资</a>
<div class="num">1</div>
<h3>平安大华保本混合【700004】</h3>
<p>
<span>近一周收益<em>25.33%</em></span>
<span>近三个月收益<em>25.33%</em></span>
<span>近一年收益<b>36.30%</b></span>
<span>类型<b>混合型</b></span>
<span>申购费率<b>1.5%</b></span>
</p>
</li>
<li>
<a class="invest_btn " href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<div class="num">2</div>
<h3>平安大华策略先锋混合【700003】</h3>
<p>
<span>近一周收益<em>83.98%</em></span>
<span>近三个月收益<em>78.29%</em></span>
<span>近一年收益<b>105.03%</b></span>
<span>类型<b>混合型</b></span>
<span>申购费率<b>1.5%</b></span>
</p>
</li>
<li>
<a class="invest_btn " href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<div class="num">3</div>
<h3>平安大华行业先锋混合【700001】</h3>
<p>
<span>近一周收益<em>50.21%</em></span>
<span>近三个月收益<em>57.90%</em></span>
<span>近一年收益<b>108.10%</b></span>
<span>类型<b>混合型</b></span>
<span>申购费率<b>1.5%</b></span>
</p>
</li>
<li>
<a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<div class="num gray_num">4</div>
<h3>平安大华新鑫先锋A【000739】</h3>
<p>
<span>近一周收益<em>19.80%</em></span>
<span>近三个月收益<em>20.30%</em></span>
<span>近一年收益<b>40.10%</b></span>
<span>类型<b>混合型</b></span>
<span>申购费率<b>1.5%</b></span>
</p>
</li>
<li>
<a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<div class="num gray_num">5</div>
<h3>平安大华新鑫先锋C【001515】</h3>
<p>
<span>近一周收益<em style="color:#50883D">-32.88%</em></span>
<span>近三个月收益<em style="color:#50883D">-31.92%</em></span>
<span>近一年收益<b>-15.39%</b></span>
<span>类型<b>混合型</b></span>
<span>申购费率<b>1.5%</b></span>
</p>
</li>
</ul>
</div>


<div class="con" id="con02">
<ul>
<s:if test="#request.pageBeanList.size>0">
<s:iterator value="#request.pageBeanList" status="index" var="finance" begin="0" end="4">
<li>

<s:if test="%{#finance.borrowStatus == 1}">
			             <a class="invest_btn" style="padding: 2px;"   target="_blank">初审中</a>
				        </s:if>
				         <s:elseif test="%{#finance.borrowStatus == 2}">
				         	   <a class="invest_btn" href="financeDetail.mht?id=${finance.id}">立即投资</a>    
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 3}">
				            <a class="invest_btn  invest_btn_over" style="padding: 2px;"   target="_blank">复审中</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 4}">
				            <a class="invest_btn  invest_btn_over" style="padding: 2px;"   target="_blank">
				            <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
				          	<s:else>已满标</s:else>
				          	</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 5}">
				           <a class="invest_btn  invest_btn_over" style="padding: 2px;"   target="_blank">已还完</a>
				        </s:elseif>
				        <s:else>
				              <a class="invest_btn  invest_btn_over" style="padding: 2px;"   target="_blank">流标</a>
				        </s:else>

<s:if test="#index.index>=3">
<div class="num gray_num">${index.index+1}</div>
</s:if>
<s:else>
<div class="num">${index.index+1}</div>
</s:else>

<h3><a href="financeDetail.mht?id=${finance.id}"  target="_blank">${finance.borrowTitle }</a></h3>
<p>
<span>年化收益率<em><s:property value="%{#finance.annualRate}" default="0"/>%</em></span>
<span>投资期限<b><s:property value="#finance.deadline" default="0"/><s:if test="%{#finance.isDayThe ==1}">个月</s:if>
			            	<s:else>天</s:else></b></span>
<span>借款金额<em>${finance.borrowAmount}</em></span>
<span>还款方式<b><s:if test="%{#finance.paymentMode == 1}">按月分期还款(等额本息还款)</s:if>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 2}">按月付息，到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></b></span>
<span>进度<em>${finance.schedules}%</em></span>
</p>
</li>
</s:iterator>
</s:if>
</ul>
</div>

<div class="con" id="con03">
<div class="pd_item">
<a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<img src="images/hefm/list_project_pic01.jpg">
<h4>伦顿1号私募</h4>
<p>
世界那么大，你该去看看，海外基金“大奖章”20年间大奖章基金平均年回报率高达35%，是华尔街远近闻名的“印钞机”，也许你错过了他，但这次不能再错过，海外基金，规避风险，投资放眼全球，寻找诗和远方。
</p>
</div>
<div class="pd_item">
<a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank">在线咨询</a>
<img src="images/hefm/list_project_pic02.jpg">
<h4>伦顿2号私募</h4>
<p>
世界那么大，你该去看看，海外基金“大奖章”20年间大奖章基金平均年回报率高达35%，是华尔街远近闻名的“印钞机”，也许你错过了他，但这次不能再错过，海外基金，规避风险，投资放眼全球，寻找诗和远方。
</p>
</div>
</div>

</div>

</div>

<div class="w1180">
<div class="news_wrap">

<div class="con">
<div class="title pt"><a href="queryAboutInfo.mht?msg=108" target="_blank">
<span><i></i>平台动态</span></a>
</div>
<s:iterator  value="#request.sanhaodaiQA" var="sanhaostick"	status="sts">
  <p><span class="date"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span><a href="frontMedialinkId.mht?id=${id}" target="_blank">${sanhaostick.title }</a></p>
</s:iterator>

</div>

<div class="con">
<div class="title hy"><a href="queryAboutInfo.mht?msg=101" target="_blank">
<span><i></i>行业资讯</span></a>
</div>

<s:iterator  value="#request.meikuInternetStick" var="sanhaostick"	status="sts">
<p><span class="date"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span><a href="frontMedialinkId.mht?id=${id}" target="_blank">${sanhaostick.title }</a></p>
</s:iterator>
</div>

</div>

<div class="app_box">
<img src="images/index/appcode.jpg">
</div>
</div>




<div class="clear"></div>
<!--不要删clear-->

<div class="license_box">
<ul>
<li>
<a class="qualification" href="frontNewsDetails.mht?id=508" >基金销售资格</a>
</li>
<li>
<a class="supervise" href="#.html">监督机构</a>
</li>
<li>
<a class="bank" href="#.html">监管银行</a>
</li>
<li>
<a class="self_discipline" href="#.html">自律组织</a>
</li>
</ul>

</div>

<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
<script type="text/javascript">
	//计算天数差的函数，通用  
	function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
	    var  aDate,  oDate1,  oDate2,  iDays  
	    aDate  =  sDate1.split("-")  
	    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
	    
	    var at = Date.UTC(aDate[0],aDate[1],aDate[2]);
	    
	    aDate  =  sDate2.split("-")  
	    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
	    var bt = Date.UTC(aDate[0],aDate[1],aDate[2])
	    iDays  =  parseInt(Math.abs(at  -  bt)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
	    return  iDays  
	}    
	$(function(){
		var sp = $("#dayt");
		var s1  =  "2014-08-28"  
		var  s2  =  new Date().getFullYear()+"-"+new Date().getMonth()+"-"+new Date().getUTCDate();  
		var time = DateDiff(s1, s2);
		sp.html((time/365).toFixed(0)+"年&nbsp;"+time%365+"&nbsp;")
	})
</script>

<script>
var mainbanner = new Swiper(".main_banner .swiper-container", {
	pagination: ".main_banner .swiper-pagination",
	paginationClickable: ".main_banner .swiper-pagination",
	nextButton: ".main_banner .swiper-button-next",
	prevButton: ".main_banner .swiper-button-prev",
	autoplay: 3000,
	autoplayDisableOnInteraction: true,
	centeredSlides: true,
	effect: "fade",
	loop: true
	});

$(".news_list").marquee({
    auto: true,
    interval: 3000,
    showNum: 1,
    stepLen: 1,
    type: 'vertical'
});

var pd_cons = $(".pd_wrap .pd_cons .con");
pd_cons.hide().filter(":first").show();

$(".pd_wrap .nav li a").click(function () {
	pd_cons.hide().stop().animate({top:"40px",opacity:"0"},"0.2");
	pd_cons.filter(this.hash).show().stop().animate({top:"0",opacity:"1"},"0.2");
	$(".pd_wrap .nav li a").removeClass("current");
	$(this).addClass("current");
	return false;
}).filter(":first").click();


	function keylogin(e) {
		var keynum
		var keychar
		var numcheck
		if (window.event) // IE
		{
			keynum = e.keyCode
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which
		}
		if(keynum==13){
			login();
		}
	}

	function login() {
		$(this).attr('disabled', true);
		if ($("#email").val() == "") {
			//$("#s_email").attr("class", "formtips onError");
			//$("#s_email").html("*请输用户名或邮箱地址");
			alert("请输入账号");
			return;
		}
		if ($("#password").val() == "") {
			//$("#s_password").attr("class", "formtips onError");
			//$("#s_password").html("*请输入密码");
			alert("请输入密码");
			return;
		}
		if ($("#code_top").val() == "") {
			//$("#s_password").attr("class", "formtips onError");
			//$("#s_password").html("*请输入密码");
			alert("请输入验证码");
			return;
		}
		$('#btn_login').text('登录中...');
		var param = {};
		param["paramMap.pageId"] = "userlogin";
		param["paramMap.email"] = $("#email").val();
		param["paramMap.password"] = $("#password").val();
		param["paramMap.code"] = $("#code_top").val();
		param["paramMap.afterLoginUrl"] = "${session.afterLoginUrl}";
		$.post("logining.mht", param, function(data) {
			if (data == 1) {
				//alert("登录成功！");
				window.location.reload();
				return;
			} else if (data == 2) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr("disabled", false);
				alert("验证码错误！");
			} else if (data == 3) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr('disabled', false);
				alert("用户名或密码错误！");
			} else if (data == 4) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr('disabled', false);
				alert("该用户已被禁用！");
			} else if (data == 8) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr('disabled', false);
				alert("用户名或密码错误!还有2次机会,您可以尝试找回密码");
			} else if (data == 9) {
				$('#btn_login').text('value', '立即登录');
				$("#btn_login").attr('disabled', false);
				alert("用户名或密码错误!还有1次机会,您可以尝试找回密码");
			} else if (data == 5) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr('disabled', false);
				alert("该用户已被限制登录，请于三小时以后登录！");
			} else if (data == 7) {
				$('#btn_login').text('立即登录');
				$("#btn_login").attr('disabled', false);
				alert("该用户账号出现异常，请速与管理员联系!");
			}
			$('#btn_login').text('立即登录');
			switchCode();
			//$('.refresh a').click();//更新验证码
		});
	}
	//初始化
	function switchCode() {
		var timenow = new Date();
		$("#codeNum").attr("src",
				"admin/imageCode.mht?pageId=userlogin&d=" + timenow);
	}
</script>
</body>
</html>
