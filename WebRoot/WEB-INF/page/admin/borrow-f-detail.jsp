<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-初审</title>
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
        <script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									借款审核初审
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
									用户名：${borrowMFADetail.username}
								</td>
								<td class="f66 leftp200">
									真实姓名：${borrowMFADetail.realName}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
								用户类型：
									<s:if test="%{borrowMFADetail.userType ==1}">个人</s:if>
									<s:elseif test="%{borrowMFADetail.userType ==2}">企业</s:elseif>
									<s:else>其它</s:else>
								</td>
								<td class="f66 leftp200">
									企业全称：${borrowMFADetail.orgName}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									标的标题：${borrowMFADetail.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${borrowMFADetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${borrowMFADetail.annualRate}%
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${borrowMFADetail.deadline}
									<s:if test="%{borrowMFADetail.isDayThe ==1}">个月</s:if><s:else>天</s:else>
								</td>
								<td class="f66 leftp200">
								           借款目的：${borrowMFADetail.purpose}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									还款方式：
									<s:if test="%{borrowMFADetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
									<s:elseif test="%{borrowMFADetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
									<s:elseif test="%{borrowMFADetail.paymentMode ==3}">
									 秒还
									</s:elseif>　
									<s:elseif test="%{borrowMFADetail.paymentMode ==4}">
									一次性还款
									</s:elseif>　
								</td>
								<td class="f66 leftp200">
								标的类型：
							<s:if test="%{borrowMFADetail.borrowWay==1}">
							净值借款
							</s:if>
							<s:elseif test="%{borrowMFADetail.borrowWay==2}">
							秒还借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==3}">
							信用借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==6}">
							流转标借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==7}">
							债权转让借款
							</s:elseif>
								</td>
							</tr>
							<s:if test="#request.subscribes==1">
									<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFADetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFADetail.circulationNumber}份
								</td>
								</tr>
							</s:if>
							<s:elseif test="%{borrowMFADetail.borrowShow==2}">
								<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFADetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFADetail.circulationNumber}份
								</td>
								</tr>
							</s:elseif>
							<s:else>
									<tr>
									<td class="blue12 left">
										最低投标金额：${borrowMFADetail.minTenderedSum}元
									</td>
									<td class="f66 leftp200">
	                                                                                                最高投标金额：
	                                    <s:if test="%{borrowMFADetail.maxTenderedSum==-1}">
								                           没有限制
								        </s:if>
								        <s:else>
								           ${borrowMFADetail.maxTenderedSum}元                
								        </s:else>                                                            
									</td>
								</tr>
							</s:else>
							<tr>
								<td class="blue12 left">
									投标奖励:  
									<s:if test="%{borrowMFADetail.excitationType ==2}">
									  按 固定金额,${borrowMFADetail.excitationSum}元
									</s:if>
									<s:elseif test="%{borrowMFADetail.excitationType ==3}">
									 按借款金额比例,${borrowMFADetail.excitationSum}%
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
									<s:if test="%{borrowMFADetail.hasPWD ==1}">
									   有
									</s:if>
									<s:else>
									   无
									</s:else>
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
							<s:if test="%{borrowMFADetail.borrowShow==1}">
								<tr>
								<td colspan="2" class="blue12 left">
									借款详情：
									<textarea   cssClass="textareash"  cssStyle="margin-left:80px;"  id="detail"   style="width: 670px; padding:5px;"  name="paramMap.detail" value="${borrowMFADetail.detail}" cols="30" rows="15">
                                    </textarea>
								</td>
								</tr>
							</s:if>
							<s:else>
									<tr>
								<td colspan="2" class="blue12 left">
									借款方商业概述：${borrowMFADetail.businessDetail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资产情况：${borrowMFADetail.assets}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资金用途：${borrowMFADetail.moneyPurposes}
								</td>
							</tr>
							</s:else>
							
							<tr>
							    <td colspan="2" class="blue12 left" >
									风控意见：<span class="require-field">*&nbsp; </span>
									<br/>
									<textarea   cssClass="textareash"  cssStyle="margin-left:80px;"    style="width: 670px; padding:5px;" id="auditOpinion" name="paramMap.auditOpinion" value="%{borrowMFADetail.auditOpinion}" cols="30" rows="15">
									</textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.mht?userId=${borrowMFADetail.userId}&userType=${borrowMFADetail.userType}" target="_blank" style="color: blue;"><strong>申请人认证详情查看</strong></a>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowMFADetail.publishTime}/IP: ${borrowMFADetail.publishIP}
								</td>
							</tr> 
							<s:if test="%{borrowMFADetail.firstAuditTime != null && borrowMFADetail.firstAuditTime != ''}">
							<tr>
								<td colspan="2" class="blue12 left">
									初审时间：${borrowMFADetail.firstAuditTime}
								</td>
							</tr>
							</s:if>
							<tr>
								<td colspan="2" class="blue12 left">
									发布时间：<input id="displayTime" name="paramMap.displayTime" type="text" class="Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								</td>
							</tr>
							
							<tr>
								<td colspan="2" class="blue12 left">
									是否预告：
									<s:radio name="paramMap.foreknow" list="#{1:'是',0:'否'}" value="%{0}"></s:radio>
								</td>
							</tr>
							
							<tr>
								<td colspan="2" class="blue12 left">
									奖励利率：
									<s:textfield name="paramMap.rewardRate" id="rewardRate" ></s:textfield>
								</td>
							</tr>
							
							</table>
							<div id="content">
							<table>
							<tr>
							<s:hidden value="%{#request.borrowMFADetail.detail}"  id="detail_old" ></s:hidden>
								<td colspan="2" class="blue12 left">
									审核状态：<span class="require-field">*</span>
									<s:radio name="paramMap.status" list="#{2:'审核通过',6:'审核不通过'}" value="%{borrowMFADetail.borrowStatus}"></s:radio>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									站内信通知：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.msg" value="%{borrowMFADetail.mailContent}"  cols="30" rows="5"></s:textarea>
								</td>
								<td align="center" class="f66">
								</td>
							</tr>
							
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" ></button>
								</td>
							</tr>
						</table>
							</div>
						<s:hidden name="id" value="%{borrowMFADetail.id}"></s:hidden>
						<s:hidden name="uid" value="%{borrowMFADetail.userId}"></s:hidden>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script>
var editor_content;
var detail_content;
$(function(){
	
	KindEditor.ready(function(K) {
	    editor_content = K.create('textarea[name="paramMap.auditOpinion"]', {
	        cssPath : '../kindeditor/plugins/code/prettify.css',
	        uploadJson : 'kindEditorUpload.mht',
	        fileManagerJson : 'kindEditorManager.mht',
	        filterMode : true
	    });
	});
	KindEditor.ready(function(K) {
		detail_content = K.create('textarea[name="paramMap.detail"]', {
            cssPath : '../kindeditor/plugins/code/prettify.css',
            uploadJson : 'kindEditorUpload.mht',
            fileManagerJson : 'kindEditorManager.mht',
            filterMode : true
        });
		detail_content.html($("#detail_old").val());
    });
})

function replaceSpace(aaa) {
            var ss = aaa;
            return ss.replace(/[ ]/g, "");
        }

var falg = false;
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
   	   $('#btn_save').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
   	  $('#btn_save').attr('disabled',true);
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.reciver'] = $('#uid').val();
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.msg'] = $('#paramMap_msg').val();
	     param['paramMap.auditOpinion'] = editor_content.html();
	     param['paramMap.detail'] = detail_content.html();
	     param['paramMap.displayTime'] = $('#displayTime').val();
	     param['paramMap.rewardRate'] = $('#rewardRate').val();
	     param['paramMap.foreknow'] = $("input[name='paramMap.foreknow']:checked").val();
	     if( param['paramMap.detail']==null ||  param['paramMap.detail']==''){
             alert("请填写借款详情")
             $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
             $('#btn_save').attr('disabled',false);
             return;
         }
	     if( param['paramMap.status']==null ||  param['paramMap.status']==''){
	    	 alert("请选择审核状态")
	    	 $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
             $('#btn_save').attr('disabled',false);
	    	 return;
	     }
	     if( param['paramMap.msg']==null ||  param['paramMap.msg']==''){
             alert("请填写站内通知")
             $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
             $('#btn_save').attr('disabled',false);
             return;
         }
	     if( param['paramMap.auditOpinion']==null ||  param['paramMap.auditOpinion']==''){
             alert("请填写风控意见")
             $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
             $('#btn_save').attr('disabled',false);
             return;
         }
	     if( param['paramMap.displayTime']==null ||  param['paramMap.displayTime']==''){
             alert("请填写发布时间")
             $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
             $('#btn_save').attr('disabled',false);
             return;
         }
	     if(falg) return ;
	     $.dispatchPost('updateBorrowF.mht',param,function(data){
		   var callBack = data.msg;
		   if(callBack == undefined || callBack == ''){
		       $('#content').html(data);
		       $("#btn_save").attr('disabled',false); 
		       falg = false;
		   }else{
		       if(callBack == 1){
		          alert("操作成功!");
		          window.location.href="borrowf.mht";
		          falg = true;
		          return false;
		       }
		       alert(callBack);
		       $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
		       falg = false;
		   $("#btn_save").attr('disabled',false); 
		   }
		 });
	 });
	});
</script>
	</body>	
</html>