<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

 <div class="w950 mt20 mb15">
    <div class="list bold600" >
    
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
     				<s:iterator value="pageBean.page" status="index" var="finance">
    
          <div class="item fl mb15 p-relative" <s:if test="#index.index==0 || #index.index%4==0"> style='margin-left:0px;'</s:if> ><!-- <span class="ico"><img src="images/ico_5.png" /></span> -->
        <%-- <s:if test="#finance.id>=296 && #finance.deadline>=3">
          	<span class="ico"><img src="images/activity_ioc.png" /></span>
          </s:if>
        <s:if test="#finance.id>=263 && #finance.deadline==3">
          	<span class="ico"><img src="images/award_5.png" /></span>
          </s:if>
           <s:if test="#finance.id>=263 && #finance.deadline==1">
          	<span class="ico"><img src="images/award_2.png" /></span>
          </s:if> --%>
        	
        <table width="100%">
              <tr height="75px;">
            <td colspan="2" class="title buttom_border  pr10"><a href="financeDetail.mht?id=${finance.id}"  target="_blank">${finance.borrowTitle }</a></td>
          </tr>
             <tr>
            <td class="disctription ">借款金额</td>
            <td class="money">${finance.borrowAmount}</td>
          </tr>
              <tr>
            <td class="disctription">年化利率</td>
            <td class="rate"><s:property value="%{#finance.annualRate-#finance.rewardRate}" default="0"/>%
            <s:if test="#finance.rewardRate>0">
          	 +奖${finance.rewardRate}%
         	 </s:if>
            </td>
          </tr>
              <tr>
            <td class="disctription">投资周期</td>
            <td class="deadline"><span class="number"><s:property value="#finance.deadline" default="0"/></span><span class="word"> <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
			            	<s:else>天</s:else></span></td>
          </tr>
              <%-- <tr>
            <td class="disctription">到期时间</td>
            <td class="C337AB7"><fmt:formatDate value="${finance.remainTimeEnd}" type="date"  pattern="yyyy-MM-dd" dateStyle="default"/>  </td>
          </tr> --%>
              <tr>
            <td class="disctription">还款方式</td>
            <td class="color9a9797"><s:if test="%{#finance.paymentMode == 1}">按月分期还款(等额本息还款)</s:if>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 2}">按月付息，到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></td>
          </tr>
           <tr>
            <td colspan="2" valign="bottom" style="height:10px;" class="buttom_border"></td></tr>
           <tr>
            <td class="disctription">可投金额</td>
            <td class="deadline text-right money"   style="padding-right:20px;"><s:property value="#finance.investNum" default="0"/></td>
          </tr>
              <tr>
            <td colspan="2"  style="padding-right:20px;height: 5px;">
                <div class="progress fl" style="height:6px;border-radius:0px; width:100%; margin:5px 0px;">
                      <div class="progress-bar progress-bar-danger"  role="progressbar" aria-valuenow="60" aria-valuemin="0"aria-valuemax="100" style="width:${finance.schedules}%;"> <span class="sr-only">60% Complete</span> </div>
                    </div>
            </td>
          </tr>
          <tr>
            <td class="disctription">融资进度</td>
            <td class="deadline text-right"   style="padding-right:20px;">${finance.schedules}%</td>
          </tr>
              <tr>
            <td colspan="2"  style="padding:0px;">
            	<s:if test="%{#finance.borrowStatus == 1}">
			             <a class="button w" style="padding: 2px;"   target="_blank">初审中</a>
				        </s:if>
				         <s:elseif test="%{#finance.borrowStatus == 2}">
				         	   <a class="button w" style="padding: 2px;" href="financeDetail.mht?id=${finance.id}"  target="_blank">查看</a>    
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 3}">
				            <a class="button_over w" style="padding: 2px;"   target="_blank">复审中</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 4}">
				            <a class="button_over w" style="padding: 2px;"   target="_blank">
				            <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
				          	<s:else>已满标</s:else>
				          	</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 5}">
				           <a class="button_over w" style="padding: 2px;"   target="_blank">已还完</a>
				        </s:elseif>
				        <s:else>
				              <a class="button_over w" style="padding: 2px;"   target="_blank">流标</a>
				        </s:else>
            </td>
          </tr>
            </table>
      </div>
      
      </s:iterator>
      
      </s:if><s:else><div class="text-center m15 pt10 pb10 f16"><b>暂无数据,再试试选择其它条件吧！！！</b></div></s:else>
        </div>
       
       <div class="w950 text-center">
        	<s:if test="pageBean.page!=null || pageBean.page.size>0">
		     <div class="page">
		     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum }"></shove:page>
		     </div>
		     </s:if>
       </div> 
       
  </div>

