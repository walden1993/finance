<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sp2p.constants.IConstants"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
     <s:set scope="request" value="#request.borrowDetailMap.borrowTitle" var="title" />
     <jsp:include page="/include/head.jsp"></jsp:include>
     <link rel="stylesheet" href="css/bidlist.css" type="text/css"  media="screen"/>
     <link rel="stylesheet" href="framework/poshytip-1.2/src/tip-twitter/tip-twitter.css" type="text/css"  media="screen"/>
     <script type="text/javascript">var menuIndex = 1;</script>
    </head>
    <body>
        <!-- 引用头部公共部分 -->
        <jsp:include page="/include/top.jsp"></jsp:include>
        
        <div class="wrap mb15">
        	<div class="w950 detail_borrow ">
        		<p class="pt10 pb5 disctription"><a href="<%=application.getAttribute("basePath")%>">首页</a> > <a href="bidlist.mht">我要投资</a> > <s:property value="#request.borrowDetailMap.borrowTitle"
                                default="---" /></p>
        		<div class="one">
        		
        		 
	        		
        		<div class="info_left fl p-relative">
        		<%-- <s:if test="#request.borrowDetailMap.id>=296 && #request.borrowDetailMap.deadline>=3">
	          		<span class="ico"><img src="images/activity_ioc.png" /></span>
	          	</s:if>
        		<s:if test="#request.borrowDetailMap.id>=263 && #request.borrowDetailMap.deadline==3">
		          	<span class="ico"><img src="images/award_5.png" /></span>
		          </s:if>
		           <s:if test="#request.borrowDetailMap.id>=263 && #request.borrowDetailMap.deadline==1">
		          	<span class="ico"><img src="images/award_2.png" /></span>
		          </s:if> --%>
        		
         <div style="margin:10px 22px 0 22px;">
         
          <div class="info_left_title">
	      <div class="fl bold700" style="position:relative;max-width:550px;overflow: hidden;white-space:nowrap;text-overflow:ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;">${borrowDetailMap.borrowTitle}
			  
		   </div>
           
          <%--<span class="info_jiang">理财奖励+0.2%</span> --%> 
           
       </div>
       <div class="info_left_main">
         <table cellpadding="0" cellspacing="0" class="infoTab" width="100%">
           <tbody><tr valign="bottom">
               
			 <td><h1>借款金额</h1><h2><span>${borrowDetailMap.borrowAmount}</span>元</h2></td>
             <td><h1>年化利率</h1><h2><span class="orange">${borrowDetailMap.annualRate}</span>%</h2></td>
             <td><h1>借款期限</h1><h2><span>${borrowDetailMap.deadline}</span><s:if test="%{#request.borrowDetailMap.isDayThe ==1}">月</s:if>
                                            <s:else>天</s:else></h2></td>
             <td style="border:none;"><h1>起投金额</h1><h2><span>${borrowDetailMap.minTenderedSum}</span>元</h2></td>

           </tr>
         </tbody></table>
       </div>
       <div class="info_left_bottom clearfix">
       	<div class="fl w050 ">
		 <div class="info_left_bottom_path fl" >
		   	 <%-- <p>开始时间：<span class="color000">${borrowDetailMap.publishTime}</span></p> --%>
             <p>还款方式：<span class="color000"><s:if test="%{#request.borrowDetailMap.isDayThe ==2}">
         到期还本付息
        </s:if> <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款(等额本息还款)</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif></span></p>
		 </div>
		 <%-- <div class="info_left_bottom_path fl">
		    <p>最高投资限额：<span class="color000"><s:if test="%{#request.borrowDetailMap.maxTenderedSum>0}" >${borrowDetailMap.maxTenderedSum}</s:if><s:else>不限</s:else></span></p>
		 </div> --%>
		 
		 
		 <div class="info_left_bottom_path fl" >
		    <p>担保方式：<span class="color000">${borrowDetailMap.agentWay }<em title="${borrowDetailMap.agentWayintroduce}" class="helplist"></em></span></p>
		 </div>
		 </div>
		 <div class="fr w050">
		 	<div  class="info_left_bottom_path fl" >
		    <p class="fl">标的类型：<span class="color000">${borrowWay}<em title="${introduce}" class="helplist"></em></span>
		    </p>
			 </div>
			 <div class="info_left_bottom_path fl" >
			    <p>担保方：<a class=" blue" href="jg_hrrzdb.mht" target="_blank">${borrowDetailMap.agent }</a></p>
			 </div>
		 </div>
		 
       </div>
         </div>
      
     </div>
      <div class="info_right fr">
         <div style="margin:20px 25px 0 25px;">
         	<div class="progress" style="height:6px;border-radius:0px; width:100%; margin:15px 0px;">
              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${borrowDetailMap.schedules}" aria-valuemin="0" aria-valuemax="100" style="width: ${borrowDetailMap.schedules}%;"> <span class="sr-only">${borrowDetailMap.schedules}</span> </div>
          </div>
		 <div class="info_right_money">
		   <p class="fl">剩余：<span class="color000"><s:property  value="#request.borrowDetailMap.investAmount" default="0.00" /></span> 元</p>
		   <p class="fr">投资进度：<span class="color000">${borrowDetailMap.schedules}%</span></p>
		 </div>
		 
		 <div class="line"></div>
		 
         <div class="info_right_ye">
         	<s:if test="#session.user!=null"><p class="fl" style="max-width: 200px;overflow: hidden;">账户可投余额：<span class="f14"><font class="orange">${usableSum}</font></span> 元</p><a class="fr button-a mt5" href="rechargeInit.mht" target="_blank">充值</a></s:if><s:else>账户可投余额：登录后可见，<a href="login.mht" class="red">立即登录</a></s:else>
         </div>
         
       <div class="info_amount">
          <input type="text" name="borrowAmount"  data-container="body"  data-placement="left" data-content=""  id="borrowAmount"  onblur="validateAmount()"   style="ime-mode:disabled;"  ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onKeyPress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;"    <s:if test="#request.borrowDetailMap.borrowStatus != 2">disabled="disabled"</s:if>   type="text"     placeholder="请输入投资金额，起投${borrowDetailMap.minTenderedSum}元" class="input h40" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'100')}else{this.value=this.value.replace(/\D/g,'')}" onkeyup="" autocomplete="off">
         <div class="clear"></div>
        </div>
        <div class="mt20">
          <input type="password" value=""  class="input h40"  data-container="body"  data-placement="left" data-content=""   id="dealPWD"   placeholder="请输入交易密码" autocomplete="off">
          <s:if test="#session.user!=null"><a class="p10 w blue fl text-right  fancybox.ajax" id="searchDealPwd" href="searchDealPwdSetp1.mht">忘记交易密码?</a></s:if>
        </div>
         <div class="info_right_sy mt20 w">
         	<div class="fl" >预期收益：<span  id="yjsy">0</span>元</div>
         	
         	<s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
                            <input  type="button" class="button_over w120 fr" value="初审中">         
            </s:if>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2}">
                                           <input id="invest" type="button" class="button w120 fr" value="立即投资">
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
                                           <input  type="button" class="button_over w120 fr"   value="复审中"/>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
                                          <input  type="button" class="button_over w120 fr"    value="已满标"/>
            </s:elseif>
                                        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
                                          <input  type="button" class="button_over w120 fr"     value="已还完"/>
              </s:elseif>
                                        <s:else>
                                            <input  type="button" class="button_over w120 fr"    value="回购中"/>
                </s:else>
         	
         </div>
        
         </div> 
     </div>
        		</div>
        		
        		<div class="two mt8 clearfix">
        			<div class="left  fl mb20">
        				<div class="m20">
						  <ul class="nav nav-tabs  f16" role="tablist">
						    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">项目详情</a></li>
						    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">风控信息</a></li>
						    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">还款计划</a></li>
						 <s:if test="%{#request.related !=null && #request.related.size()>0}"><li role="presentation"><a href="#cailiao" aria-controls="cailiao" role="tab" data-toggle="tab">相关材料</a></li></s:if>
						  </ul>
						
						  <div class="tab-content">
						    <div role="tabpanel" class="tab-pane active" id="home">
						    <div class="small_list_title hidden"><h4 class="title p0 h0">借款方信息</h4></div>
						    <div class="plan_tab_con mt30 hidden">
						          <table width="100%" border="0" cellspacing="0" style="line-height: 30px;">
						          <tr>
						                                <td>
						                                   企业名称：
						                                    <s:if test="#request.borrowUserMap.auditperson == 3">
						                                    <shove:sub suffix="*" value="#request.borrowUserMap.orgName " size="2"/>
						                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.orgName " size="2"/></strong>
						                                    </s:if>
						                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.orgName " size="2"/></s:else>
						                                </td>
						                                <td>
						                                    法人代表：
						                                    <s:if test="#request.borrowUserMap.auditperson == 3">
						                                        <strong><shove:sub suffix="***" value="#request.borrowUserMap.legalPerson " size="1"/></strong>
						                                    </s:if>
						                                    <s:else><shove:sub suffix="***" value="#request.borrowUserMap.legalPerson " size="1"/></s:else>
						                                </td>
						                                
						                            </tr>
						                            <tr>
						                            <td>
						                                   注册地址：
						                                    <s:if test="#request.borrowUserMap.auditperson == 3">
						                                        <strong><shove:sub suffix="*************" value="#request.borrowUserMap.address " size="2"/></strong>
						                                    </s:if>
						                                    <s:else><shove:sub suffix="*************" value="#request.borrowUserMap.address " size="2"/></s:else>
						                                </td>
						                                <td>
						                                    联系电话：
						                                    <s:if test="#request.borrowUserMap.auditwork == 3">
						                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.phone " size="3"/></strong>
						                                    </s:if>
						                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.phone " size="3"/></s:else>
						                                </td>
						                            </tr>
						                            <tr>
						                                <td colspan="2">
						                                   营业执照号：
						                                    <s:if test="#request.borrowUserMap.auditwork == 3">
						                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.regNum " size="3"/></strong>
						                                    </s:if>
						                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.regNum " size="3"/></s:else>
						                                </td>
						                            </tr>
						      </table>
						    </div>
						    <div class="dashed clearfix"></div>
						    
						    <div class="small_list_title"><h4 class="title p0 h0 hidden">项目情况</h4></div>
						    <div class="list_info">
						        ${borrowDetailMap.borrowInfo}
						    </div>
						    
						    </div>
						    <div role="tabpanel" class="tab-pane" id="profile">
								    <div class="small_list_title"><h4 class="title p0 h0">审核信息</h4></div>
								    <div class="mt30">
								     <table width="100%" class="table">
								      <tr>
								        <td  align="center">审核项目</td>
								        <td align="center">状态</td>
								        <td align="center">通过日期</td>
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
								                                                    <img src="images/neiye2_44.jpg" width="14" height="15" />
								                                                </s:elseif>
								                                                <s:else>
								                                     等待资料上传
								          </s:else>
								                                            </td>
								                                            <td align="center">
								                                                ${bean.passTime}
								                                            </td>
								                                       </tr>
								                                    </s:iterator>
								                                </s:if>
								                                <s:else>
								                                    <td colspan="4" align="center">
								                                        暂无数据
								                                    </td>
								                                </s:else>
								    </table>
								    </div>
						    		<div class="small_list_title"><h4 class="title p0 h0 hidden">风控意见</h4></div>
								    <div class="list_info">${borrowDetailMap.auditOpinion }</div>
						    </div>
														   
						    <div role="tabpanel" class="tab-pane" id="messages">
						    	<div class="mt10">
							     <table width="100%"  class="table">
							          <tr>
							            <td align="center">还款日期</td>
							            <td align="center">已还本息</td>
							            <td align="center">待还本息</td>
							            <td align="center">状态</td>
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
							      <td colspan="4" align="center">暂无数据</td>
							  </s:else>
							    </table>
							    </div>
						    </div>
						    
						    <%--相关材料 --%>
						     <div  role="tabpanel" class="tab-pane"  id="cailiao">
								      <s:if test="%{#request.related !=null && #request.related.size()>0}">
								    <div id="photo" class="fl">
								      <div class="Cont">
								        <div class="ScrCont" id="viewer">
								          <div class="List1" id="viewerFrame"> 
						                     <s:iterator value="#request.related" id="bean"  status="s">
						                             <div class="fl m20 w025 text-center">
						                                  <p><a href="${bean.imgPath}"  data-fancybox-group="gallery" class="single_image3" rel="lightbox[plants]" title="${bean.description}"><img src="images/blank.gif" class="loadingImg" data-echo="${bean.imgPath}" alt="Plants: image ${s.index } 0f  ${fn:length(related)} thumb" title="三好资本_${bean.name }" height="100"/></a></p>
						                                  <p>${bean.name }</p>
						                             </div>
						                      </s:iterator>
								          </div>
								        </div>
								      </div>
									</div>
								 </s:if>
								</div>
							</div>
						</div>
        			</div>
        			<div class="right fr mb20">
        			   <div class=" m20 ">
        				<div class="mb10 mt10" style="border-bottom: 1px solid #ddd;"><span class="bold400 f16">投资记录</span></div>
        				<table class="table table-bordered table-hover">
				        	<thead align="center">
				        		<tr align="center">
				        			<th>投标人</th>
				        			<th>投标金额(元)</th>
				        			<th>投标时间</th>
				        		</tr>
				        	</thead>
				        	<tbody>
				        		<s:if    test="%{#request.investList !=null && #request.investList.size()>0}">
                                    <s:iterator value="#request.investList" id="investList"  status="s" >
                                        <tr>
                                            <td align="center">
                                                <!--<a href="userMeg.mht?id=<s:property value="#investList.investor"/>" target="_blank"> -->
                                                    <s:property value="#investList.username" default="---" /> <!--   creditedStatus==2 代表该用户在转让债权 -->
                                                    <s:if test="#investList.creditedStatus !=null && #investList.creditedStatus==2 ">
                                                        <img src="images/zrico.jpg" width="30" height="16" />
                                                    </s:if>
                                                <!--</a> -->
                                            </td>
                                            <td align="center" class="fred">
                                                <s:property value="#investList.investAmount" />
                                            </td>
                                            <td align="center">
                                              <s:property value="#investList.investTime" />
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <td colspan="3" align="center">
                                        暂无数据
                                    </td>
                                </s:else>
				        	</tbody>
				        </table>
        			</div>
        		</div>
        		</div>
        </div>
        </div>
        <div class="clearfix"></div>
        <%--
        
        <input type="hidden" id="idStr" value="${idStr}" />
        
        <div class="ny_content clearfix">
  <div class="w1002 location"><a href="<%=application.getAttribute("basePath")%>">首页</a> > <a href="bidlist.mht">我要投资</a> > <s:property value="#request.borrowDetailMap.borrowTitle"
                                default="---" /></div>
