<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="clearfix"></div>
<div class="footer">
<div class="w950">
      <div class="one ">
    <div class="link">
          <ul>
        <li>
              <h5><a href="queryAboutInfo.mht?msg=99" target="_blank">关于我们</a></h5>
            </li>
        <li><a href="queryAboutInfo.mht?msg=110" target="_blank">运营主体</a></li>
        <li><a href="queryAboutInfo.mht?msg=111">业务模式</a></li>
        <li><a href="jg_zf.mht" target="_blank">资费说明</a></li>
      </ul>
          <ul>
        <li>
              <h5><a href="queryAboutInfo.mht?msg=101" target="_blank">平台资讯</a></h5>
            </li>
        <li><a href="queryAboutInfo.mht?msg=100" target="_blank">三好贷公告</a></li>
        <li><a href="queryAboutInfo.mht?msg=103" target="_blank">三好贷动态</a></li>
        <li><a href="queryAboutInfo.mht?msg=108" target="_blank">三好贷问答</a></li>
        
      </ul>
          <ul>
        <li>
              <h5><a href="queryAboutInfo.mht?msg=106" target="_blank">帮助中心</a></h5>
            </li>
        <li><a href="queryAboutInfo.mht?msg=106&amp;cid=1" target="_blank">新手入门</a></li>
        <li><a href="queryAboutInfo.mht?msg=106&amp;cid=3" target="_blank">我要投资</a></li>
        <li><a href="queryAboutInfo.mht?msg=106&amp;cid=8" target="_blank">我要提现</a></li>
      </ul>
        </div>
    <div class="phone">
          <ul>
        <li class="info">全国服务热线</li>
        <li class="content">${sitemap.servicePhone }</li>
        <li class="info">工作日</li>
        <li class="content">9:00-18:00</li>
      </ul>
        </div>
    <div class="weixin">
          <ul>
        <li class="info text-center">微信服务号</li>
        <li><img data-echo="images/wxgz.jpg" class="loadingImg" src="images/blank.gif"  width="90" height="90"></li>
      </ul>
        </div>
  </div>
      <div class="two">
    <div class="qy">
          <div class="tt">企业相关资质：</div>
          <ul>
        <li style="padding-left:10px;">
              <div><a class="single_image"   href="images/fz8.jpg" data-fancybox-group="gallery"> <img class="loadingImg" data-echo="images/fz_8.jpg" src="images/blank.gif" height="70" onmouseover="this.src='images/fz8.jpg'" onmouseout="this.src='images/fz_8.jpg'" title="三好贷"></a></div>
              <p>""中国平安"金融服务奖</p>
            </li>
         <li>
           <div><a class="single_image" href="images/fz1.jpg" data-fancybox-group="gallery"> <img class="loadingImg"  data-echo="images/fz_1.jpg" src="images/blank.gif"  height="70" onmouseover="this.src='images/fz1.jpg'" onmouseout="this.src='images/fz_1.jpg'" title="三好贷"></a></div>
           <p>中国十大AAA级信用担保机构</p>
         </li>
        <li>
              <div><a class="single_image" href="images/fz5.jpg" target="_blank" data-fancybox-group="gallery"> <img class="loadingImg"  data-echo="images/fz_5.jpg"  src="images/blank.gif"   height="70" onmouseover="this.src='images/fz5.jpg'" onmouseout="this.src='images/fz_5.jpg'" title="三好贷"></a></div>
              <p>企业法人营业执照</p>
            </li>
        <li>
              <div><a class="single_image" href="images/fz6.jpg" data-fancybox-group="gallery"> <img class="loadingImg"  data-echo="images/fz_6.jpg"  src="images/blank.gif"   height="70" onmouseover="this.src='images/fz6.jpg'" onmouseout="this.src='images/fz_6.jpg'" title="三好贷"></a></div>
              <p>组织机构代码证书</p>
            </li>
        <li>
              <div><a class="single_image" href="images/fz7.jpg" data-fancybox-group="gallery"> <img class="loadingImg"  data-echo="images/fz_7.jpg"  src="images/blank.gif"   height="70" onmouseover="this.src='images/fz7.jpg'" onmouseout="this.src='images/fz_7.jpg'" title="三好贷"></a></div>
              <p>税务登记证书</p>
            </li>
      </ul>
          <div class="clear"></div>
        </div>
  </div>
      <div class="three">
    <div class="link clearfix w600 fl"> 
    	  <a href="http://www.miitbeian.gov.cn/"   class="ico3" target="_blank"  ></a>
    	  <a href="https://search.szfw.org/cert/l/CX20140825008763005051" target="_blank" title=""  class="ico5" target="_blank" ></a>
		  <a href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&dn=www.sanhaodai.com&lang=zh_cn" target="_blank" title="" class="ico1"></a>
		  <a href="http://webscan.360.cn/index/checkwebsite?url=www.sanhaodai.com" target="_blank" title=""  class="ico6" target="_blank" ></a>
          <a href="http://www.cyberpolice.cn/wfjb/" target="_blank" title=""  class="ico7" target="_blank" ></a>
    </div>
    <div class="copyright">
          <p> ${sitemap.certificate }&nbsp;|&nbsp;©2015&nbsp;${sitemap.regionName }</p>
          <p> ${sitemap.companyName}版权所有</p>
        </div>
    <div class="clear"></div>
  </div>
  <div>
    </div>
