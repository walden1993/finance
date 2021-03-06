<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>
	<body>
		<input type="hidden" id="idStr" value="${idStr}" />
		<div class="ifbox1" style="margin-top: 20px;">
			<div class="til"
				style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
				<ul>
					<li class="on">
						该借款信息
					</li>
				</ul>
				<div class="til_bot">
				</div>
			</div>
			<div class="boxmain">
				<div class="pic-info">
					<div class="tx">
						<shove:img src="${borrowDetailMap.imgPath }"
							defaulImg="images/default-img.jpg" width="180" height="180"></shove:img>
					</div>
					<div class="guanzhu">

					</div>
					<p>
						<a id="focusonBorrow" href="javascript:void(0);">关注此借款</a>
					</p>
				</div>
				<div class="xqbox">
					<h3>
						<s:property value="#request.borrowDetailMap.borrowTitle"
							default="---" />
					</h3>
					<div class="xqboxmain">
						<div class="xqtop">
							<div class="money">
								<p>
									借款金额：
									<strong>￥<s:property
											value="#request.borrowDetailMap.borrowAmount" default="---" />
									</strong>
								</p>
								<p>
									借款年利率：
									<span style="margin-right: 15px;"><s:property
											value="#request.borrowDetailMap.annualRate" default="---" />%(月利率：<s:property
											value="#request.borrowDetailMap.monthRate" default="---" />%)</span>
									借款期限：
									<span><s:property
											value="#request.borrowDetailMap.deadline" default="---" />个月</span>
								</p>
							</div>
							<div class="tbbtn">
								<s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
									<img src="images/neiye2_15.jpg" width="97" height="31" />&nbsp;
        </s:if>

								<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
									<img src="images/neiye2_16.jpg" width="97" height="31" />&nbsp;
        </s:elseif>
								<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
									<img src="images/neiye1_636.jpg" width="97" height="31" />&nbsp;
        </s:elseif>
								<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
									<img src="images/neiye1_637.jpg" width="97" height="31" />&nbsp;
        </s:elseif>
								<s:else>
									<img src="images/neiye2_18.jpg" width="97" height="31" />&nbsp;
        </s:else>
							</div>
						</div>
						<div class="xqbottom">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<div>
											<div style="float: left;">
												投标进度：
											</div>
											<div
												style="float: left; vertical-align: bottom; margin-right: 5px; margin-top: 8px; padding: 3px; background-image: url(images/index9_57.jpg); width: 100px; height: 10px;">
												<img src="images/index9_56.jpg"
													width="<s:property value="#request.borrowDetailMap.schedules" default="0"/>"
													height="10" style="display: block;" />
											</div>
											<div style="float: left;">
												<span><s:property
														value="#request.borrowDetailMap.schedules" default="0" />%</span>
											</div>
										</div>
									</td>
									<td>
										还差：
										<span>￥<s:property
												value="#request.borrowDetailMap.investAmount" default="0" />
										</span>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										还款方式：
										<s:if test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:if>
										<s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
										<s:elseif test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
									    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
									</td>
									<td>
										投标奖励：
										<s:if test="%{#request.borrowDetailMap.excitationType == 1}">无</s:if>
										<s:elseif
											test="%{#request.borrowDetailMap.excitationType ==2}">按固定金额</s:elseif>
										<s:elseif
											test="%{#request.borrowDetailMap.excitationType ==3}">按借款金额比例</s:elseif>
									</td>
									<td>
										交易类型：
										<s:if test="%{#request.borrowDetailMap.tradeType == 1}">线上交易</s:if>
										<s:elseif test="%{##request.borrowDetailMap.tradeType ==2}">线下交易</s:elseif>
									</td>
								</tr>
								<tr>
									<td>
										最小投标金额： ￥
										<s:property value="#request.borrowDetailMap.minTenderedSum"
											default="0" />
										元
									</td>
									<td>
										最大投标金额：
										<s:if test="%{#request.borrowDetailMap.maxTenderedSum==-1}">
							没有限制
				     </s:if>
										<s:else>
					    ￥<s:property value="#request.borrowDetailMap.maxTenderedSum"
												default="0" />元                
					</s:else>
									</td>									
								</tr>							
								<tr>
									<td>总投标数：<s:property value="#request.borrowDetailMap.investNum"
											default="0" /></td>									
									<td>浏览量：<s:property value="#request.borrowDetailMap.visitors"
											default="---" /></td>
									<s:if test="%{#request.borrowDetailMap.borrowStatus == 4}">
									</s:if>		
									<s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
									</s:elseif>
									<s:else>
									<td>剩余时间：<span id="remainTimeOne"></span></td>	
									</s:else>
								</tr>
								<tr>
									<td colspan="3"><span>${earnAmount}</span></td>
								</tr>
							</table>

						</div>
					</div>
				</div>
				<div class="reninfo">
					<div class="rinfomain">
						<div class="tx">
							<shove:img src="${borrowUserMap.personalHead}"
								defaulImg="images/default-img.jpg" width="62" height="62"></shove:img>
						</div>
						<div class="jfico">
							<img src="images/ico_r_${borrowUserMap.rating}.gif" />
						</div>
						<div class="">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="debt_table">
								<tr>
									<td  align="right">
										用&nbsp;户&nbsp;名：
									</td>
									<td>
										${borrowUserMap.username}
										<s:if test="#request.borrowUserMap.vipStatus ==2"></s:if>
									</td>
								</tr>
								<tr>
									<td  align="right">
										企业地址：
									</td>
									<td> <span class="user_address">${borrowUserMap.address}</span></td>
								</tr>
								<tr>
									<td width="70px" align="right">
										信用指数：
										
									</td>
									<td>
										<img src="images/ico_${borrowUserMap.credit}.jpg"
											title="${borrowUserMap.creditrating}分" width="33" height="22" />
									</td>
								</tr>
								<tr>
									<td align="right">
										注册时间：
									</td>
									<td>${borrowUserMap.createTime}</td>
								</tr>
								<tr>
									<td align="right">
										最后登录：
									</td>
									<td>${borrowUserMap.lastDate}</td>
								</tr>
								<tr>
									<td align="center" colspan="2">
										<img src="images/neiye2_22.jpg" width="16" height="16" />
										<a id="focusonUser" href="javascript:void(0);">关注此人</a>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>

			</div>
			<div class="ifbox2">
				<div class="til"
					style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
					<ul>
						<li class="on">
							相关信息
						</li>
					</ul>
					<div class="til_bot">
					</div>
				</div>
				<div class="boxmain">
					<p class="xydj">
						三好资本信用等级：
						<img src="images/ico_${borrowUserMap.credit}.jpg"
							title="${borrowUserMap.creditrating}分" width="33" height="22" />
						<span>三好资本信用额度： ${borrowUserMap.creditLimit}</span>
					</p>
					<div class="tips">
						以下基本信息资料，经用户同意披露。其中
						<span class="fred">红色</span>字体的信息，为通过三好资本审核的项目。
						<br />
						<strong>审核意见：</strong>${borrowDetailMap.auditOpinion}
					</div>
					<div class="infotab">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th colspan="6" align="left">
									基本信息
								</th>
							</tr>
							<tr>
								<td>
									行业类别：
									<s:if test="#request.borrowUserMap.auditStatus == 3">
										<strong> <s:if
												test="#request.borrowUserMap.industory == 0">IT|通信|电子|互联网</s:if>
											<s:elseif test="#request.borrowUserMap.industory == 1">金融业</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 2">房地产|建筑业</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 3">商业服务</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 4">贸易|批发|零售|租赁业</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 5">文体教育|工艺美术</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 6">生产|加工|制造</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 7">交通|运输|物流|仓储</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 8">服务业</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 9">文化|传媒|娱乐|体育</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 10">能源|矿产|环保</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 11">政府|非盈利机构</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 12">农|林|牧|渔|</s:elseif>
											<s:elseif test="#request.borrowUserMap.industory == 13">其他</s:elseif>
											<s:else>无</s:else> </strong>
									</s:if>
									<s:else>
										<s:if test="#request.borrowUserMap.industory == 0">IT|通信|电子|互联网</s:if>
										<s:elseif test="#request.borrowUserMap.industory == 1">金融业</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 2">房地产|建筑业</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 3">商业服务</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 4">贸易|批发|零售|租赁业</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 5">文体教育|工艺美术</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 6">生产|加工|制造</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 7">交通|运输|物流|仓储</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 8">服务业</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 9">文化|传媒|娱乐|体育</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 10">能源|矿产|环保</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 11">政府|非盈利机构</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 12">农|林|牧|渔|</s:elseif>
										<s:elseif test="#request.borrowUserMap.industory == 13">其他</s:elseif>
										<s:else>无</s:else>
									</s:else>
								</td>
							</tr>
							<tr>
								<td>
									企业性质：
									<s:if test="#request.borrowUserMap.auditStatus == 3">
										<strong> <s:if
												test="#request.borrowUserMap.companyType == 0">国企</s:if> <s:elseif
												test="#request.borrowUserMap.companyType == 1">民营 </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 2">合资 </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 3">外商独资 </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 4">股份制企业</s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 5">上市公司 </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 6">代表处</s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 7">国家机关</s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 8">事业单位</s:elseif>
											<s:elseif test="#request.borrowUserMap.companyType == 9">其它 </s:elseif>
											<s:else>无</s:else> </strong>
									</s:if>
									<s:else>
										<s:if test="#request.borrowUserMap.companyType == 0">国企</s:if>
										<s:elseif test="#request.borrowUserMap.companyType == 1">民营 </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 2">合资 </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 3">外商独资 </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 4">股份制企业</s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 5">上市公司 </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 6">代表处</s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 7">国家机关</s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 8">事业单位</s:elseif>
										<s:elseif test="#request.borrowUserMap.companyType == 9">其它 </s:elseif>
										<s:else>无</s:else>
									</s:else>
								</td>
							</tr>
							<tr>
								<td>
									企业规模：
									<s:if test="#request.borrowUserMap.auditStatus == 3">
										<strong> 
											<s:if test="#request.borrowUserMap.companyScale == 0">20人以下 </s:if>
											<s:elseif test="#request.borrowUserMap.companyScale == 1">20-99人  </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyScale == 2">100-499人  </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyScale == 3">500-999人  </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyScale == 4">1000-9999人 </s:elseif>
											<s:elseif test="#request.borrowUserMap.companyScale == 5">10000人以上</s:elseif>
											<s:else>无</s:else>
										</strong>
									</s:if>
									<s:else>
										<s:if test="#request.borrowUserMap.companyScale == 0">20人以下 </s:if>
										<s:elseif test="#request.borrowUserMap.companyScale == 1">20-99人  </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyScale == 2">100-499人  </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyScale == 3">500-999人  </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyScale == 4">1000-9999人 </s:elseif>
										<s:elseif test="#request.borrowUserMap.companyScale  == 5">10000人以上</s:elseif>
										<s:else>无</s:else>
									</s:else>
								</td>
							</tr>
							<tr>
								<td>
									成立时间：
									<s:if test="#request.borrowUserMap.auditStatus == 3">
										<strong>${borrowUserMap.foundDate }</strong>
									</s:if>
									<s:else>${borrowUserMap.foundDate}</s:else>
								</td>
							</tr>
						</table>
					</div>
					<div class="infotab">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th colspan="5" align="left">
									三好资本融资记录
								</th>
							</tr>
							<tr>
								<td>
									发布融资标的：
									<s:if test="#request.borrowRecordMap.publishBorrow !=''">${borrowRecordMap.publishBorrow}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									成功融资笔数：
									<s:if test="#request.borrowRecordMap.successBorrow !=''">${borrowRecordMap.successBorrow}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									还清笔数：
									<s:if test="#request.borrowRecordMap.repayBorrow !=''">${borrowRecordMap.repayBorrow}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									逾期次数：
									<s:if test="#request.borrowRecordMap.hasFICount !=''">${borrowRecordMap.hasFICount}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									严重逾期次数：
									<s:if test="#request.borrowRecordMap.badFICount !=''">${borrowRecordMap.badFICount}</s:if>
									<s:else>0</s:else>
								</td>
							</tr>
							<tr>
								<td>
									共借入：
									<s:if test="#request.borrowRecordMap.borrowAmount !=''">${borrowRecordMap.borrowAmount}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									待还金额：
									<s:if test="#request.borrowRecordMap.forRepayPI !=''">${borrowRecordMap.forRepayPI}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									逾期金额：
									<s:if test="#request.borrowRecordMap.hasI !=''">${borrowRecordMap.hasI}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									共借出：
									<s:if test="#request.borrowRecordMap.investAmount !=''">${borrowRecordMap.investAmount}</s:if>
									<s:else>0</s:else>
								</td>
								<td>
									待收金额：
									<s:if test="#request.borrowRecordMap.forPI !=''">${borrowRecordMap.forPI}</s:if>
									<s:else>0</s:else>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="ifbox2">
				<div class="til"
					style="background-image: url(images/neiye2_32242.jpg); background-repeat: repeat-x; border: none;">
					<ul class="shtab">
						<li class="on" id="audit">
							审核记录
						</li>
						<li id="repay">
							还款详情
						</li>
						<s:if test="#request.colSize != null">
							<li id="collection">
								催收记录
							</li>
						</s:if>
					</ul>
					<div class="til_bot">
					</div>
				</div>
				<div class="boxmain">
					<div class="box">
						<div class="tips">
							三好资本将以客观、公正的原则，最大程度地核实融资者信息的真实性，但不保证审核信息100%真实。如果融资者长期逾期，其提供的信息将被公布。
						</div>
						<div id="contentList" class="tytab">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th align="center">
										审核项目
									</th>
									<th align="center">
										状态
									</th>
									<th align="center">
										通过时间
									</th>
									<th align="center">
										操作
									</th>
								</tr>
								<s:if test="%{#request.list !=null && #request.list.size()>0}">
									<s:iterator value="#request.list" id="bean">
										<tr>
											<td align="center">
												${bean.name}
											</td>
											<td align="center">
												<s:if test="#bean.auditStatus == 1">
                               					       等待审核
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
											<td align="center" class="ck">
												<s:if test="#bean.visiable== 1 && #bean.auditStatus==3">
													<a
														href="javascript:showImg(${bean.materAuthTypeId},${bean.userId});">查看</a>
												</s:if>
												<s:else>---
         										</s:else>
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
				</div>
				<div class="ifbox2">
					<div class="til"
						style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
						<ul>
							<li class="on">
								标的详情
							</li>
						</ul>
						<div class="til_bot">
						</div>
					</div>
					<div class="boxmain">
						<p class="txt">
							<strong>我的借款描述：</strong>
							<br />
							<s:property value="#request.borrowDetailMap.borrowInfo"
								default="" />
						</p>
					</div>
				</div>
				<div id="msg" class="ifbox2">
					<img src="images/load.gif" class="load" alt="加载中..." />
				</div>
				<span id="span_record"></span>
			</div>

			<div id="remainSeconds" style="display: none;">
				${borrowDetailMap.remainTime}
			</div>
			
			<script type="text/javascript" src="script/nav-lc.js"></script>
			<script type="text/javascript" src="css/popom.js"></script>
			<script type="text/javascript" src="script/jquery.dispatch.js"></script>
			<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
			<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
			<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
			<script>
