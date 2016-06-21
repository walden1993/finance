<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本-不用戳破屏，不用摇断手，刮红包，包包有料！</title>
<meta name="keywords" content="三好资本,网贷平台,网络贷款,网络贷款平台,p2p投资理财平台,投资理财平台,p2p贷款,网络借贷平台,深圳市易付通金融信息技术有限公司,不用戳破屏，不用摇断手，刮红包，包包有料！">
<meta name="description" content="【三好资本p2p网贷平台】（www.sanhaodai.com）是中国安全升值的互联网金融综合服务平台，提供安全高效的P2P投资理财、P2P网络借贷、p2p网络贷款、小额贷款、短期贷款、个人贷款、无抵押贷款等互联网金融服务。通过第三方资金托管、机构100%本息担保为您的资金安全及收益提供强有力的保障。">
<meta name="viewport" content="width=device-width, initial-scale=0.54, user-scalable=yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script src="activity/20150306/images/jquery.js" type="text/javascript"></script>
<script src="activity/20150306/images/wScratchPad.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<link href="activity/20150306/images/other.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="w720 bj">
  <!-- <div class="pl20 pt25"><img src="activity/20150306/images/wx_logo.png" width="432" height="80" /></div> -->
  <div class="pl20 pt25">
    <div class="fl"><a href="www/index.html#/tab/dash" ><img src="activity/20150306/images/wx_logo.png" width="432" height="80" /></a></div>
    <div class="fr login">
    <s:if test="#session.user!=null">
    	用户名:${user.userName}
    </s:if>
    <s:else>
       <a href="www/index.html#/login">登录</a> | <a href="www/index.html#/register/">注册</a>
    </s:else>
    </div>
  </div>
  
  <div class="tc pt30"><img src="activity/20150306/images/wx_pic01.png" width="694" height="172" /></div>
  <div class="guajiang">     
    <div class="for">
      <div id="wScratchPad3" class="list_one"></div>
      <div class="list_two hidden">
          <h2 class="mt70 f50 red"  id="award"></h2>
		  <a href="javascript:window.location.reload();" class="btn">再刮一次</a>
      </div>
      <div class=" clearfix" style="position:absolute;top:240px; text-align:center;width:600px;color:#e83234;font-size:30px;font-weight:bold;">刮开涂层，有机会赢取1-1000元</div>
      <div class=" clearfix" style="position:absolute;top:300px; text-align:center;width:600px;color:#e83234;font-size:30px;font-weight:bold;"><s:if test="#session.user!=null">您的刮奖次数剩余：${awardTimes}次</s:if></div>
    </div>
    <script type="text/javascript">
    	   var feba = true;
    	   var lottery = '';
    	   var alertAcc = 0;
           $("#wScratchPad3").wScratchPad({
            cursor:'activity/20150306/images/scratchpad_bg.png',
            scratchMove: function(e, percent)
            {	
                
				if(percent>1){
					
					//未登录
					if(!'${user.id}'){
						if(alertAcc++ % 2 == 0){
							alert('亲，登录后才可以刮大奖哦!!!');
						}
						alertFlag = false;
						this.reset();
						return false;
					}else{
						//已登录
						if(feba){
							$.dispatchPost("awardStart.mht",null,function(response){
								if(response.awardId){
									$("#award").html(response.retAwardName);
								}else{
									$("#award").html(response.msg);
									alert("Honey，邀请好友注册、注册后再投资，累积刮奖送不停！");
									window.location.href="#wrapcontent";
								}
								lottery = response.retAwardName;
								$(".list_two").css("z-index",999).show();
							});
							feba = false;
						}
					}
				}
                if(percent > 10 && lottery){
                	this.clear();
                }
                
            }
        });

    </script>
  </div>
</div>
<div id="wrapcontent" class="wrapcontent">
	<s:if test="#session.user!=null">
		<h1 style="color: #fff;">邀请链接：</h1><div class="fuzhi"><%=application.getAttribute("basePath")%><br/>/www/index.html#/register/${user.userName}<br/></div>
	</s:if>
    <div class="cont_one">
       <h2>活动内容</h2>
       <div class="info">
          <h3><span>惊喜活动一：</span>刮红包！！1000现金大奖任性到底！</h3>
<p>新用户注册，无需实名验证，就可刮现金红包，100%中奖，红包即时到账<br/>
刮红包奖金为：现金 3元、6元、10元、20元、50元、100元、500元、1000元</p>
<h3><span>惊喜活动二：</span>推荐就给利</h3>
<p>活动期间分享活动邀请好友注册成功，获得刮红包机会<br/>
每成功注册一位，送一次刮红包机会；好友投资，再送一次刮红包机会<br/>
刮红包奖金为：谢谢参与，2元、5元、10元、20元、50元、100元、500元、1000元</p></div></div>
    <div class="cont_two">
       <h2>活动规则</h2>
       <div class="info">
       1、活动时间：2015年3月13日20:00到4月10日18:00<br/>
          2、通过三好资本网站平台、微信公众号推广，扫三好资本二维码，关注微信公众号，进入刮红包活动，注册账户，点击刮奖，奖金到账；
<br/>	3、新用户成功注册，送刮红包机会，100%中奖，红包奖励金额投资后方可提现；
<br/>4、邀请好友注册成功，送刮红包机会，每成功注册一位，送一次刮红包机会，好友投资，再送一次刮红包机会，红包奖励金额投资后方可提现；
<br/>5、邀请好友注册需填写推荐人用户名，若未填写，将不计算推荐人刮红包机会；
<br/>6、红包奖励在活动期间内有效，在有效期内投资，否则逾期奖金将视为无效；
<br/>7、活动结束后我们客服会根据邀请的注册好友随机抽取进行联系，如发现联系不上或信息不属实，奖金也将视为无效哦；
<br/>8、活动作弊者，一经发现，扣除所有奖励所得；
<br/>9、本活动最终解释权归深圳市易付通金融信息技术有限公司所有。

       </div>
    </div>
</div>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>
<script type="text/javascript">
$('.fuzhi').click(function(){
    	copy_clip($('#yq_address_input').html());
});
function copy_clip(copy){
	 
	if (window.clipboardData){
		if(window.clipboardData.setData("Text", copy)){
			alert("邀请链接已复制成功。")
		};
	} else {
		$('.fuzhi').zclip({ 
	        path:'script/ZeroClipboard.swf', 
	        copy:$('#yq_address_input').html()
	   });
	}
}
</script>

</body>
</html>
