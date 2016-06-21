<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sp2p.constants.IConstants"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
    <title><%=IConstants.SEO_TITLE%></title>
     <link id="skin" rel="stylesheet" href="css/jbox/Skins2/Blue/jbox.css" />
     <link rel="stylesheet" type="text/css" href="css/new_inside.css" />
      <link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
     <link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
      <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta name="keywords" content="<%=IConstants.SEO_KEYWORDS%>">
    <meta name="description" content="<%=IConstants.SEO_DESCRIPTION %>">
    <%=IConstants.SEO_OTHERTAGS %>
    </head>
    <body>
        <!-- 引用头部公共部分 -->
        <jsp:include page="/include/top.jsp"></jsp:include>
        <input type="hidden" id="idStr" value="${idStr}" />
        
        <div class="ny_content clearfix">
  <div class="w1002 location"><a href="<%=application.getAttribute("basePath")%>">首页</a> > <a href="bidlist.mht">我要投资</a> > <s:property value="#request.borrowDetailMap.borrowTitle"
                                default="---" /></div>
  <div class="bd_detail w1002 clearfix" style="position:relative;">
  <s:if test="%{#request.borrowDetailMap.deadline>=3 && #request.borrowDetailMap.id>=167}"><a href="activityIndex_awardmon.mht" target="_blank"><img src="images/activity_ioc.png" alt=""  style="position: absolute; left: 0px; top: 0px;"/></a></s:if>
     <div class="info fl">
        <div class="tit clearfix">
           <div class="fl"><i class="ui_item_detail_icon ui_item_pic_qiye"></i><b class="f18"><s:property value="#request.borrowDetailMap.borrowTitle"
                                default="---" /></b><s:if test="%{#request.borrowDetailMap.deadline>=3 && #request.borrowDetailMap.id>=167}"><img style="margin-left: 20px;margin-bottom: 5px;" src="images/award_ioc.png" /><font color="orange" style="font-size: 14px;margin-right: 5px;font-weight: bold;">+0.${borrowDetailMap.deadline}%</font></s:if></div>
        </div>
         <ul class="datum fl clearfix">
            <li>
              <div class="pr50">借款总额(元)<br/><em class="f30 orange">￥<s:property
                                                value="#request.borrowDetailMap.borrowAmount" default="---" /></em><em class="f14"></em></div>
              <div class="pr50">年利率<br/><em class="f30 orange"><s:property
                                                value="#request.borrowDetailMap.annualRate" default="---" />%</em><em class="f14"></em></div>
              <div class="pr30">借款期限<s:if test="%{#request.borrowDetailMap.isDayThe ==1}">(月)</s:if>
                                            <s:else>(天)</s:else><br/><em class="f30 orange"><s:property
                                                value="#request.borrowDetailMap.deadline" default="---" />
                                            </em><em class="f14"></em></div>
            </li>
            <li><div style="width: 56%;">还款方式:
            <s:if test="%{#request.borrowDetailMap.isDayThe ==2}">
         到期还本付息
        </s:if> <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款(等额本息还款)</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
            </div>
            <div class="fr" style="width: 44%;">
            <div id="h_content" >
            		<div class="ml30 mr10"><s:property value="#request.borrowWay" default="---" /></div>
                    <div class="help-tip">
                      <p>${introduce }</p>
                    </div> 
                </div>
                           
           </div>
            
            
            </li>
            <li> 
               <div class="fl mr10">当前进度：</div><div class="fl"><div class="progress_bg fl"><div class="progress" style="width:${borrowDetailMap.schedules}%;"></div></div><s:property
                                                            value="#request.borrowDetailMap.schedules" default="0" />%</div>
               <div class="ml30">
               <s:if test="%{#request.borrowDetailMap.borrowStatus == 4}">
                                        </s:if>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
                                        </s:elseif>
                                        <s:else>
                                              <s:if test='#request.borrowDetailMap.displayTime != 0' ><i class="ui_item_detail_icon ui_item_pic_time"></i><span>剩余时间 ：</span> <span id="remainTimeOne"></span></s:if>
                                        </s:else>
               </div>
            </li>
       </ul>
     </div>
     <div class="tender fl">
     <s:if test="#session.user!=null"><p class="fr"><input name=""  onclick="window.location.href='rechargeInit.mht'"  value="充值" type="button" class="n_blue_btn"    /></p></s:if>
       <p>剩余可投金额：<span class="orange f18">￥<s:property     value="#request.borrowDetailMap.investAmount" default="0" /></span>元</p>
       <p>账户可投金额： <s:if test="#session.user!=null"><span class="orange f18">￥${usableSum}</span>元</p></s:if><s:else><a href="login.mht"  style="color:#09F;text-decoration: underline;">登录</a>后可见</s:else>
       <p><input name="borrowAmount"  id="borrowAmount"  onblur="validateAmount()"    placeholder="起投金额：${borrowDetailMap.minInvestCal }"    style="ime-mode:disabled;"  ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onKeyPress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;"  <s:if test="#request.borrowDetailMap.borrowStatus != 2">disabled="disabled"</s:if>    type="text"   class="inptext w300" maxlength="15" />元</p>
       <span class="red pl10">*投资金额只能是${jebs}的整数倍</span>
       <p>
       <s:if test="#request.borrowDetailMap.borrowStatus == 2">
                <s:if test='#request.borrowDetailMap.displayTime == 0' >
                    <input type="button"   disabled='disabled'   value='预发布'  class='grey_btn'  name="timers"    _time="${borrowDetailMap.ShowTime}"  _id="${finance.id}" />
                    <input type="button" class="red_btn" id="investnow"  value='立即投标'  style="display: none" />
                 </s:if>
                 <s:else>
                    <input type="button" class="red_btn" id="investnow"  value='立即投标'   />
                 </s:else>
       </s:if>
       
       <s:else>
                <s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
                                            <input type="button" class="grey_btn"       value="初审中"/>
            </s:if>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
                                            <input type="button" class="grey_btn"       value="复审中"/>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
                                            <input type="button" class="grey_btn"       value="已满标"/>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
                                           <input type="button" class="grey_btn"       value="已还完"/>
              </s:elseif>
                                        <s:else>
                                            <input type="button" class="grey_btn"       value="回购中"/>
                </s:else>
       </s:else>
       </p>
       <p class="mt5"><i class="ui_item_detail_icon ui_item_pic_count"></i>您的预期收益：￥<span id="yjsy">0.00</span>元 </p>
    </div>
  </div>
  
  <div class="plan_tab clearfix w1002">
        <!--<ul>                                        
          <li id="one1" onclick="setTab('one',1,5)"  class="hover"><a href="#a">项目详细</a></li>
          <li id="one2" onclick="setTab('one',2,5)" >风控信息</li>
          <li id="one3" onclick="setTab('one',3,5)">相关材料</li>
          <li id="one4" onclick="setTab('one',4,5)" >还款信息</li>
          <li id="one5" onclick="setTab('one',5,5)" >投资记录</li>
          <li style="border-left:1px solid #d1dfea;padding:0;"></li>
        </ul>-->
         <ul class="fl w1002">                                        
          <li><a href="#detail">项目详细</a></li>
          <li><a href="#info">风控信息</a></li>
          <s:if test="%{#request.related !=null && #request.related.size()>0}"><li><a href="#cailiao">相关材料</a></li></s:if>
          <li><a href="#payoff">还款信息</a></li>
          <li><a href="#jilu">投资记录</a></li>
          <li style="border-left:1px solid #d1dfea;padding:0;"></li>
          <div class="mt7">
          <s:if test="#request.borrowDetailMap.borrowStatus == 2">
       <s:if test='#request.borrowDetailMap.displayTime == 0' > <a class="btn01  fr  mr10"  href="javascript:void(0)" id="invest"  style="display: none">立即投标</a><a class="gray_btn  fr  mr10 f12"  href="javascript:void(0)"  id="foreInvest"  name="timers"    _time="${borrowDetailMap.ShowTime}"  _id="${finance.id}">预发布</a> </s:if>
       <s:else> <a class="btn01  fr  mr10"  href="javascript:void(0)" id="invest" >立即投标</a> </s:else>
       </s:if>
       <s:else>
                <s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
                <a class="gray_btn fr  mr10"  href="javascript:void(0)" >初审中</a>
            </s:if>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
                                        <a class="gray_btn fr  mr10"  href="javascript:void(0)">复审中</a>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
                                        <a class="gray_btn fr  mr10"  href="javascript:void(0)" >已满标</a>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
                                        <a class="gray_btn fr  mr10"  href="javascript:void(0)" >已还完</a>
              </s:elseif>
                                        <s:else>
                                        <a class="gray_btn fr  mr10"  href="javascript:void(0)" >回购中</a>
                </s:else>
       </s:else>
          </div>
        </ul>
  </div>
  <div class="contain_main clearfix " id="detail" >
    <!--用户信息-->
    <div class="pub_list_title"><i class="ui_item_detail_icon ui_item_pic_project"></i>项目详情</div>
    <div class="small_list_title">● 借款方信息</div>
    <div class="plan_tab_con clearfix">
          <table width="100%" border="0" cellspacing="0">
          <tr>
                                <td>
                                    性别：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong><s:property  value="#request.borrowUserMap.sex" default="未填写" /></strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.sex}</s:else>
                                </td>
                                <td>
                                    年龄：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.age}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.age}</s:else>
                                </td>
                                <td colspan="3">
                                    婚姻状况：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.maritalStatus}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.maritalStatus}</s:else>
                                </td>
                                <td colspan="3">
                                    工作城市：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</s:else>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    公司行业：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.companyLine}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.companyLine}</s:else>
                                </td>
                                <td>
                                    公司规模：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.companyScale}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.companyScale}</s:else>
                                </td>
                                <td colspan="3">
                                    职位：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.job}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.job}</s:else>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    毕业学校：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.school}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.school}</s:else>
                                </td>
                                <td>
                                    学历：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.highestEdu}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.highestEdu}</s:else>
                                </td>
                                <td colspan="3">
                                    入学年份：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong>${borrowUserMap.eduStartDay}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.eduStartDay}</s:else>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    有无购房：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.hasHourse}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.hasHourse}</s:else>
                                </td>
                                <td>
                                    有无购车：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.hasCar}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.hasCar}</s:else>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    有无房贷：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.hasHousrseLoan}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.hasHousrseLoan}</s:else>
                                </td>
                                <td>
                                    有无车贷：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong>${borrowUserMap.hasCarLoan}</strong>
                                    </s:if>
                                    <s:else>${borrowUserMap.hasCarLoan}</s:else>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
      </table>
    </div>
    <div class="dashed clearfix"></div>
    
    <div class="small_list_title">● 项目情况</div>
    <div class="list_info">${borrowDetailMap.borrowInfo }
    </div>
  </div>
 <s:if test="#session.user!=null">
     <div class="contain_main clearfix " id="info">
    <div class="pub_list_title"><i class="ui_item_detail_icon ui_item_pic_fk"></i>风控信息</div>
    <s:if test="#request.borrowDetailMap.borrowWay ==5">
            <div class="small_list_title">● 担保信息</div>
            <div class="list_info">
            <p>担保方：<span class=" blue" onclick="tztysm('${borrowDetailMap.introduce}')"  >${borrowDetailMap.agent }</span></p>
            <p>
            担保方式：<span class=" blue" onclick="tztysm('${borrowDetailMap.agentWayintroduce}')"  >${borrowDetailMap.agentWay }</span></p>
            </p>
            </div>
    </s:if>
    <div class="small_list_title">● 风控意见</div>
    <div class="list_info">${borrowDetailMap.auditOpinion }</div>
    <div class="small_list_title">● 审核信息</div>
    <div class="plan_tab_con01 clearfix">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tc">
      <tr class="leve_titbj">
        <td width="330">审核项目</td>
        <td width="156" class="w50">状态</td>
        <td width="362">通过日期</td>
      </tr>
      <s:if test="%{#request.list !=null && #request.list.size()>0}">
                                    <s:iterator value="#request.list" id="bean">
                                        <tr>
                                            <td align="center">
                                                ${bean.name}
                                            </td>
                                            <td align="center">
                                                <s:if test="#bean.auditStatus == 1">
                                      审核中
          </s:if>
                                                <s:elseif test="#bean.auditStatus == 2">
                                       审核失败
          </s:elseif>
                                                <s:elseif test="#bean.auditStatus == 3">
                                                    <img src="images/right.png" width="28" height="28" />
                                                </s:elseif>
                                                <s:else>
                                     等待资料上传
          </s:else>
                                            </td>
                                            <td align="center">
                                                ${bean.passTime}
                                            </td>
                                            <%--<td align="center" class="ck">
                                                <s:if test="#bean.visiable== 1 && #bean.auditStatus==3">
                                                <!-- 
                                                    <a
                                                        href="javascript:showImg11('${bean.materAuthTypeId}','${bean.userId}','${user.id }');">查看</a> -->
                                                    <a href="javascript:void(0);" onclick="showImg('${bean.materAuthTypeId}','${bean.userId}','${user.id }'); return false;">查看</a>
                                                </s:if>
                                                <s:else>---
          </s:else>
                                            </td>
                                        --%></tr>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <td colspan="4" align="center">
                                        没有数据
                                    </td>
                                </s:else>
    </table>
    </div>
  </div>
      
  <s:if test="%{#request.related !=null && #request.related.size()>0}">
  <div class="contain_main clearfix" id="cailiao">
    <div class="pub_list_title"><i class="ui_item_detail_icon ui_item_pic_cl"></i>相关材料</div>
    <input type="button" class="fl arrow_fl" id="left" />
    <div id="photo" class="fl">
      <div class="Cont">
        <div class="ScrCont" id="viewer">
          <div class="List1" id="viewerFrame">
                     <s:iterator value="#request.related" id="bean"  status="s">
                             <div>
                                  <p><a href="${bean.imgPath}" rel="lightbox[plants]" title="${bean.description}"><img src="${bean.imgPath}" alt="Plants: image ${s.index } 0f  ${fn:length(related)} thumb" /></a>${bean.name }<br/>
                             </div>
                      </s:iterator>
             
          </div>
        </div>
      </div>
