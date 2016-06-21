<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="/include/head.jsp"></jsp:include>
    <!-- link href="css/inside.css"  rel="stylesheet" type="text/css" /> -->
	<link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="mainwarp">
  <div class="w1002 clearfix">
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
  <div class="fr w765 clearfix">
  <div class="uesr_border personal">
  <table width="100%" style="font-size:14px;line-height:26px;">
  <s:if test="%{#request.notFound == 0}">
  <tr height="30">
  		<td align="right" width="30%"></td> 
  		<td align="left" style="color:#ff0005">
  		  <s:if test="%{#request.fimap.myLevel ==1 }">您目前是铜牌理财师</s:if>
  		  <s:elseif test="%{#request.fimap.myLevel ==2}">您目前是银牌理财师</s:elseif>
  		  <s:elseif test="%{#request.fimap.myLevel ==3}">您目前是金牌理财师</s:elseif>
  		</td>
  		
  	</tr>
  </s:if>
  <s:if test="%{#request.retStatus ==0 }">
    <s:if test="%{#request.fimap.myLevel !=3 }">
  	<tr height="30">
  		<td align="right" width="30%">申请成为：</td>
  		<td>
  		<select id="myLevel">
  			<s:if test="%{#request.fimap.myLevel ==1 }"><option value="2">银牌理财师</option></s:if>
  			<s:elseif test="%{#request.fimap.myLevel ==2 }"><option value="3">金牌理财师</option></s:elseif>
  			<s:else><option value="1">铜牌理财师</option></s:else>
  		</select>
  		</td>
  	</tr>
  	</s:if>
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<input type="text" class="inp188" id="leader" value=""/>(请填写推荐理财师用户名)
  		</td>
  	</tr>
  </s:if>
  <s:elseif test="%{#request.retStatus ==1}">
  	<tr height="30">
  		<td align="right" width="30%"> </td> 
  		<td align="left" >
  		  申请正在审核中，请耐心等待。
  		</td>
  		
  	</tr>
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<input type="text" class="inp188" id="leader" value=""/>(请填写推荐理财师用户名)
  		</td>
  	</tr>
  </s:elseif>
  <s:elseif test="%{#request.retStatus ==2 }">
    <s:if test="%{#request.fimap.myLevel !=3 }">
  	<tr height="30">
  		<td align="right" width="30%">申请成为：</td>
  		<td>
  		<select id="myLevel">
  			<s:if test="%{#request.fimap.myLevel ==1 }"><option value="2">银牌理财师</option></s:if>
  			<s:elseif test="%{#request.fimap.myLevel ==2 }"><option value="3">金牌理财师</option></s:elseif>
  			<s:else><option value="1">铜牌理财师</option></s:else>
  		</select>
  		</td>
  	</tr>
  	</s:if>
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<input type="text" class="inp188" id="leader" value=""/>(请填写推荐理财师用户名)
  		</td>
  	</tr>
  </s:elseif>
  <s:elseif test="%{#request.retStatus ==3}">
    <s:if test="%{#request.fimap.myLevel !=3 }">
  	<tr height="30">
  		<td align="right" width="30%">申请成为：</td>
  		<td>
  		<select id="myLevel">
  			<s:if test="%{#request.fimap.myLevel ==1 }"><option value="2">银牌理财师</option></s:if>
  			<s:elseif test="%{#request.fimap.myLevel ==2 }"><option value="3">金牌理财师</option></s:elseif>
  			<s:elseif test="%{#request.fimap.myLevel ==3 }"></s:elseif>
  			<s:else><option value="1">铜牌理财师</option></s:else>
  		</select>
  		</td>
  	</tr>
  	</s:if>
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<s:property value="#request.fimap.leaderName"  default="" />
  		</td>
  	</tr>
  </s:elseif>
  <s:elseif test="%{#request.retStatus ==4}">
  	<tr height="30">
  		<td align="right" width="30%"> </td> 
  		<td align="left" >
  		  申请正在审核中，请耐心等待。
  		</td>
  		
  	</tr>
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<s:property value="#request.fimap.leaderName"  default="" />
  		</td>
  	</tr>
  </s:elseif>
  <s:elseif test="%{#request.retStatus ==5}">
  	<tr height="30">
  		<td align="right"> 推荐理财师：</td>
  		<td>
  		<s:property value="#request.fimap.leaderName"  default="" />
  		</td>
  	</tr>
  </s:elseif>
  	
  	<s:if test="%{#request.retStatus ==0|| #request.retStatus ==1||#request.retStatus ==2||#request.retStatus ==3 }">
  	<tr height="30">
  	<td align="right"> <input type="hidden" id="nowLevel" value="${fimap.myLevel}"/> </td>
  		<td colspan="2"><a id="subbtn" style="cursor:pointer;" class="bcbtn">提交</a></td>
  	</tr>
  	</s:if>
  </table>   
 
 <div style="font-size:14px;line-height:26px;">
	<p>
	<span class="red f20">说明：</span><br/>
	理财师确定后，不能修改<br />
	<b class="f16">铜牌理财师：</b><br/>
	1.有完全民事行为能力。<br/>
	2.认同平台的各项规章制度和风险提示。<br/>
	3.个人投资额满1万。<br/>
	4.个人名下有客户3人以上，并合计投资额超3万。<br/><br/>
	 
	<b class="f16">银牌理财师：</b><br/>
	1.有完全民事行为能力。<br/>
	2.认同平台的各项规章制度和风险提示。<br/>
	3个人投资额满3万。<br/>
	4.成功辅导过至少3名以上铜牌理财师。<br/><br/>
	 
	<b class="f16">金牌理财师：</b><br/>
	1.有完全民事行为能力。<br/>
	2.认同平台的各项规章制度和风险提示。<br/>
	3个人投资额满5万。<br/>
	4.成功辅导过至少3名以上银牌理财师。<br/>
	<br/>
	    <b class="f16">理财师退出机制：</b><br/>
	1.触碰国家法律<br/>
	2.虚假宣传，损害三好资本品牌<br/>
	3.6个月内无访问记录<br/>
    </p>
    
    </div>
    
    </div>
    </div>
  </div>


 <!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>


<script>
$(function(){
	
	//displayDetail('3');
	$("#financialManager").addClass('on');
          
    $("#subbtn").click(function(){
         var param = {};
         param["paramMap.myLevel"] =$("#myLevel").val();
         param["paramMap.leader"] =$("#leader").val();
         param["paramMap.nowLevel"] =$("#nowLevel").val();

		 if (($("#myLevel").val() == undefined || $("#myLevel").val()=="") && ($("#leader").val() == undefined || $("#leader").val()=="" ) ){
			 alert("提交不能为空。");
			 return;
		 }
         
         $.dispatchPost("applyFinance.mht",param,function(data){
             if (data.code == 1 || data.code == 2){
				alert(data.msg);
             } else {
				alert("申请成功。");
				window.location.href="financialManager.mht";
             }
        });
    });
    
    
});


</script>
</body>
</html>

