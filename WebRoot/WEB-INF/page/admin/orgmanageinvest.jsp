<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
        <script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				$('#search').click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});	
				
				$("#excel").click(function(){
				   window.location.href=encodeURI(encodeURI("exportuserInvestInfo.mht?i="+${userId}+"&paramMap.createtimeStart="+$("#createtimeStart").val()+"&paramMap.createtimeEnd="+$("#createtimeEnd").val()));
				
				});			
			});		
			
			function initListInfo(praData){
			param["i"] = ${userId };
			param["paramMap.createtimeStart"] = $("#createtimeStart").val();
			param["paramMap.createtimeEnd"] = $("#createtimeEnd").val();
		    $.post("queryUserManageInvest.mht",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
			$("#li_work").click(function() {
			window.location.href = 'queryUserMangework.mht?uid=${map.userId}';
		});
			
		</script>

	</head>
	<div id="dataInfo">
			<div style="padding: 15px 10px 0px 10px;">
				<!-- div>
					<table width="10%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100%" height="28" class="main_alll_h2">
								<a href="javascript:void(0)">企业基本信息</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
				</div> -->
				<div id="showcontent"
					style="width: auto; background-color: #FFF; padding: 10px;">
					<!-- 内容显示位置 -->

					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<!--第1个table  -->

							<td  style="width: 18%"  valign="top">
						  <div style="margin-top: 0px;">
						<table  border="1" cellspacing="1" cellpadding="3"  width="100%" style="min-width: 200px">
							<tr>
								<td width="100%" height="28" class="main_alll_h2">
									<a href="javascript:void(0)">企业基本信息</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</tr>
							<tr class="gvItem">
								<td  align="right" 
									class="blue12" width="70">
									用户名：
								</td>
								<td align="left" width="130">
								 ${ UserMsgmap.userName}
								</td>
							</tr>	
							<tr>
                                 <td  align="right" 
									class="blue12">
									企业全称：
								</td>
								<td align="left" class="f66" >	
										 ${UserMsgmap.orgName}	
								</td> 							
							
							</tr>
							<tr>
							       <td  align="right" 
									class="blue12">
									企业邮箱：
								</td>
								<td align="left" class="f66" >
								 ${ UserMsgmap.email}
								</td> 	
								
							</tr>
							
							<tr>
							       <td  align="right"
									class="blue12" >
									联系人手机：
								</td>
								<td align="left" class="f66">
									 ${ UserMsgmap.linkmanMobile}
								</td> 	
							
							</tr>
							
							
						</table>
						</div>
						 </td>
							<!--第二个table  -->
							<td id="secodetble" style="width: 70%" colspan="2">
								<table border="0" cellspacing="1" cellpadding="3" width="100%">
									<tr>
										<td colspan="2">
											<table>
												<tr>
													<td class="xxk_all_a" height="20" align="center"
														width="100px">
														<a style="cursor: pointer;" onclick="javascript:window.location.href='queryUserManageBaseInfoinner.mht?i=${id}&userType=${userType}';">企业基本信息</a>
													</td>
													<td>
														&nbsp;
													</td>
													<td  class="main_alll_h2" width="100px" height="20"
														align="center">
														<a style="cursor: pointer;" onclick="javascript:window.location.href='queryOrgManageInvest.mht?i=${UserMsgmap.id}';" >投资信息</a></td>
												</tr>
											</table>
										</td>
									</tr>
                                    <tr>
                                    <td>
                                    <div>
                                                                                                  投资时间：
									<input id="createtimeStart" class="Wdate" value="${paramMap.createtimeStart}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="createtimeEnd" class="Wdate" value="${paramMap.createtimeEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									&nbsp;&nbsp; <input id="search" type="button" value="查找" name="search" />&nbsp;&nbsp;
									<input id="excel" type="button" value="导出Excel" name="excel" />
                                    </div>
                                    </td>
                                    </tr>

									<tr>
										<td>
										<div >
											<table>
												<tbody>
													<tr class=gvHeader>
														<th style="width: 50px;">
															序号
														</th>
														<th style="width: 80px;">
															用户名
														</th>
														<th style="width: 70px;">
															企业全称
														</th>
														<th style="width: 100px;">
															联系人手机
														</th>
														<th style="width: 150px;">
															投资日期
														</th>
														<th style="width: 150px;">
															还款方式
														</th>
														<th style="width: 100px;">
															投资期限
														</th>
														<th style="width: 100px;">
															投资标题
														</th>
														<th style="width: 100px;">
															投资金额
														</th>
													</tr>
													<s:if test="pageBean.page==null || pageBean.page.size==0">
														<tr align="center" class="gvItem">
															<td colspan="6">
																暂无数据
															</td>
														</tr>
													</s:if>
													<s:else>
														<s:set name="counts" value="#request.pageNum" />
														<s:iterator value="pageBean.page" var="bean" status="s">
															<tr class="gvItem">
																<td>
																	<s:property value="#s.count+#counts" />
																</td>
																<td align="center">
																	${userName }
																</td>
																<td align="center">
																	${orgName }
																</td>
																<td align="center">
																	${linkmanMobile }
																</td>
																<td align="center">
																	<s:date name="#bean.investTime" format="yyyy-MM-dd HH:mm:ss" />
																</td>
																<td align="center">
																	<s:if test="paymentMode==1">
								                                                                                                           按月等额本息还款
								                                    </s:if>
																	<s:elseif test="paymentMode==2">
							                                        	按先息后本还款
							                                       	</s:elseif>
							                                       	<s:elseif test="paymentMode==3">
							                                        	秒还
							                                       	</s:elseif>
							                                       	<s:elseif test="paymentMode==4">
							                                        	一次性还款
							                                       	</s:elseif>
																	
																</td>
																<td align="center">
																	${deadline }
																	<s:if test="%{#bean.isDayThe ==1}">个月</s:if>
							                                        <s:else>天</s:else>
																</td>
																<td align="center">
																	${borrowTitle }
																</td>
																<td align="center"><fmt:formatNumber pattern="0.00">${investAmount }</fmt:formatNumber>
																</td>
															</tr>
														</s:iterator>
													</s:else>
												</tbody>
											</table>
											</div>
										</td>
									</tr>
									<tr><td>
											<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
									
									</td></tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		</html>
