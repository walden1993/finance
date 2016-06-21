<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>邀请好友</title>
    <!-- link href="css/inside.css"  rel="stylesheet" type="text/css" /> -->
	<link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>	

<div class="nymain" style="width: 100%;">
    <div class="r_main" style="width: 100%;">
      <div class="tabtil">
        <h4 class="on">邀请好友
        </h4>
        </div> 
      <div class="box">
        <div class="boxmain2 table-responsive">
         <p class="tips">温馨提示：请不要发送邀请信给不熟悉的人士,避免给别人带来不必要的困扰。

请把下边的链接地址发给您的好友，这样您就成了他的邀请者。
<s:if test="%{#request.friendCost>0}">您邀请的好友注册vip并成功付费后，那么您可以一次性获得${friendCost }元的奖金。

每月结算已充值成功后，通过网站充值方式打到您的账上。</s:if>
</p>
   <p class="tips">邀请链接：<span id="yq_address_input">${url}reg.mht?param=${userId}</span></p>
<table class="table">
  <tr>
    <td>
   		我的推荐人 : <s:if test="#request.recommendUserId==null"><input class="scbtn" type="button"  value="补填推荐人" onclick="fillRecomment();"/> </s:if> 
   		<s:else>${recommendUserId}</s:else>
    </td>
    <td>好友投资总额： <fmt:formatNumber value="${reInvestAmount}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> </td>
  </tr>
</table>
   
         <div class="biaoge" id="hyyq">
          <table  class="table">
  <tr>
    <th>邀请的好友</th>
    <th>注册时间</th>
    <th>VIP成功付费时间</th>
    <th>奖励</th>
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

 <!-- 引用底部公共部分 -->     
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>


<script>
$(function(){
	
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


function fillRecomment(){
	
	var url ="fillRecommentPage.mht";
    $.jBox("iframe:"+url, {
        title: "补填推荐人",
        width: 600,
        height: 250,
        buttons: { '关闭': true }
    });
    window.opener.location.href=window.opener.location.href;//window.close();
	//window.location.reload();
}
function myreload(){
	window.location.reload();
}
</script>
</body>
</html>