</div>
    <input type="button"  id="right" class="fl arrow_rig"/>
  </div></s:if>
  
  
  <div class="contain_main clearfix " id="payoff">
     <div class="pub_list_title"><i class="ui_item_detail_icon ui_item_pic_hk"></i>还款信息</div>
     <div class="plan_tab_con01 mt10 clearfix">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tc">
          <tr class="leve_titbj">
            <td width="330">还款日期</td>
            <td width="156">已还本息</td>
            <td width="362">待还本息</td>
            <td width="362">状态</td>
          </tr>
          <s:if test="%{#request.repayList !=null && #request.repayList.size()>0}">
      <s:iterator value="#request.repayList" id="bean">
      <tr>
          <td align="center">${bean.repayDate}</td>
          <td align="center">${bean.hasPI} </td>
          <td align="center">${bean.stillPI} </td>
          <td align="center">
          <s:if test="#bean.repayStatus == 1">
                                      未偿还
          </s:if>
          <s:elseif test="#bean.repayStatus == 2">
                                       已偿还
          </s:elseif>
          <s:elseif test="#bean.repayStatus == 3">
                                       偿还中
          </s:elseif>
          </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="7" align="center">没有数据</td>
  </s:else>
    </table>
    </div>
  </div>
  <div class="contain_main clearfix " id="jilu">
     <div class="pub_list_title"><i class="ui_item_detail_icon ui_item_pic_fk"></i>投资记录</div>
    <div class="fr mt10">
        <span class="mr10" style=" margin-right: 35px;">目前总投资金额：<em class="f18 orange">${borrowDetailMap.hasInvestAmount}</em>元</span>
     </div>
     <div class="clear"></div>
     <div class="plan_tab_con01 clearfix">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tc">
          <tr class="leve_titbj">
            <td width="330">序号</td>
            <td width="156">投标人</td>
            <td width="362">投标金额</td>
            <td width="362">投标时间</td>
          </tr>
          <s:if    test="%{#request.investList !=null && #request.investList.size()>0}">
                                    <s:iterator value="#request.investList" id="investList"  status="s" >
                                        <tr>
                                        <td>${s.index+1}</td>
                                            <td align="center">
                                                <!--<a href="userMeg.mht?id=<s:property value="#investList.investor"/>" target="_blank"> -->
                                                    <s:property value="#investList.username" default="---" /> <!--   creditedStatus==2 代表该用户在转让债权 -->
                                                    <s:if test="#investList.creditedStatus !=null && #investList.creditedStatus==2 ">
                                                        <img src="images/zrico.jpg" width="30" height="16" />
                                                    </s:if>
                                                <!--</a> -->
                                            </td>
                                            <td align="center" class="fred">
                                                ￥
                                                <s:property value="#investList.investAmount" />
                                            </td>
                                            <td align="center">
                                                <s:property value="#investList.investTime" />
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <td colspan="4" align="center">
                                        没有数据
                                    </td>
                                </s:else>
    </table>
    </div>
   </div> 
 </s:if>
   <s:else>
   <div class="contain_main  "  style="text-align: center;font-size: 18px;padding-top: 20px"><span>更多信息，<a href="login.mht"  style="color:#09F;text-decoration: underline;">登录</a>后可见</span></div>
   </s:else>