$(function(){
	var user_address = $(".user_address").html();
	var new_address = user_address.substring(0,3);
	$(".user_address").html(new_address);
	 $('#focusonBorrow').click(function(){
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.userName}';
	      if(username == uname){
	         alert("您不能关注自己发布的借款");
	         return false;
	      }
	     param['paramMap.id'] = '${borrowDetailMap.id}';
	     $.dispatchPost('focusonBorrow.mht',param,function(data){
		   var callBack = data.msg;
		   alert(callBack);
		 });
	   });
	   $('#focusonUser').click(function(){ 
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.userName}';
	      if(username == uname){
	         alert("您不能关注自己");
	         return false;
	      }
	     param['paramMap.id'] = '${borrowUserMap.userId}';
	     $.dispatchPost('focusonUser.mht',param,function(data){
	       var callBack = data.msg;
		   alert(callBack);
		 });
	   });
	 
	   
	   $('#audit').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#repay').removeClass('on');
	      $('#collection').removeClass('on');
	      param['paramMap.id']=id;
	      $.dispatchPost('financeAudit1.mht',param,function(data){
	        $('#contentList').html(data);	       
		  });
	   });
	   $('#repay').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#audit').removeClass('on');
	      $('#collection').removeClass('on');
	      param['paramMap.id']=id;
	      $.dispatchPost('financeRepay.mht',param,function(data){
	        $('#contentList').html(data);	       
		  });
	      
	   });
	   $('#collection').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#audit').removeClass('on');
	      $('#repay').removeClass('on');
	      param['paramMap.id']=id;
	      $.dispatchPost('financeCollection.mht',param,function(data){
	        $('#contentList').html(data);	       
		  });
	      
	   });
	   initListInfo(param);
	   initListAcutionInfo();
});