<%--    <div class="public_tit mt20"><i class="title_icon01"></i>投资列表</div>
   <div class="invest">
     <ul>
     <s:if test="pageBean.page!=null || pageBean.page.size>0">
           <s:iterator value="pageBean.page" var="finance">
		       <li class="clearfix">
		         <div class="pic" style="position: relative;"><s:if test="%{#finance.deadline>=3 && #finance.id>=167}"><a href="activityIndex_awardmon.mht" target="_blank"><img src="images/activity_ioc.png" alt=""  style="position: absolute;"/></a></s:if><a href="financeDetail.mht?id=${finance.id}" target="_blank" > 
                     <shove:img src="${finance.imgPath}" defaulImg="images/hslogo_42.jpg" alt="标" width="80" height="79"></shove:img></a></div>
		         <div class="w640 fl">
		            <div class="title clearfix">
		              <s:if test="#finance.borrowWay ==1">
		              <span class="jing" title="净值借款">净</span>
                      </s:if>
                      <s:if test="#finance.borrowWay ==2">
                          <span class="miao" title="秒还借款">秒</span>
                      </s:if>
                      <s:if test="#finance.borrowWay ==3">
                      </s:if>
                      <s:if test="#finance.borrowWay ==4">
                          <span class="shi"  title="实地考察借款">实地</span>
                      </s:if>
                      <s:if test="#finance.borrowWay ==5">
                          <span class="bao" title="机构担保借款" >保</span>
                      </s:if>
		            <h2><a href="financeDetail.mht?id=${finance.id}" target="_blank" >
                      <shove:sub size="15" value="#finance.borrowTitle" suffix="..."/><s:if test="%{#finance.deadline>=3 && #finance.id>=167}"><img style="margin-left: 20px;margin-bottom: 5px;" src="images/award_ioc.png" /><font color="orange" style="font-size: 14px;margin-right: 5px;font-weight: bold;">+0.${finance.deadline}%</font></s:if>
                       </a>&nbsp; </h2></div>
		            <div class="datum"><span>年利率：<i class="orange f20"><s:property value="#finance.annualRate" default="0"/></i>%</span><span>总额：<i class="f20"><s:property value="#finance.borrowAmount" default="0"/></i></span><span>期限：<i class="f20"><s:property value="#finance.deadline" default="0"/></i><s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                        <s:else>天</s:else></span></div>
		            <div class="">
		               <div class="progress_bg fl"><div class="progress" style="width:<s:property value="#finance.schedules" default="0"/>%;"></div></div><s:property value="#finance.schedules" default="0"/>%
		            </div>
		         </div>
		         <div class="w200 fl">
		            <div class="f12 icon_tip"><span class="icon01">满标当日计息</span><span class="icon02">安全保障</span></div>
		            <div class="fr mt30">
		            <s:if test="%{#finance.borrowStatus == 1}">
                     <span class="btn01">初审中</span>
                </s:if>
                 <s:elseif test="%{#finance.borrowStatus == 2}">
                     <s:if test="%{#finance.borrowShow == 2}"><a href="javascript:void(0);" class="btn01"  onclick="checkTou(${finance.id},2)">立即认购</a></s:if>
                     <s:elseif test="#finance.displayTime == 0 && #finance.borrowShow == 1"><div class="gray_btn f12"  name="timers"    _time="${finance.ShowTime}"  _id="${finance.id}">预发布</div></s:elseif>
                      <s:else><a href="javascript:void(0);"  class="btn01" onclick="checkTou(${finance.id},1)">立即投标</a></s:else>
                </s:elseif>
                <s:elseif test="%{#finance.borrowStatus == 3}">
                    <span class="gray_btn">复审中</span>&nbsp;
                </s:elseif>
                <s:elseif test="%{#finance.borrowStatus == 4}">
                    <span class="gray_btn">
                    <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
                    <s:else>还款中</s:else>
                   </span>&nbsp;
                </s:elseif>
                <s:elseif test="%{#finance.borrowStatus == 5}">
                   <span class="gray_btn">已还完</span>&nbsp;
                </s:elseif>
                <s:else>
                      <span class="gray_btn">流标</span>&nbsp;
                </s:else>
		            </div>
		         </div>
		       </li>
        </s:iterator>
    </s:if><s:else><div class="center m15"><b>暂无数据,再试试选择其它条件吧！！！</b></div></s:else>
     </ul>
     <s:if test="pageBean.page!=null || pageBean.page.size>0">
     <div class="page">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum }"></shove:page>
     </div>
     </s:if>
   </div>
 --%>