</div>

<%-- 悬浮 --%>

<div id="suspend" style="right: 0px;" >
  <%--排行榜 start--%>
  <div class="suspend_phb" style="right: -245px;">
    <div class="phb_box">
        <div class="suspend_phb_jt"></div>
        <div class="suspend_phb_title mgt15">理财排行榜/ <span>TOP10</span></div>
        <div class="suspend_phb_select">
            <p>总</p>
            <ul class="suspend_phb_select_list">
                <li>日</li>
                <li>周</li>
                <li>月</li>
                <li>季</li>
                <li>年</li>
                <li>总</li>
            </ul>
            <input type="hidden" name="phbYear" autocomplete="off">
        </div>

        <ul class="suspend_phb_top">
          <li style="width:51px;">排名</li>
          <li style="width:65px;">用户名</li>
          <li style="width:106px;">投资金额</li>
        </ul>
        <div class="suspend_phb_main"><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no suspend_phb_no1">1</div></li><li style="width:65px;">188****88</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>5177621</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no suspend_phb_no1">2</div></li><li style="width:65px;">130****72</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>110000</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no suspend_phb_no1">3</div></li><li style="width:65px;">137****41</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>100000</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">4</div></li><li style="width:65px;">139****79</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>91126</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">5</div></li><li style="width:65px;">133****96</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>69498</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">6</div></li><li style="width:65px;">138****35</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>58503</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">7</div></li><li style="width:65px;">138****83</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>51360</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">8</div></li><li style="width:65px;">136****90</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>50001</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">9</div></li><li style="width:65px;">186****80</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>42500</li> </ul><ul class="clearfix"><li style="width:51px;"><div class="suspend_phb_no">10</div></li><li style="width:65px;">185****11</li><li style="width:106px;"><span class="suspend_phb_money">¥ </span>38332</li> </ul></div>

        <div class="suspend_phb_line"></div>
        <%--登录后--%>
        
        
        <%--登录后--%>
        <div class="suspend_phb_tel">
          <p class="p1">服务热线：</p>
          <p class="p2">${sitemap.servicePhone }</p>
        </div>
    </div>
  </div>
  <%--排行榜 end--%>

  <div class="suspend_charts"  ><span>理<br>财<br>排<br>行<br>榜<br></span><p class="pt10">&lt;&lt;</p></div>

  <%--登录后--%>
    
  <%--登录后--%>

  <%--登录前
  <div class="suspend_cclc"><div class="suspend_cclc_icon"></div></div>--%>
  <div class="suspend_mycclc" style="height:110px;" id="xuanfu_login">
    <div class="suspend_mycclc1">我<br>的<br>账<br>户</div>
    <%--xuanfu_login

    <div class="xuanfu_login">
      <div class="xuanfu_login_main">
        <div class="xuanfu_login_top"></div>

        <form class="xuanfu_login_con">
            <input name="_csrf" value="mjS809KujNlFBc5EbnnIhwfKI7NJX0GA4P9gE=" type="hidden" autocomplete="off">
            <input class="xuanfu_login_text" type="text" _check="mobile" placeholder="手机号" name="username" autocomplete="off"><input style="display:none;">
            <p class="tips"></p>
            <input class="xuanfu_login_text xuanfu_login_pwd" type="password" _check="password" placeholder="密码" name="password" autocomplete="off">
            <p class="tips"></p>
            <div class="xuanfu_login_link">
              <a href="/member/register">免费注册</a>
              <em>|</em>
              <a href="/member/reset">忘记密码？</a>
            </div>
            <input id="index_login" class="xuanfu_login_btn" type="button" value="" autocomplete="off">
        </form>

      </div>
    </div>

    xuanfu_login--%>
  </div>
  <%--登录前--%>
    
  <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2198380644&amp;site=qq&amp;menu=yes" target="_blank" class="suspend_link suspend_qq"><div class="suspend_link_main" style="opacity: 0; right: 50px; display: none;"><div class="suspend_link_text">在线客服</div><div class="suspend_link_jt"></div></div></a>
  <%--登录后--%>
    
    <%--登录后--%>
  <a href="homeBorrowRecycleList.mht" class="suspend_link suspend_lc"><div class="suspend_link_main" style="opacity: 0; right: 50px; display: none;"><div class="suspend_link_text">理财记录</div><div class="suspend_link_jt"></div></div></a>
  
  <a href="http://shang.qq.com/wpa/qunwpa?idkey=ea4a4f75c8a79ec4750deee62906864aacfd5173ac79d85ceda1adde71656eac"  target="_blank"  class="suspend_link suspend_qun"><div class="suspend_link_main" style="opacity: 0; right: 50px; display: none;"><div class="suspend_link_text">官方服务群</div><div class="suspend_link_jt"></div></div></a>
	
	
  <br><br><a  href="queryAboutInfo.mht?msg=106&js=1" class="white hover-white"><span>名<br>词<br>解<br>释<br></span></a>
  <%--<div class="suspend_link suspend_qun">
    <div class="suspend_link_main" style="opacity: 0; right: 60px; display: none;">
      <div class="suspend_link_text">
        <span><a href="http://shang.qq.com/wpa/qunwpa?idkey=ea4a4f75c8a79ec4750deee62906864aacfd5173ac79d85ceda1adde71656eac" target="_blank">官方服务群</a></span>
      </div>
      <div class="suspend_link_jt"></div>
    </div> 
  </div> --%>

  <div class="suspend_bottom">
    <div class="suspend_wechart"><div class="suspend_wechart_main" style="opacity: 0; top: -100px; height: 0px; display: none;"><img src="images/wxgz.jpg"></div></div>
    <a href="javascript:;" class="suspend_gotop"></a>
  </div>