</div>
        <div id="remainSeconds" style="display: none;">
            ${borrowDetailMap.remainTime}
        </div>
        <input type="hidden" id="bid" value="${borrowDetailMap.id}" />
        <!-- 引用底部公共部分 -->
        <jsp:include page="/include/footer.jsp"></jsp:include>

<script src="js/jquery-ui-1.8.18.custom.min.js"></script>
<script src="js/jquery.smooth-scroll.min.js"></script>
<script src="js/lightbox.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>

<script>

window.onload = timersTask;
var item = $("*[name='timers']");
function timersTask(){
    if(item.length>0){
        for(var i = 0;i<item.length;i++){
            var time = item[i].getAttribute("_time");
            SetRemainTime1(time,i);
        }
       window.setTimeout("timersTask()", 1000)
    }
}

function SetRemainTime1(time,i) {
    if (time > 0) {
        time = time - 1;
        var second = Math.floor(time % 60); // 计算秒     
        var minite = Math.floor((time / 60) % 60); //计算分 
        var hour = Math.floor((time / 3600) % 24); //计算小时 
        var day = Math.floor((time / 3600) / 24); //计算天 
        var times = day + "天" + hour + "时" + minite + "分" + second + "秒";
        if(item[i].value){
        	item[i].value=times;
        }else{
        	item[i].innerHTML=times;
        }
    } else {//剩余时间小于或等于0的时候，就停止间隔函数 
    	item[i].style.display = "none";
        $("#invest").attr("style","display:block");
        $("#investnow").attr("style","display:block");
    }
    item[i].setAttribute("_time",time);
}
</script>

