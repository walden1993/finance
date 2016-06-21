<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-招标中</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
        <link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
        <script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
        <script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									借款审核招标中
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="white12">
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${borrowMTenderInDetail.username}
								</td>
								<td class="f66 leftp200">
									用户类型：${borrowMTenderInDetail.userType}
								</td>
							</tr>
							<tr>
								<td class="blue12 left" align="center">
									标的标题：${borrowMTenderInDetail.borrowTitle}
								</td>
								<td class="f66 leftp200">
									企业全称：${borrowMTenderInDetail.orgName}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${borrowMTenderInDetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${borrowMTenderInDetail.annualRate}%
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${borrowMTenderInDetail.deadline}
									<s:if test="%{borrowMTenderInDetail.isDayThe ==1}">个月</s:if><s:else>天</s:else>
								</td>
								<td class="f66 leftp200">
								           借款用途：${borrowMTenderInDetail.purpose}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									还款方式：
									<s:if test="%{borrowMTenderInDetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
									<s:elseif test="%{borrowMTenderInDetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
									<s:elseif test="%{borrowMTenderInDetail.paymentMode ==3}">
									 秒还
									</s:elseif>　
									<s:elseif test="%{borrowMTenderInDetail.paymentMode ==4}">
									  一次性还款
									</s:elseif>　
								</td>
								<td class="f66 leftp200">
								标的类型：
							<s:if test="%{borrowMTenderInDetail.borrowWay==1}">
							净值借款
							</s:if>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==2}">
							秒还借款
							</s:elseif>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==3}">
							信用借款
							</s:elseif>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==6}">
							流转标借款
							</s:elseif>
							<s:elseif test="%{borrowMTenderInDetail.borrowWay==7}">
							债权转让借款
							</s:elseif>
								</td>
							</tr>
							<s:if test="#request.subscribes==1">
									<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMTenderInDetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMTenderInDetail.circulationNumber}份
								</td>
								</tr>
							</s:if>
							<s:elseif test="%{borrowMTenderInDetail.borrowShow==2}">
								<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMTenderInDetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMTenderInDetail.circulationNumber}份
								</td>
								</tr>
							</s:elseif>
							<s:else>
									<tr>
									<td class="blue12 left">
										最低投标金额：${borrowMTenderInDetail.minTenderedSum}元
									</td>
									<td class="f66 leftp200">
	                                                                                                最高投标金额：
	                                    <s:if test="%{borrowMTenderInDetail.maxTenderedSum=='-1.00'}">
								                           没有限制
								        </s:if>
								        <s:else>
								           ${borrowMTenderInDetail.maxTenderedSum}元                
								        </s:else>                                                            
									</td>
								</tr>
							</s:else>
							<tr>
								<td class="blue12 left">
									投标奖励:  
									<s:if test="%{borrowMTenderInDetail.excitationType ==2}">
									  按 固定金额,${borrowMTenderInDetail.excitationSum}元
									</s:if>
									<s:elseif test="%{borrowMTenderInDetail.excitationType ==3}">
									 按借款金额比例,${borrowMTenderInDetail.excitationSum}%
									</s:elseif>
									<s:else>
									   无
									</s:else>
								</td>
								<td align="left" class="f66">
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									投标密码：
									<s:if test="%{borrowMTenderInDetail.hasPWD ==1}">
									   有
									</s:if>
									<s:else>
									   无
									</s:else>
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
							<s:if test="%{borrowMTenderInDetail.borrowShow==1}">
								<tr>
								<td colspan="2" class="blue12 left">
									借款详情：${borrowMTenderInDetail.detail}
								</td>
								</tr>
							</s:if>
							<s:else>
									<tr>
								<td colspan="2" class="blue12 left">
									借款方商业概述：${borrowMTenderInDetail.businessDetail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资产情况：${borrowMTenderInDetail.assets}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资金用途：${borrowMTenderInDetail.moneyPurposes}
								</td>
							</tr>
							</s:else>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowMTenderInDetail.publishTime}/IP: ${borrowMTenderInDetail.publishIP}
								</td>
							</tr>
							
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.mht?userId=${borrowMTenderInDetail.userId}&userType=${borrowMTenderInDetail.userType}" target="_blank">申请人认证详情查看</a>
								</td>
							</tr>
                            <tr>
								<td colspan="2" class="blue12 left">
									审核状态：
									<s:radio name="" list="#{2:'审核通过',1:'审核不通过'}" disabled="true" value="2"></s:radio>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									站内信通知：
									<br/><s:textarea cssStyle="margin-left:80px;" disabled="true" cols="30" rows="5" value="%{borrowMTenderInDetail.mailContent}"></s:textarea>
								</td>
								<td align="center" class="f66">
								</td>
							</tr>
							</table>
							<div id="content">
							<table>
							<s:hidden value="%{#request.borrowMTenderInDetail.auditOpinion}"  id="auditOpinion_old"></s:hidden>
							<tr>
							    <td colspan="2" class="blue12 left">
									风控意见：<span class="require-field">*&nbsp;</span>
									<br/><textarea  style="width: 70%"  name="paramMap.auditOpinion"  cols="30" rows="10"></textarea>
								</td>
							</tr>
							<s:if test="%{borrowMTenderInDetail.firstAuditTime != null && borrowMTenderInDetail.firstAuditTime != ''}">
							<tr>
								<td colspan="2" class="blue12 left">
									初审时间：${borrowMTenderInDetail.firstAuditTime}
								</td>
							</tr>
							</s:if>
							<s:if test="%{borrowMTenderInDetail.displayTime != null && borrowMTenderInDetail.displayTime != ''}">
							<tr>
								<td colspan="2" class="blue12 left">
									发布时间：
									<input id="displayTime" size=25 class="Wdate" value="${borrowMTenderInDetail.displayTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								</td>
							</tr>
							</s:if>
						<s:if test="%{borrowMTenderInDetail.borrowShow!=2}">
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_update"
										style="background-image: url('../images/admin/btn_update.jpg'); width: 70px; border: 0; height: 26px"></button>
								   &nbsp;&nbsp;
								    <button id="btn_reback" 
										style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button>
								</td>
							</tr>
							</s:if>
							
						</table>
							</div>
						<s:hidden name="id" value="%{borrowMTenderInDetail.id}"></s:hidden>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>