</div>

<!--底部footer 结束-->
<!--[if !IE]><!-->
<script type="text/javascript" src="js/jquery.min.js?ver=20150803"></script>
<script type="text/javascript" src="framework/bootstrap-3.3.5/js/bootstrap.min.js?ver=20150803"></script>
<script type="text/javascript" src="framework/echo/echo.min.js"></script>
<script type="text/javascript">
	echo.init({
	    offset: 100,
	    throttle:0
	});
</script>
<script type="text/javascript" src="framework/placeholder-master/jquery.placeholder.js?ver=20150803"></script>
<!--<![endif]-->

<!--[if gte IE 8]>
<script type="text/javascript" src="js/jquery.min.js?ver=20150803"></script>
<script type="text/javascript" src="framework/bootstrap-3.3.5/js/bootstrap.min.js?ver=20150803"></script>
<script type="text/javascript" src="framework/echo/echo.min.js"></script>
<script type="text/javascript">
	echo.init({
	    offset: 100,
	    throttle:0
	});
</script>
<script type="text/javascript" src="framework/placeholder-master/jquery.placeholder.js?ver=20150803"></script>
<![endif]-->

<!--[if lt IE 8]>
  <script src="script/jquery-1.7.1.min.js?ver=20150803"></script>
<![endif]-->
<script type="text/javascript" src="script/jquery.dispatch.js?ver=201508031"></script>
<script type="text/javascript" src="js/top.js?ver=20150803"></script>
<script type="text/javascript" src="framework/fancyapps-fancyBox/lib/jquery.mousewheel-3.0.6.pack.js?ver=20150803"></script>
<script type="text/javascript" src="framework/fancyapps-fancyBox/source/jquery.fancybox.pack.js?ver=20150803"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$('.single_image').fancybox();
	});
