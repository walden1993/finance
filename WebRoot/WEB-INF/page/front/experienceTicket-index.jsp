<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<jsp:include page="/include/head.jsp"></jsp:include>
    	<link href="css/inside.css"  rel="stylesheet" type="text/css" />
    	<link href="css/css.css" rel="stylesheet" type="text/css" />
    	<link href="css/inside.css"  rel="stylesheet" type="text/css" />
    	<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    	<link href="css/WdatePicker.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
ul,li {
	margin: 0;
	padding: 0
}

#scrollDiv {
	height: 182px;
	overflow: hidden
}

.load {
	width: 18px;
	height: 18px;
	margin: 10px;
}
		</style>
	</head>
	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>	
      <div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    </div>

								<div class="fl w760">
									<div class="new_tit fl  clearfix">
										投资体验
									</div>
									<div class="r_main clearfix">
										<div class="total fl">
											<p class="pt30 f16">
												体验金总额
											</p>
											<p class="f20">
											<s:if test="#request.mapSum.amount ==null||#request.mapSum.amount==''">
											0
											</s:if>
											<s:else>
												${mapSum.amount }
											</s:else>元
											</p>
										</div>
										<div class="fl pl30">
											<p class="pt30 f16 tc">
												累计收益
											</p>
											<p class="f20">
											<s:if test="#request.mapSum.userHasRepaymentAmount ==null||#request.mapSum.userHasRepaymentAmount==''">
											0
											</s:if>
											<s:else>
												${mapSum.userHasRepaymentAmount }
											</s:else>元
											</p>
										</div>
									</div>
									<p class="clear"></p>
									<div class="ty_tips">
										<span class="orange f18">体验券小贴士</span>
										<br />
										1、体验券只能在"投资体验"中使用。
										<br />
										2、如在"投资体验"中投资时"可用体验券"为零，则说明您没有体验券资格。
										<br />
										3、根据当期"投资体验"活动具体规则，可使用体验券投资。
										<br />
										4、体验券产生的利息归用户所有，体验券在成功加入投资体验后自动归还平台。
										<br />
										5、体验券过期不可使用。
										<br />
										6、体验券和投资体验最终解释权归三好资本所有。
									</div>
									<div class="r_main">
										<div class="tabtil">
											<ul>
												<li id="lab_1" onclick="jumpUrl('financeExperience.mht');">
													我的体验记录
												</li>
												<li id="lab_2" class="on" onclick="jumpUrl('experienceTicket.mht');">
													我的体验券
												</li>
											</ul>
										</div>
										<div class="box">
        <div class="boxmain2">
         <div class="srh">
          <form action="searchTicket.mht" id="searchForm">
	         <span>券号：<s:textfield  name="paramMap.ticketNo"  cssClass="ny_ipt"    /></span>
             <span class="ml20">状态：<s:select class="ny_set"   list="#{'':'全部','3':'已激活','2':'已使用','1':'冻结' }" name="paramMap.ticketStatus"  ></s:select>
	         <input  type="submit"  class="ny_info_btn"  value="查询" />
	        
            <a href="activate_experience_ticket.mht"  class="ny_info_btn01 ml50  fancybox.ajax single_image">激活体验券</a>
          </form>
        </div>
         <div class="biaoge">
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tbody>
		  
		  <s:if test="%{#request.myTicketList ==null || #request.myTicketList.size()==0}">
          		<p class="orange mt30 mb30 tc">您无可用体验券信息！</p>
          </s:if>
		  <s:else>
		  
		  <tr>
		    <th>券号</th>
		    <th>体验项目</th>
		    <th>金额</th>
		    <th>可用时间</th>
		    <th>使用时间 </th>
		    <th>状态</th>
		  </tr>
		   <s:iterator value="#request.myTicketList" id="myTicketList" >
		  	<tr>
			    <td align="center"><s:property value="#myTicketList.ticketNo" /></td>
				<td align="center">
				<s:if test="#myTicketList.borrowTitle == ''   ||  #myTicketList.borrowTitle == null  ">
				/
				</s:if>
				<s:else>
				    <a href="experienceDetail.mht?id=<s:property value="#myTicketList.id" />"><s:property value="#myTicketList.borrowTitle" />
                </a>
				</s:else>
				</td>
		    	<td align="center"><s:property value="#myTicketList.amount" />
		    	<td align="center">${fn:substring(enableDate,0,10)}至${fn:substring(disableDate,0,10)}</td>
		    	<td align="center">
		    	<s:if test="#myTicketList.useTime ==null  || #myTicketList.useTime.toString()  ==''" >
                /
                </s:if>
		    	<s:else>${fn:substring(useTime,0,10)}&nbsp;${fn:substring(useTime,10,19)}</s:else>
		    	</td>
		    	<s:if test="#myTicketList.isShixiao!=1">
			    	<s:if test="%{#myTicketList.ticketStatus == 1}">
			    		<td align="center">冻结</td>
			    	</s:if>
			    	<s:if test="%{#myTicketList.ticketStatus == 2}">
			    		<td align="center">已使用</td>
			    	</s:if>
			    	<s:if test="%{#myTicketList.ticketStatus == 3}">
			    		<td align="center">已激活</td>
			    	</s:if>
			    </s:if>
			    <s:else>
			    	<td align="center">已过期</td>
			    </s:else>
		    </tr>
		   </s:iterator>
		 </s:else>
		     </tbody>
		     
		     </table>
		   <s:if test="pageBean.page!=null || pageBean.page.size>0">
		   <!-- 页尾 -->
            <div class="page" >
                    <p>
                       <shove:page url="searchTicket.mht" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">                
                       </shove:page>
                    </p>
                </div>    
    
    </s:if>
          </div>
    </div>
</div>
									</div>
								</div>
							</div>
						</div>
						
						<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>

<script>

$(function(){
    //样式选中
     //displayDetail('1');
	 $("#investExpeNew").addClass('on');
});	

function jumpUrl(obj){
    window.location.href=obj;
}


function jihuo(){
	//jQuery.jBox.open("post:activate_experience_ticket.mht", "激活体验券", 600,250,{ buttons: { } });
	$.jBox("iframe:activate_experience_ticket.mht", {
        title: "激活体验券",
        width: 600,
        height: 250,
        buttons: { '关闭': true }
    });
	
}
$("#jumpPage").attr("href","javascript:void(null)");
$("#curPageText").attr("class","cpage");
$("#jumpPage").click(function(){
     var curPage = $("#curPageText").val();
    
     if(isNaN(curPage)){
        alert("输入格式不正确!");
        return;
     }
window.location.href="searchTicket.mht?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
});
</script>


	</body>
</html>