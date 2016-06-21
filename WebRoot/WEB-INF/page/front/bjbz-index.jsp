<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  <c:set value="安全保障" scope="request" var="title"/>
  <jsp:include page="/include/head.jsp"></jsp:include>
  <script type="text/javascript">var menuIndex = 3;</script>
  </head>
  
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain" id="show_des">
    <div class="bigbox loadingImg h500">
 <%-- 	 <div class="til">本金保障</div>
  <div class="sqdk" style="background:none; margin:20px">
    <div class="baozhang">
      <div class="bz_title"></div>
      <p>本站风险保障金累计： <s:property value="#request.totalRiskMap.total"/>元<br/>
      当日支出：<s:if test="#request.currentRiskMap.riskSpending !=''">${currentRiskMap.riskSpending}</s:if><s:else>0.00</s:else><br/>
        当日收入：<s:if test="#request.currentRiskMap.riskInCome !=''">${currentRiskMap.riskInCome}</s:if><s:else>0.00</s:else>
      </p>
    </div>
<span id="show_des"></span>
  </div> --%>
  </div>
<!-- 引用底部公共部分 --> 
</div>    
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript">
	$(function(){
    //样式选中
     $("#bj_hover").attr('class','nav_first');
		 $("#bj_hover div").removeClass('none');
		 $('.tabmain').find('li').click(function(){
		    $('.tabmain').find('li').removeClass('on');
		    $(this).addClass('on');
		 });
		 initList();
	});
	
	function initList(){
	   $.post("queryAboutInfo.mht?msg=9", function(data) {
			$("#show_des").html(data);
		});
	   /*$.post();
	   $.dispatchPost("queryAboutInfo.mht?msg=9",null,function(data){
	      $("#show_des").html(data);
	   });*/
	}	
	</script>

</body>
</html>