<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
var editor_content;
$(function(){
    KindEditor.ready(function(K) {
        editor_content = K.create('textarea[name="paramMap.auditOpinion"]', {
            cssPath : '../kindeditor/plugins/code/prettify.css',
            uploadJson : 'kindEditorUpload.mht',
            fileManagerJson : 'kindEditorManager.mht',
            allowFileManager : true
        });
        editor_content.html($("#auditOpinion_old").val());
    });
})

var falg = false;
	$(function(){
	  //提交表单
   	  $('#btn_update').click(function(){
   	   	  if (falg) return ;
   		 $('#btn_update').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
      	 $('#btn_update').attr('disabled',true);
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.auditOpinion'] = editor_content.html();
	     param['paramMap.displayTime'] = $('#displayTime').val();
	     if( param['paramMap.auditOpinion']==null ||  param['paramMap.auditOpinion']==''){
             alert("请填写风控意见")
            $('#btn_update').attr('style',"background-image: url('../images/admin/btn_update.jpg'); width: 70px; border: 0; height: 26px");
               $('#btn_update').attr('disabled',false);
             return;
         }
	     $.dispatchPost('updateBorrowTenderIn.mht',param,function(data){
		   var callBack = data.msg;
		   if(callBack == undefined || callBack == ''){
		       $('#content').html(data);
		        falg = false;
		       
		   }else{
		       if(callBack == 1){
		          alert("操作成功!");
		          falg = true;
		          window.location.href="borrowTenderIn.mht";
		          return false;
		       }
		       alert(callBack);
		       falg = false;
		       $('#btn_reback').attr('style',"background-image: url('../images/admin/btn_update.jpg'); width: 70px; border: 0; height: 26px");
		       $('#btn_update').attr('disabled',false);
		   }
		 });
	   });
	   $('#btn_reback').click(function(){
			  if (falg) return ;
	     var r=confirm("撤消操作,是否继续?");
	     if(r == true){
	    	$('#btn_reback').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
	      	$('#btn_reback').attr('disabled',true);
	        param['paramMap.id'] = $('#id').val();
	        $.dispatchPost('reBackBorrowTenderIn.mht',param,function(data){
		         var callBack = data.msg;
		         if(callBack == 1){
		        	 falg = true;
		            alert("操作成功!");
		            window.location.href="borrowTenderIn.mht";
		            return false;
		         } falg = false;
		         
		         alert(callBack);
		         $('#btn_reback').attr('style',"background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px");
		         $('#btn_reback').attr('disabled',false);
		    });
	     }
	   });
	});
</script>
	</body>	
</html>