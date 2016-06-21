<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
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
												<li id="lab_1" class="on" onclick="jumpUrl('financeExperience.mht');">
													我的体验记录
												</li>
												<li id="lab_2" onclick="jumpUrl('experienceTicket.mht');">
													我的体验券
												</li>
											</ul>
										</div>
										<div class="box">
											<div class="boxmain2">
												<div class="srh">
													<form action="searchExperience.mht" id="searchForm">
														<span>状态：</span>
														<s:select class="ny_set"   list="#{'':'--全-部--','0':'招标中','1':'还款中','2':'已还完' }" name="paramMap.status"  ></s:select>
														<input type="submit" value="查&nbsp;&nbsp;询" class="ny_info_btn"/>

													</form>
												</div>
												<div class="biaoge">
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tbody>
														<s:if test="%{#request.myExperienceList ==null || #request.myExperienceList.size()==0}">
												          		<p class="orange mt30 mb30 tc">您无可用体验记录信息！</p>
												          </s:if>
														  <s:else>
															<tr>
																<th>
																	项目名称
																</th>
																<th>
																	年利率
																</th>
																<th>
																	期限
																</th>
																<th>
																	收益返还
																</th>
																<th>
																	投资金额
																</th>
																<th>
																	应收
																</th>
																<th>
																	已收
																</th>
																<th>
																	状态
																</th>
																<th>
																	下次还款日期
																</th>
															</tr>
														  <s:iterator value="#request.myExperienceList" id="myExperienceList" >
															<tr>
																<td align="center">
																	<a href="experienceDetail.mht?id=<s:property value="#myExperienceList.id" />"><s:property value="#myExperienceList.borrowTitle" /></a>
																</td>
																<td align="center">
																	<s:property value="#myExperienceList.annualRate" />%
																</td>
																<td align="center">
																	<s:property value="#myExperienceList.deadline" />个月
																</td>
																<td align="center">
																	<s:if test="#myExperienceList.paymentMode == 1">按月还息</s:if>
      			    												<s:elseif test="#myExperienceList.paymentMode == 2">按天还息</s:elseif>
      			    												<s:else>/</s:else>
																</td>
																<td align="center">
																	<s:property value="#myExperienceList.amount" />
																</td>
																<td align="center">
																	<s:property value="#myExperienceList.userInterestAmount" />
																</td>
																<td align="center">
																	<s:property value="#myExperienceList.userHasRepaymentAmount" />
																</td>
																<td align="center">
																<s:if test="#myExperienceList.isShixiao!=1">
																   <s:if test="#myExperienceList.status == 0">
															       	招标中
															       </s:if>
															       <s:if test="#myExperienceList.status == 1">   
															 		还款中
															       </s:if>
															       <s:if test="#myExperienceList.status == 2">
															 		已还完
															       </s:if>
															    </s:if>
															    <s:else>
															    	已过期
															    </s:else>
																</td>
																<td align="center">
																        ${planRepaymentDate }
																</td>
															</tr>
															</s:iterator>
		 												</s:else>
														</tbody>
													</table>
													<s:if test="pageBean.page!=null || pageBean.page.size>0">
    			<div class="page" >
                    <p>
                       <shove:page url="searchExperience.mht" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
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

<script>

$(function(){
	 //displayDetail('1');
	 $("#investExpeNew").addClass('on');
	 
    //样式选中	
     $('#li_17').addClass('on');
});	     


function jumpUrl(obj){
    window.location.href=obj;
}
$("#jumpPage").attr("href","javascript:void(null)");
$("#curPageText").attr("class","cpage");
$("#jumpPage").click(function(){
     var curPage = $("#curPageText").val();
    
     if(isNaN(curPage)){
        alert("输入格式不正确!");
        return;
     }
window.location.href="searchExperience.mht?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
});
</script>


	</body>
</html>