<div class="bd_detail w1002 clearfix" style="position:relative;">
  <s:if test="%{#request.borrowDetailMap.deadline>=3 && #request.borrowDetailMap.id>=167}"><a href="activityIndex_awardmon.mht" target="_blank"><img src="images/activity_ioc.png" alt=""  style="position: absolute; left: 0px; top: 0px;"/></a></s:if>

     <div class="info fl">
        <div class="tit clearfix">
           <div class="fl"><i class="ui_item_detail_icon ui_item_pic_ge"></i><b class="f18"><s:property value="#request.borrowDetailMap.borrowTitle"
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
     <s:if test="#session.user!=null"><p class="fr"> <input name=""  onclick="window.location.href='rechargeInit.mht'"  value="充值" type="button" class="n_blue_btn"    /></p></s:if>
       <p>剩余可投金额：<span class="orange f18">￥<s:property      value="#request.borrowDetailMap.investAmount" default="0" /></span>元</p>
       <p>账户可投金额： <s:if test="#session.user!=null"><span class="orange f18">￥${usableSum}</span>元</p></s:if><s:else><a href="login.mht"  style="color:#09F;text-decoration: underline;">登录</a>后可见</s:else>
       <p><input name="borrowAmount"  id="borrowAmount"  onblur="validateAmount()"   style="ime-mode:disabled;"  ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onKeyPress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;"    <s:if test="#request.borrowDetailMap.borrowStatus != 2">disabled="disabled"</s:if>   type="text"   class="inptext w300"  placeholder="起投金额：${borrowDetailMap.minTenderedSum}"  maxlength="15" />元</p>
       <span class="red pl10">*投资金额只能是${jebs}的整数倍</span>
       <p>
       <s:if test="#request.borrowDetailMap.borrowStatus == 2">
                <s:if test='#request.borrowDetailMap.displayTime == 0' >
                    <input type="button"  disabled='disabled' value='预发布'  class='grey_btn'  name="timers"    _time="${borrowDetailMap.ShowTime}"  _id="${finance.id}" />
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
         <ul class="fl w1002">                                        
          <li><a href="#detail">项目详细</a></li>
          <li><a href="#info">风控信息</a></li>
          <s:if test="%{#request.related !=null && #request.related.size()>0}"><li><a href="#cailiao">相关材料</a></li></s:if>
          <li><a href="#payoff">还款信息</a></li>
          <li><a href="#jilu">投资记录</a></li>
          <li style="border-left:1px solid #d1dfea;padding:0;"></li>
          <div class="mt7">
          <s:if test="#request.borrowDetailMap.borrowStatus == 2">
       
       <s:if test='#request.borrowDetailMap.displayTime == 0' > <a class="btn01  fr  mr10"  href="javascript:void(0)" id="invest"  style="display: none">立即投标</a><a class="gray_btn  fr  mr10 f12"  href="javascript:void(0)" id="foreInvest"  name="timers"    _time="${borrowDetailMap.ShowTime}"  _id="${finance.id}">预发布</a> </s:if>
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
                                   企业名称：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                    <shove:sub suffix="*" value="#request.borrowUserMap.orgName " size="2"/>
                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.orgName " size="2"/></strong>
                                    </s:if>
                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.orgName " size="2"/></s:else>
                                </td>
                                <td>
                                    法人代表：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong><shove:sub suffix="***" value="#request.borrowUserMap.legalPerson " size="1"/></strong>
                                    </s:if>
                                    <s:else><shove:sub suffix="***" value="#request.borrowUserMap.legalPerson " size="1"/></s:else>
                                </td>
                                
                            </tr>
                            <tr>
                            <td>
                                   注册地址：
                                    <s:if test="#request.borrowUserMap.auditperson == 3">
                                        <strong><shove:sub suffix="*************" value="#request.borrowUserMap.address " size="2"/></strong>
                                    </s:if>
                                    <s:else><shove:sub suffix="*************" value="#request.borrowUserMap.address " size="2"/></s:else>
                                </td>
                                <td>
                                    联系电话：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.phone " size="3"/></strong>
                                    </s:if>
                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.phone " size="3"/></s:else>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                   营业执照号：
                                    <s:if test="#request.borrowUserMap.auditwork == 3">
                                        <strong><shove:sub suffix="*******" value="#request.borrowUserMap.regNum " size="3"/></strong>
                                    </s:if>
                                    <s:else><shove:sub suffix="*******" value="#request.borrowUserMap.regNum " size="3"/></s:else>
                                </td>
                            </tr>
      </table>
    </div>
    <div class="dashed clearfix"></div>
    
    <div class="small_list_title">● 项目情况</div>
    <div class="list_info">
        ${borrowDetailMap.borrowInfo}
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
                                            <td align="center" class="ck">
                                                <s:if test="#bean.visiable== 1 && #bean.auditStatus==3">
                                                <!-- 
                                                    <a
                                                        href="javascript:showImg11('${bean.materAuthTypeId}','${bean.userId}','${user.id }');">查看</a> -->
                                                    <a href="javascript:void(0);" onclick="showImg('${bean.materAuthTypeId}','${bean.userId}','${user.id }'); return false;">查看</a>
                                                </s:if>
                                                <s:else>---
          </s:else>
                                            </td>
                                       </tr>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <td colspan="4" align="center">
                                        暂无数据
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
      <td colspan="7" align="center">暂无数据</td>
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
                                        暂无数据
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
        <input type="hidden" id="bid" value="${borrowDetailMap.id}" /> --%>
        <!-- 引用底部公共部分 -->
        <jsp:include page="/include/footer.jsp"></jsp:include>