</script>

<script>
function onlyValue(){
    var   guid   =   "";
    for (var  i = 1; i <= 32;i++)
    {
      var   n   =   Math.floor(Math.random()   *   16.0).toString(16);
      guid   +=   n;
    }
    return guid;
}
//读取cookies 
function getCookie(name)
{
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
  if(arr=document.cookie.match(reg))
      return unescape(arr[2]); 
  else 
      return null; 
}
function setCookie(name,value)
{
    value=onlyValue();
    var Days = 30;
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
}


$(function(){

    var clientIp = getCookie('clientIP');
    if (clientIp == null){
        setCookie("clientIP", '');
    }
    clientIp = getCookie('clientIP');
    var param = {};
    param["paramMap.mockClientIp"] = clientIp;
    $.dispatchPost("pvuvStatistic.mht",param,initCallBack2);
    
});
function initCallBack2(data){
}
</script>

<%-- <script type="text/javascript">
//右侧通用start
$(document).ready(function(e) {
	$("#rightButton").css("right", "0px");
	
    var button_toggle = true;
	$(".right_ico").live("mouseover", function(){
		var tip_top;
		var show= $(this).attr('show');
		var hide= $(this).attr('hide');
		tip_top = show == 'tel' ?  65 :  0;
		button_toggle = false;
		$("#right_tip").css("top" , tip_top).show().find(".flag_"+show).show();
		$(".flag_"+hide).hide();
		
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	
	$("#right_tip").live("mouseover", function(){
		button_toggle = false;
		$(this).show();
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	function hideRightTip(){
		setTimeout(function(){		
			if( button_toggle ) $("#right_tip").hide();
		}, 500);
	}
	
	$("#backToTop").live("click", function(){
		var _this = $(this);
		$('html,body').animate({ scrollTop: 0 }, 500 ,function(){
			_this.hide();
		});
	});

	$(window).scroll(function(){
		var htmlTop = $(document).scrollTop();
		if( htmlTop > 0){
			$("#backToTop").fadeIn();	
		}else{
			$("#backToTop").fadeOut();
		}
	});
});
//右侧通用end
</script>
 --%>

<%-- <script type="text/javascript">
function addCookie()
{
 if (document.all){
       window.external.addFavorite('<%=application.getAttribute("basePath")%>','三好贷');
    }
    else if (window.sidebar) {
       window.sidebar.addPanel('三好贷', '<%=application.getAttribute("basePath")%>', "");
    }else{
       alert('请手动设为首页');
    }
}

function setHomepage(){
    if (document.all){
        document.body.style.behavior='url(#default#homepage)';
        document.body.setHomePage('<%=application.getAttribute("basePath")%>');
    }else if (window.sidebar){
        if(window.netscape){
         try{  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
         }  
         catch (e)  
         {  
            alert( "该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true" );  
         }
    }else{
        alert('请手动添加收藏');
    }
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components. interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage','<%=application.getAttribute("basePath")%>');
 }
}


$(document).ready(function(e) {
	$("#rightButton").css("right", "0px");
	$("#backToTop").bind("click", function(){
		var _this = $(this);
		$('html,body').animate({ scrollTop: 0 }, 500 ,function(){
			_this.hide();
		});
	});

	$(window).scroll(function(){
		var htmlTop = $(document).scrollTop();
		if( htmlTop > 0){
			$("#backToTop").show();	
		}else{
			$("#backToTop").hide();
		}
	});
});

$(function(){
	$(window).scroll(function(){
		if($(window).scrollTop()>=109){
			$(".nav-zdh").css("position","fixed")
		}
		else{
		$(".nav-zdh").css("position","relative")
		}
	})
})

function login1(){     
	$(this).attr('disabled',true);
	if($("#email1").val()==""){   
        //$("#s_email").attr("class", "formtips onError");
        //$("#s_email").html("*请输用户名或邮箱地址");
        alert("请输入账号");
        return;
    }
    if($("#password1").val()==""){   
    	//$("#s_password").attr("class", "formtips onError");
        //$("#s_password").html("*请输入密码");
    	alert("请输入密码");
    	return;   
    }  
    if($("#code_top").val()==""){   
    	//$("#s_password").attr("class", "formtips onError");
        //$("#s_password").html("*请输入密码");
    	alert("请输入验证码");
    	return;   
    }  
	$('#btn_login').attr('value','登录中...');
	var param = {};
	param["paramMap.pageId"] = "userlogin";
	param["paramMap.email"] = $("#email1").val();
	param["paramMap.password"] = $("#password1").val();
	param["paramMap.code"] = $("#code_top").val();		
	param["paramMap.afterLoginUrl"]="${session.afterLoginUrl}";
	param["paramMap.status"]="index";
    $.post("logining1.mht",param,function(data){
		if(data == 1){
			//alert("登录成功！");
			window.location.reload();
			return;
		}else if (data == 2) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr("disabled",false); 
			alert("验证码错误！");
		} else if (data == 3) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("用户名或密码错误！");
		} else if (data == 4) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("该用户已被禁用！");
		}else if (data == 8) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("用户名或密码错误!还有2次机会,您可以尝试找回密码");
		}else if (data == 9) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("用户名或密码错误!还有1次机会,您可以尝试找回密码");
		}else if (data == 5) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("该用户已被限制登录，请于三小时以后登录！");
		}else if (data == 7) {
			$('#btn_login').attr('value','立即登录');
			$("#btn_login").attr('disabled',false); 
			alert("该用户账号出现异常，请速与管理员联系!");
		}
		switchCode();
		//$('.refresh a').click();//更新验证码
	});
}
//初始化
function switchCode(){
    var timenow = new Date();
    $("#codeNum_top").attr("src","admin/imageCode.mht?pageId=userlogin&d="+timenow);
}
$(document).keydown(function(e){
	
    // 这个判断是为了兼容所有浏览器，使 e 能被所有浏览器所解析 

    if(!e) var e = window.event;
    if (13 != e.keyCode){
		return;
    }
    var act = document.activeElement.id;
	if ("code_top" == act){
		login1();
	}
    
})
var key = '${key}'
</script>