<script type="text/javascript">

function tztysm(introduce){
    jQuery.jBox(introduce, {
        title: "简介",
        width: 700,
        height: 250,
        buttons: { '关闭': true }
    });
}


function rateCalculate(borrowSum){//利息计算
    param["paramMap.borrowSum"] = borrowSum;
    param["paramMap.yearRate"] = ${borrowDetailMap.annualRate};//利率
    param["paramMap.borrowTime"] = ${borrowDetailMap.deadline};//期限
    param["paramMap.Way"] = ${borrowDetailMap.paymentMode};//方式
    param["paramMap.bidReward"] = 0;
    param["paramMap.bidRewardMoney"] = 0;
    
    $.ajax({
        type : "post",
        async:true,
        data:param,
        url : "incomeCalculate.mht",
        success : function(data){
            for(var i = 0; i < data.length; i ++){
                $("#yjsy").html(data[i].rateSum) ;
            }
        },
        error:function(){
            $("#yjsy").html("0.00") ;
        }
    });
}



var SysSecond;
var InterValObj;
$(function(){   
    //获取要定位元素距离浏览器顶部的距离
    var navH = $(".plan_tab").offset().top;
    //滚动条事件
    $(window).scroll(function(){
        //获取滚动条的滑动距离
        var scroH = $(this).scrollTop();
        //滚动条的滑动距离大于等于定位元素距离浏览器顶部的距离，就固定，反之就不固定
        if(scroH>=navH){
            $(".plan_tab").css({"position":"fixed","top":0,"left":"50%","margin":"0 0 0 -505px"});
            $(this).addClass("hover");//就移除
        }else if(scroH<navH){
            $(".plan_tab").css({"position":"static","left":"50%","margin":"0 auto","margin-top":"25px"});
            $(this).removeClass("hover");//如果没有就加
        }
       // console.log(scroH==navH);
    })
})
    
    function validateAmount(){
	   var  minsum = parseFloat('${borrowDetailMap.minTenderedSum}'.replace(/[^\d\.-]/g, ""));
	   var jebs = parseInt('${jebs}');
	   var investAmount = parseFloat('${borrowDetailMap.investAmount}'.replace(/[^\d\.-]/g, ""));
       var usableSum = parseFloat('${usableSum}'.replace(/[^\d\.-]/g, ""));
	   var amount = $("#borrowAmount").val();
		 if (amount>=minsum) {
	         var scale = parseInt(amount/jebs);
	         if(scale!=(amount/jebs) && amount!=investAmount){
	        	 jQuery.jBox.tip("投资金额只能是"+jebs+"的倍数(特殊情况除外\n如：投资金额等于剩余可投金额)","提示");
	        	 return false;
	         }else{
	        	 return true;
	         }
	     }else{
	          jQuery.jBox.tip("投资金额不能小于最小投资金额)","提示");
	          return false
	     }
         
    }


    $(function(){
        showScroll();
        SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
        InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
        var param = {};
        
        $("#borrowAmount").keyup(function(event){
        	if(/\D/.test(this.value)){
                this.value=this.value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')
                return
             }
        	
            var investAmount = '${borrowDetailMap.investAmount}'
            var usableSum = '${usableSum}';
            var jebs = parseInt('${jebs}');

            if(this.value>parseFloat(usableSum.replace(/[^\d\.-]/g, ""))){
            	jQuery.jBox.tip("投资金额已超过你的可用余额","提示");
            	var scale = parseInt(usableSum.replace(/[^\d\.-]/g, ""))/jebs;
                this.value=scale*jebs;
            }
            
            if(this.value>parseFloat(investAmount.replace(/[^\d\.-]/g, ""))){
            	jQuery.jBox.tip("投资金额已超过可投金额","提示");
                var scale = parseInt(investAmount.replace(/[^\d\.-]/g, ""))/jebs;
                //this.value=scale*jebs;
                this.value = parseFloat(investAmount.replace(/[^\d\.-]/g, ""));
            }
            
            //处理文本框中的键盘事件
            var myEvent = event || window.event;
            var keyCode = myEvent.keyCode;
            var sy;
            //处理输入框键盘事件
            var borrowAmount = $("#borrowAmount").val();
            if(borrowAmount==null || $.trim(borrowAmount)==''){//输入框内容为空的时候
                $("#yjsy").html("0.00")
                return ;
          }else{
              rateCalculate(borrowAmount)
          }
           
        });
        
        
        $("#investnow").click(function(){
        	if(!validateAmount()){
        		return;
        	}
        	var step = '${session.user.authStep}';
        	var username = '${borrowUserMap.username_2}';
            var uname = '${user.userName}';
            var bid = $('#bid').val();
            if (username == uname) {
                alert("无效操作,不能投自己发布的标");
                return false;
            } 
            
            var  minsum = ${borrowDetailMap.minTenderedSum};
            var borrowAmount = $("#borrowAmount").val();
            if(borrowAmount<minsum){
                alert("最小投资金额不能小于"+minsum+"元");
                return ;
            }
            
            param['id'] = '${borrowDetailMap.id}';
            param['borrowAmount'] = borrowAmount;
            if (step < 5) {
                window.location.href = 'financeInvestInit.mht?id='+bid+'&borrowAmount='+borrowAmount;
                return false;
            }
            $.post('financeInvestInit.mht',param,function(data) {
                var callBack = data.msg;
                        if (callBack != undefined) {
                            alert(callBack);
                        } else {
                            window.location.href = 'financeInvestInit.mht?id='+bid+'&borrowAmount='+borrowAmount;
                        }
            });
        	
        })
        
        $('#invest').click(function() {
        	
                var step = '${session.user.authStep}';
                var username = '${borrowUserMap.username_2}';
                var uname = '${user.userName}';
                var bid = $('#bid').val();
                if (username == uname) {
                    alert("无效操作,不能投自己发布的标");
                    return false;
                } 
                param['id'] = '${borrowDetailMap.id}';
                if (step < 5) {
                    window.location.href = 'financeInvestInit.mht?id='+bid;
                    return false;
                }
                $.post('financeInvestInit.mht',param,function(data) {
                    var callBack = data.msg;
                            if (callBack != undefined) {
                                alert(callBack);
                            } else {
                                window.location.href = 'financeInvestInit.mht?id='+bid;
                            }
                });
        });
        $('#focusonBorrow').click(function() {
            var username = '${borrowUserMap.username_2}';
            var uname = '${user.userName}';
            if (username == uname) {
                alert("您不能关注自己发布的借款");
                return false;
            }
            param['paramMap.id'] = '${borrowDetailMap.id}';
            $.dispatchPost('focusonBorrow.mht', param, function(data) {
                var callBack = data.msg;
                alert(callBack);
            });
        });
        $('#focusonUser').click(function() {
            var username = '${borrowUserMap.username_2}';
            var uname = '${user.userName}';
            if (username == uname) {
                alert("您不能关注自己");
                return false;
            }
            param['paramMap.id'] = '${borrowUserMap.userId}';
            $.dispatchPost('focusonUser.mht', param, function(data) {
                var callBack = data.msg;
                alert(callBack);
            });
        });
        $('#sendmail').click(function() {
            var id = '${borrowUserMap.userId}';
            var username = '${borrowUserMap.username_2}';
            var url = "mailInit.mht?id=" + id + "&username=" + username;
            var uname = '${user.userName}';
            if (username == uname) {
                alert("您不能给自己发送站内信");
                return false;
            }
            $.dispatchPost('mailInit.mht', param, function(data) {
                 $.jBox("iframe:" + url, {
                     title: "发送站内信",
                     width: 500,
                     height: 400,
                     buttons: {  }
             });
            });
        });
        $('#reportuser').click(function() {
            var id = '${borrowUserMap.userId}';
            var username = '${borrowUserMap.username_2}';
            var url = "reportInit.mht?id=" + id + "&username=" + username;
            var uname = '${user.userName}';
            if (username == uname) {
                alert("您不能举报自己");
                return false;
            }
            $.dispatchPost('reportInit.mht', param, function(data) {
                 $.jBox("iframe:" + url, {
                     title: "举报此人",
                     width: 500,
                     height: 400,
                     buttons: {  }
              });
            });
        });
        $('#audit').click(function() {
            var id = $('#idStr').val();
            $(this).addClass('on');
            $('#repay').removeClass('on');
            $('#collection').removeClass('on');
            param['paramMap.id'] = id;
            $.dispatchPost('financeAudit.mht', param, function(data) {
                $('#contentList').html(data);
            });
        });
        $('#repay').click(function() {
            var id = $('#idStr').val();
            $(this).addClass('on');
            $('#audit').removeClass('on');
            $('#collection').removeClass('on');
            param['paramMap.id'] = id;
            $.dispatchPost('financeRepay.mht', param, function(data) {
                $('#contentList').html(data);
            });

        });
        $('#collection').click(function() {
            var id = $('#idStr').val();
            $(this).addClass('on');
            $('#audit').removeClass('on');
            $('#repay').removeClass('on');
            param['paramMap.id'] = id;
            $.dispatchPost('financeCollection.mht', param, function(data) {
                $('#contentList').html(data);
            });

        });
        initListInfo(param);
        
    })
    
    
        //将时间减去1秒，计算天、时、分、秒 
        function SetRemainTime() {
            if (SysSecond > 0) {
                SysSecond = SysSecond - 1;
                var second = Math.floor(SysSecond % 60); // 计算秒     
                var minite = Math.floor((SysSecond / 60) % 60); //计算分 
                var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
                var day = Math.floor((SysSecond / 3600) / 24); //计算天 
                var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
                $("#remainTimeOne").html(times);
                $("#remainTimeTwo").html(times);
            } else {//剩余时间小于或等于0的时候，就停止间隔函数 
                window.clearInterval(InterValObj);
                //这里可以添加倒计时时间为0后需要执行的事件 
            }
        }
    
    function showScroll(){
            $(window).scroll( function() { 
                var scrollValue=$(window).scrollTop();
                scrollValue > 100 ? $('div[class=scroll]').fadeIn():$('div[class=scroll]').fadeOut();
            } );    
            $('#scroll').click(function(){
                $("html,body").animate({scrollTop:0},200);  
            }); 
        }
    function initListInfo(param) {
        param['paramMap.id'] = '${borrowDetailMap.id}';
        $.dispatchPost('borrowmessage.mht', param, function(data) {
            $('#msg').html(data);
        });
    }
    function showImg(typeId, userId,ids) {
        var session = '<%=session.getAttribute("user")%>';
        if(session =='null') {
            window.location.href = 'login.mht';
            return;
        }
        var url = "showImg.mht?typeId=" + typeId + "&userId=" + userId;
        $.jBox("iframe:" + url, {
            title : "查看认证图片(点击图片放大)",
            width : 500,
            height : 428,
            buttons : {}
        });
    }
    function close() {
        ClosePop();
    }
</script>
<script type="text/javascript">
  jQuery(document).ready(function($) {
      $('a').smoothScroll({
        speed: 1000,
        easing: 'easeInOutCubic'
      });

      $('.showOlderChanges').on('click', function(e){
        $('.changelog .old').slideDown('slow');
        $(this).fadeOut();
        e.preventDefault();
      })
  });

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-2196019-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<script type="text/javascript" src="js/jquery.imageScroller.js"></script>
<script type="text/javascript">
    if(${fn:length(related)}>6){
        var j = jQuery.noConflict();
        j("#viewer").imageScroller({
            next:"right",
            prev:"left",
            frame:"viewerFrame",
            width:120,
            child: "div",
            auto: true
        });
    }
</script>
    </body>
</html>