<input type="hidden" id="id" value="${borrowDetailMap.id}" />
<%--
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
            $(this).removeClass("hover");//如果暂无就加
        }
        //console.log(scroH==navH);
    })
})

    $(function(){
        showScroll();
        SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
        InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
        var param = {};
        
        $("#borrowAmount").keyup(function(event){
            if(/\D/.test(this.value)){
                this.value=this.value.replace(/[^\d{1,}\\d{1,}|\d{1,}]/g,'')
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
        		return false;
        	}
            var step = '${session.user.authStep}';
            var username = '${borrowOrgName}';
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
            var username = '${borrowOrgName}';
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
    
    var SysSecond;
        var InterValObj;
        
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
</script> --%>
<script type="text/javascript" src="framework/poshytip-1.2/src/jquery.poshytip.min.js"></script>
<script type="text/javascript">

function rateCalculate(borrowSum){//利息计算
       param["paramMap.borrowSum"] = borrowSum;
       param["paramMap.yearRate"] = ${investDetailMap.annualRate};//利率
       param["paramMap.borrowTime"] =${investDetailMap.deadline};//期限
       param["paramMap.Way"] = ${investDetailMap.paymentMode};//方式
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

function validateAmount(){
    var  minsum = parseFloat('${investDetailMap.minTenderedSum}'.replace(/[^\d\.-]/g, ""));
    var jebs = parseInt('${jebs}');
    var investAmount = parseFloat('${investDetailMap.investNum}'.replace(/[^\d\.-]/g, ""));
    var usableSum = parseFloat('${usableSum}'.replace(/[^\d\.-]/g, ""));
    var amount = $("#borrowAmount").val();
      if (amount>=minsum) {
          var scale = parseInt(amount/jebs);
          if(scale!=(amount/jebs) && amount!=investAmount){
              show("投资金额只能是"+jebs+"的倍数(特殊情况除外\n如：投资金额等于剩余可投金额)");
              return false;
          }else{
              return true;
          }
      }else{
          show("投资金额不能小于最小投资金额");
          return false
       }
      
 }

var flag = true;
$(function(){
	//提示语
	$('.helplist').poshytip({
		className: 'tip-twitter',
		alignTo: 'target',
		alignX: 'inner-left',
		offsetX: -60,
		offsetY: 10
	});
	
	$("#searchDealPwd").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '40%',
		height		: '70%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
	
	$('.single_image3').fancybox();
	
	$("#borrowAmount").keyup(function(event){
		 if(/\D/.test(this.value)){
             this.value=this.value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')
             return
          }

            
         var investAmount = '${investDetailMap.investNum}'
         var usableSum = '${userMap2.usableSum}';
         var jebs = parseInt('${jebs}');
         
         if(this.value>parseFloat(usableSum.replace(/[^\d\.-]/g, ""))){
             show("投资金额已超过你的可用余额");
             var scale = parseInt(usableSum.replace(/[^\d\.-]/g, ""))/jebs;
             this.value=scale*jebs;
         }
         
         
         if(this.value>parseFloat(investAmount.replace(/[^\d\.-]/g, ""))){
             show("投资金额已超过可投金额");
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
    var borrowAmount = $("#borrowAmount").val();
	rateCalculate(borrowAmount)
	
     //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
	  $('#invest').click(function(){
		  
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $("#borrowAmount").val();;
	     param['paramMap.dealPWD'] = $('#dealPWD').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     param['paramMap.result'] = $('#result').val();//认购份数
	     param['paramMap.smallestFlowUnit'] = $('#smallestFlowUnit').val();//每份认购金额
	     param['paramMap.subscribes'] = $('#subscribes').val();//认购模式是否开启
	     
	     if(flag){
           flag = false;
           $('#invest').val('投标中...');
	       $.dispatchPost('financeInvest.mht',param,function(data){
	       	  $('#invest').val('立即投资');
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         show(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.mht?id='+id;
		          flag = false;
		          return false;
		         }
		         show(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
	 $('#btnsave').click(function(){
		 if(!validateAmount()){
             return;
         }
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $('#amount').val();
	     param['paramMap.dealPWD'] = $('#dealPWD').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     
	     if(param['paramMap.amount']==null || param['paramMap.amount']==''){
             show("请输入投资金额");
             return ;
         }else{
             if(param['paramMap.amount']<=0){
                 show("请输入投资金额");
                 return ;
             }
         }
         
         if(param['paramMap.dealPWD']==null || param['paramMap.dealPWD']==''){
             show("请输入交易密码");
             return ;
         }
	     if(flag){
           flag = false;
           //var l = Ladda.create(this);
           //l.start();
           $('#btnsave>span').html('投标中...');
	       $.dispatchPost('financeInvest.mht',param,function(data){
	    	   //l.stop();
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.mht?id='+id;
		          flag = false;
		          return false;
		         }
		         if(data.updatePwd){
                     show("为了您的账户安全，交易密码不能与登录密码相同，请立即修改交易密码。");
                     window.location.href = 'securityCent.mht?isOn=2';
                }else{
                    show(callBack);
                    flag = true;
                }
		      }
		      $('#btnsave>span').html('确定');
		    });
		 }
	 });
});	

	function tz(){    
		window.location.href='updatePasswordAndDealpwd.mht';
	}

</script>
    </body>
</html>