<script type="text/javascript">jQuery(".TB-focus").slide({ mainCell:".bd ul",effect:"fold",autoPlay:true,delayTime:200 });</script>
<script>
 $("#shutdown").click(
	 function(){
	 	$("#imgdiv").hide();
	}
 )
</script>
 --%>
<script type="text/javascript">
var url =window.location.href; //获得当前url 
if (url.indexOf(".do?") > 0){
	url = url.replace(".do?",".mht?");
	location.replace(url);//装入这个新的url
}
var key = '${key}'
</script>

<script type="text/javascript">
/**
 * 加载理财排行榜
 */
function loadRanking(value){
	var sequence="0";
	if(value=="总"){
		sequence="0";
	}else if(value=="年"){
		sequence="1";
	}else if(value=="季"){
		sequence="2";
	}else if(value=="月"){
		sequence="3";
	}else if(value=="周"){
		sequence="4";
	}else if(value=="日"){
		sequence="5";
	}
	$('.suspend_phb_main').html("");
	$.ajax({
		url: 'getRanking.mht',
		async: true,
		type: 'get',
		data: {type:sequence},
		success: function (dta) {
			if(!dta.err && dta.list) {
				$.each(dta.list, function (i, o) {
					var html='<ul class="clearfix">';
					if(i<=2)
					    html+='<li style="width:41px;"><div class="suspend_phb_no suspend_phb_no1">'+(i+1)+'</div></li>';
					else
						html+='<li style="width:41px;"><div class="suspend_phb_no">'+(i+1)+'</div></li>';
					html+='<li style="width:65px;">'+o.username+'</li>';
					html+='<li style="width:116px;"><span class="suspend_phb_money">¥ </span>'+o.sumMoney+'</li>';
					html+=" </ul>";
					$('.suspend_phb_main').append(html);
				});
			}else{
				$('.suspend_phb_main').append("<span   style='display: block;text-align: center;width: 100%;'>暂无数据！</span>");
			}
		},error:function(err){
			$('.suspend_phb_main').append("<span   style='display: block;text-align: center;width: 100%;'>系统错误！</span>");
		}
	});
}

 $(document).ready(function(){
     if(!getCookie('pic_qq')){
         $('.index_question_xf').show();
         addCookie('pic_qq','1',24);
     }
     
    var menuIndex = (window.menuIndex || 4);
    if(menuIndex==6)menuIndex=0;
   	var $menuIndex = ".menu ul li:eq("+menuIndex+")";
   	$($menuIndex).find("a").css("color","red");
   	
   	$("#searchDealPwd").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '40%',
		height		: '70%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
 });

	function openToggle(){
		if($('#suspend').css('right')=='0px')
		{
			$('#suspend').stop().animate({'right':245},300);
		    $('.suspend_phb').stop().animate({'right':0},300);
			var sequence=$("input[name='phbYear']").val();
			loadRanking(sequence);
		}
		else
		{
			$('#suspend').stop().animate({'right':0},300);
		    $('.suspend_phb').stop().animate({'right':-245},300);
		}
	}

