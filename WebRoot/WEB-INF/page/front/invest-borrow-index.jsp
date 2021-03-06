<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<c:set value="投资记录" var="title" scope="request" />
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
  <div class="w950 clearfix">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">投资记录</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    <div class="fr w750 clearfix">
    <div class="pub_tb bottom">
        <h3>我的投资</h3>
        <h3 style="border:none; "><a href="experienceTicket.mht" style="color:#a6a6a6;">体验标</a></h3>
        <h3 style="border:none; "><a href="automaticBidInit.mht" style="color:#a6a6a6;">自动投标</a></h3>
        </div>
         <div class="tabtil"> 
    <div class="uesr_border personal clearfix">
             
        <form action="homeBorrowRecycleList.mht" id="searchForm" method="post">
             <div class="uesr_serch mt20 list_filtrate">
                <span class="red3">投资状态：</span>
                <a href="javascript:;" id="aitem1" onclick="clickItem(1);" class="on">全部</a>
                <a href="javascript:;" id="aitem2" onclick="clickItem(2);">待成立</a>
                <a href="javascript:;" id="aitem3" onclick="clickItem(3);">回款中</a>
                <a href="javascript:;" id="aitem4" onclick="clickItem(4);">已还完</a>
                <a href="javascript:;" id="aitem5" onclick="clickItem(5);">失败</a>
            </div>
             <div class="uesr_serch mt20">
                <span class="red3">投资时间：</span>
               <!-- input type="text" onclick="" class="input_border w100" id="startTime"> -
               <input type="text" onclick="" class="input_border w100" id="endTime"> -->
               
               <input type="text" id="publishTimeStart"  name="publishTimeStart" value="${paramMap.publishTimeStart }" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        -
	        <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
               
               <button onclick="fundRecordList();" class="button w100 ml20 h34" id="search" >查询</button>
            </div>
             <div class="mt15">您总共有<span class="red f20">${investNo }</span>笔借款，总投资金额<span class="red f20">${investAllSum }</span>元</div>
           <input type="hidden" id="queryType" name="queryType" value="${queryType }"/>
           <input type="hidden" id="pageNum" name="curPage" value="1"/>
           
           </form> 
        </div>
<div class="box">
        <div class="boxmain2">
         <div class="tab_ec clearfix" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		    <td>投资编号</td>
		    <td>投资金额(元)</td>
		    <td>年化利率</td>
		    <td>到期时间</td>
		    <td>投资时间</td>
		    <td> 最近回款</td>
		    <td> 状态</td>
		    <td>操作</td>
		    </tr>
		  	<s:if test="pageBean.page==null || pageBean.page.size==0">
			   <tr align="center">
				  <td colspan="10">暂无数据</td>
			   </tr>
			   
			</s:if>
			<s:else>
			<s:iterator value="pageBean.page" var="bean">
			 <tr>
			    <td align="center">${seq }</td>
				<td align="center">${ bean.investAmount}</td>
		    	<td align="center">${bean.annualRate}% </td>
		    	<td align="center">${endTime }</td>
		    	<td align="center" >${ bean.investTime}</td>
		    	<td align="center">${repayDate }</td>
		    	<td align="center">
		    	<s:if test="#bean.borrowStatus ==1">等待资料</s:if>
		        <s:elseif test="#bean.borrowStatus ==2"> 招标中</s:elseif>
		    	<s:elseif test="#bean.borrowStatus ==3 ">已满标 </s:elseif>
		    	<s:elseif test="#bean.borrowStatus ==4 ">还款中 </s:elseif>
		    	<s:elseif test="#bean.borrowStatus ==5 "> 已还完 </s:elseif>
		    	<s:elseif test="#bean.borrowStatus ==6 ">失败</s:elseif>
		    	<s:else>${bean.borrowStatus}</s:else>
		    	</td>
		    	<td align="center"> <a href="homeBorrowRecycleList.mht?id=${bean.id}" target="_blank">查看详情</a></td>
		    	
		       </tr>
		     </s:iterator>
			</s:else>
		</table>
		<div  class="page" >
	     <shove:page  url='homeBorrowRecycleList.mht' curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="publishTimeStart">${paramMap.publishTimeStart}</s:param>
	    	<s:param name="publishTimeEnd">${paramMap.publishTimeEnd}</s:param>
	    	<s:param name="queryType">${queryType}</s:param>
	    	<s:param name="type">${paramMap.type}</s:param>
	     </shove:page> 
  </div>
          </div>
          </div>
    </div>
</div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
	 //displayDetail(1,2);
	 
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
     $('#li_10').addClass('on');
	 $('#search').click(function(){
	 	if($("#publishTimeStart").val().length == 10 && $("#publishTimeEnd").val().length == 10 &&
	 		 	$("#publishTimeStart").val() >$("#publishTimeEnd").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
		$("#pageNum").val(1);

		var queryType ="${queryType}";
		$("#queryType").val(queryType);
		/*if (queryType == 1){
			$("#searchForm").attr('action','homeBorrowInvestList.mht');
		}else if (queryType >= 2){
			$("#searchForm").attr('action','homeBorrowRecycleList.mht');
		}*/
		
	 	$("#searchForm").submit();
     });
     
     $("#jumpPage").click(function(){
		if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
	 	var curPage = $("#curPageText").val();
	 	if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		}
	 	$("#pageNum").val(curPage);
	 	$("#searchForm").submit();
	});

	styleDisplay();
   	
});



function jumpUrl(obj){
    window.location.href=obj;
}
function showAgree(investId,borrowId){
     var url = "protocol.mht?typeId=1&&borrowId="+borrowId+"&&investId="+investId+"&&styles=1";
     jQuery.jBox.open("post:"+url, "查看协议书", 700,400,{ buttons: { } });
}

//点击item
function clickItem(imno){
	$("#publishTimeStart").val("");
	$("#publishTimeEnd").val("");
	var aitem = $("a[id^='aitem']");
	aitem.removeClass("on");
	$("#aitem"+imno).addClass("on");
	$("#queryType").val(imno);
	/*if (imno == 1){
		$("#searchForm").attr('action','homeBorrowInvestList.mht');
	}else if (imno >= 2){
		$("#searchForm").attr('action','homeBorrowRecycleList.mht');
	}*/

	$("#searchForm").submit();
}


function styleDisplay(){
	var queryType = "${queryType}";
	var aitem = $("a[id^='aitem']");
	aitem.removeClass("on");
	$("#aitem"+queryType).addClass("on");
}

</script>
</body>
</html>