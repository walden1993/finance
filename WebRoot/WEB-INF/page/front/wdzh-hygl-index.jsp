<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="友情邀请" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="nymain wrap">
  <div class="wdzh w950">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">友情邀请</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    <div class="r_main w750 fr bg-white">
      <div class="pub_tb bottom"><h3>邀请好友</h3></div>
      <div class="clearfix"></div>
      <div class="box">
        <div class="boxmain2">
         <p class="tips">温馨提示：请不要发送邀请信给不熟悉的人士,避免给别人带来不必要的困扰。

请把下边的链接地址发给您的好友，这样您就成了他的邀请者。
<s:if test="%{#request.friendCost>0}">您邀请的好友注册vip并成功付费后，那么您可以一次性获得${friendCost }元的奖金。

每月结算已充值成功后，通过网站充值方式打到您的账上。</s:if>
</p>
   <p class="tips">邀请链接：<span id="yq_address_input">${url}reg.mht?param=${userId}</span><a  id="yq_address_btn" class="scbtn" style="margin-left:30px;">复制</a></p>
<table>
  <tr>
    <td>
   		我的推荐人 : <s:if test="#request.recommendUserId==null"><a href="fillRecommentPage.mht" id="addReffer"  class="red fancybox.ajax">补填推荐人</a> </s:if> 
   		<s:else>${recommendUserId}</s:else>
    </td>
    <td width="52%">&nbsp;</td>
    <td>好友投资总额：</td>
    <td class="red"> <fmt:formatNumber value="${reInvestAmount}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> 
    </td>
  </tr>
</table>
   
         <div class="biaoge" id="hyyq">
          <table width="100%" class="table" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th class="text-center">邀请的好友</th>
    <th class="text-center">注册时间</th>
    <th class="text-center">VIP成功付费时间</th>
    <th class="text-center">奖励</th>
    </tr>
    <s:if test="pageBean.page==null || pageBean.page.size==0">
		<tr align="center" class="gvItem">
			<td colspan="4">暂无数据</td>
		</tr>
	</s:if>
	<s:else>
	   <s:iterator value="pageBean.page" var="bean" >
        <tr>
          <td align="center">${bean.username}</td>
          <td align="center">${bean.createTime }</td>
          <td align="center">${bean.vipCreateTime}</td>
          <td colspan="6" align="center">${bean.money}</td>
        </tr>
       </s:iterator>
	</s:else>

    </table>
    <div class="page" align="center">
      <shove:page url="friendManagerInit.mht" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
      </shove:page>
    </div>  
    </div>
    </div>
    
</div> 
	 
	  <div id="userfrends" class="box" style="display:none;">
	    
	        <!--关注好友部分-->
	  </div>
    </div>
  </div>
</div>

 <!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>


<script>
$(function(){
	
	//displayDetail(5,8);
	$("#friendMgrNew").addClass('on');
    //$("#zh_hover").attr('class','nav_first');
	//$("#zh_hover div").removeClass('none');
	//$('#li_6').addClass('on');
	  
    $("#attention").click(function(){
            var param = {};
          param["paramMap.id"] =${user.id}
          param["paramMap.attention"] ="attention";
          $.dispatchPost("userFrends.mht",param,function(data){
           $("#userfrends").html(data);
         });
    });
    
    
    $("#addReffer").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '40%',
		height		: '70%',
		autoSize	: true,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
    
    $('#yq_address_btn').click(function(){
    	copy_clip($('#yq_address_input').html());
    	//var userAgent = window.navigator.userAgent.toLowerCase();
    	//var chromeFlag = (userAgent.indexOf("chrome") > 0 ? true:false);
    	//var firefoxFlag = (userAgent.indexOf("firefox") > 0 ? true:false);
        //if(!chromeFlag){
         /*  var txt = '复制文本到剪贴板:\n\n';
           txt = txt+$('#yq_address_input').html();
           window.clipboardData.setData('text', $('#yq_address_input').html());
        */   //alert(txt);
        //}
    });
  
	$('.tabtil').find('li').click(function(){
	    $('.tabtil').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.tabtil').nextAll('div').hide();
         $('.tabtil').nextAll('div').eq($(this).index()).show();
	});
	init();
});


function copy_clip(copy){
	 
	if (window.clipboardData){
		if(window.clipboardData.setData("Text", copy)){
			alert("邀请链接已复制成功。")
		};
	} else {
		$('#yq_address_btn').zclip({ 
	        path:'script/ZeroClipboard.swf', 
	        copy:$('#yq_address_input').html()
	   });
	}
}

function init(){
  if(!$.browser.msie){
      $('#yq_address_btn').zclip({
         path:'script/ZeroClipboard.swf',
         copy:function(){return $('#yq_address_input').html();}
      });
  }
}   
function initListInfo(){
 	$.dispatchPost("friendManagerList.mht",param,function(data){
 	   $("#hyyq").html(data);
	});
}

$("#jumpPage").click(function(){
    var curPage = $("#curPageText").val();
   
	 if(isNaN(curPage)){
		alert("输入格式不正确!");
		return;
	 }
	 $("#pageNum").val(curPage);

	 window.location.href="friendManagerInit.mht?curPage="+curPage+"&pageSize=10&m"
});


function myreload(){
	window.location.reload();
}
</script>
</body>
</html>