$(document).ready(function(){
	var user = '${session.user.creditLimit}'
	if(user)
	$.ajax({url:"queryEamilSum.mht",success:function(data){$("#mEmail").html('消息('+data+')')}})
	/*悬浮start*/
	$('.suspend_link').hover(function(){
		$('.suspend_link_main',this).stop().animate({'opacity':'1','right':'28px'},300);
		$('.suspend_link_main',this).show();
	},function(){
		$('.suspend_link_main',this).stop().animate({'opacity':'0','right':'50px'},500,function(){$(this).hide()});
		
	});
	
	$('.suspend_wechart').hover(function(){
		$('.suspend_wechart_main',this).stop().animate({'opacity':'1','top':'-160px','height':'206px'},300);
		
		$('.suspend_wechart_main',this).show();
	},function(){
		$('.suspend_wechart_main',this).stop().animate({'opacity':'0','top':'-100px','height':'0'},300);
		$('.suspend_wechart_main',this).hide();
	});
	
	$(".suspend_gotop").click(function(){
	    $('html, body').animate({scrollTop:0},1000)
	})
	
	$('.suspend_charts').click(function(){
		openToggle()
	});
	
	$('.suspend_phb_select p').click(function(){
		$(this).next('.suspend_phb_select_list').slideToggle();	
	});
	
	$('.suspend_phb_select_list li').click(function(){
		var value=$(this).text();
		$(this).parents('.suspend_phb_select').find('input').val(value);
		$(this).parent('.suspend_phb_select_list').slideUp();
		$(this).parents('.suspend_phb_select').find('p').text(value);
		loadRanking(value);
	});
	$('.suspend_mycclc1').click(function(){
		<%--$('.xuanfu_login').is(':hidden') ? $('.xuanfu_login').show() : $('.xuanfu_login').hide();--%>
		window.location.href="home.mht";
	});
	/*悬浮end*/
})
</script>

<div style="display:none;">
	<script type="text/javascript">
	/*baidu_tongji*/
	//var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	//document.write(unescape("%3Cscript  src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Ffb09efd7997b64258fbd6ad7a0ef8d0b' type='text/javascript'%3E%3C/script%3E"));
	/*cnzz_tongji*/
	//var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253102918'%3E%3C/span%3E%3Cscript     src='" + cnzz_protocol + "s19.cnzz.com/z_stat.php%3Fid%3D1253102918%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));
	</script>
</div>