function initListAcutionInfo(){
 	var para = {};
 	para['paramMap.id']= $("#debtId").val();
 	if($("#auctionMode").val() == 1){
	 	 $.dispatchPost('queryAcutionRecordInfo.mht',para,function(data){
			   $('#span_record').html(data);
	    });
	}else{
 		$('#span_record').html("");
 	}
   
	
}
function initListInfo(param){
    param['paramMap.id']= $("#debtId").val();
    $.dispatchPost('debtMSGInit.mht',param,function(data){
		   $('#msg').html(data);
    });
}
function close(){
ClosePop();
}		     
</script>
			<script type="text/javascript"> 
 var SysSecond; 
 var InterValObj; 
  
 $(document).ready(function() { 
  SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
  InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
 }); 
 
 function showImg(typeId,userId){
		var session = '<%=session.getAttribute("user")%>';
		if (session == 'null'){
			window.location.href='login.mht';
			return ;
		}
        var url = "showImg.mht?typeId="+typeId+"&userId="+userId;
        $.jBox("iframe:"+url,{
            title: "查看认证图片(点击图片放大)",
            width: 600,
            height: 500,
            buttons: { '关闭': true }
        });
}
 //将时间减去1秒，计算天、时、分、秒 
 function SetRemainTime() { 
  if (SysSecond > 0) { 
   SysSecond = SysSecond - 1; 
   var second = Math.floor(SysSecond % 60);             // 计算秒     
   var minite = Math.floor((SysSecond / 60) % 60);      //计算分 
   var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时 
   var day = Math.floor((SysSecond / 3600) / 24);        //计算天 
   var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
   $("#remainTimeOne").html(times);
   $("#remainTimeTwo").html(times); 
  } else {//剩余时间小于或等于0的时候，就停止间隔函数 
   window.clearInterval(InterValObj); 
   //这里可以添加倒计时时间为0后需要执行的事件 
  } 
 } 
</script>
	</body>
</html>
