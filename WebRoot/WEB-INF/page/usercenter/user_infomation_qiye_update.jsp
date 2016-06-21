<%@page import="com.sp2p.entity.User"%>
<%@page import="com.shove.util.DesSecurityUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<% 
	//加密/解密 工具类
	DesSecurityUtil des = new DesSecurityUtil();
%>
<html lang="zh_cn">
	<head>
		<!--=======================-->
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="common/date/calendar.css" />
		<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<script src="script/jquery-2.0.js" type="text/javascript"></script>
		<link rel="stylesheet" href="kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="kindeditor/plugins/code/prettify.js"></script>
<style>
<!--
.ke-dialog {
 position: absolute;
 top: 30%;
 left: 30%;
 margin: 0;
 padding: 0;
 }
-->
</style>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
			<div class="bigbox" style="border:none">
				
				<div class="sqdk" style="background: none;">
					<div class="l-nav">
						<ul>
							<li class="on"><a><span>step1 </span>基本资料</a></li>
							<li>
								<s:if test="#session.user.authStep>=3"> <a href='companyPictureDate.mht?from=${from}' ></s:if>
		  						<s:else><a></s:else>
								 <span>step2 </span>上传资料</a>
							 </li>
							<li>
								<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
								<s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.mht?t=${session.borrowWay}"><span>step3 </span> 发布融资</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.mht"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.mht?t=0"><span>step3 </span>发布融资</a>	
								  </s:else>
							</s:if>
							<s:else>
								<s:if test="#session.user.authStep>=5">
								  <s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.mht?t=${session.borrowWay}"><span>step3 </span>发布融资</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.mht"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.mht?t=0"><span>step3 </span>发布融资</a>	
								  </s:else>
								</s:if>	
								<s:else>
								  <s:if test="#session.stepMode ==1">
								       <a><span>step3 </span>发布融资</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a><span>step3 </span> 申请额度</a>
								  </s:elseif>
								</s:else>
							</s:else>						  
							</li>
						</ul>
					</div>
					<div class="r-main">
						<div class="til01">
							<ul>
								<li class="on" id="li_geren">
									基本信息
								</li>
								<li  id="li_vip">
									申请vip
								</li>
							</ul>
						</div>
						<div class="rmainbox">
						<!-- 基础资料div -->
							<div class="box01" id="jibenziliao">
								<p class="tips">
									<span class="fred">*</span> 为必填项，所有资料均会严格保密。
								</p>
								<div class="tab">
									<form id="BaseDataform">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="right">
												    <input type="hidden" id="companyId" value="${paramMap.id}"/>
													<span style="color: red; float: none;" class="formtips">*</span>企业全称：
												</td>
												<td>
												 <input type="text" readonly="readonly" class="formtips" name="paramMap.organizationName" value="${paramMap.organization_name}" id="organizationName"  />
											     <span style="color: red; float: none;" id="u_organizationName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业地址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.address" value="${paramMap.address}" id="address" />
													<span style="color: red; float: none;" id="u_address" class="formtips"></span>
												</td>
											</tr>
										<tr>
					                        <td align="right">
						                         <span style="color: red; float: none;" class="formtips">*</span>行业类别：
					                         </td>
					                        <td>
						                     
						                     <select name="paramMap.industory" id="industory"  >
											    <option value="-1"  >请选择</option>
                                                <option value="0"  <s:if test="#request.paramMap.industory==0">selected="selected"</s:if>>IT|通信|电子|互联网</option>
                                                <option value="1" <s:if test="#request.paramMap.industory==1">selected="selected"</s:if>>金融业</option>
                                                <option value="2" <s:if test="#request.paramMap.industory==2">selected="selected"</s:if>>房地产|建筑业</option>
                                                 <option value="3" <s:if test="#request.paramMap.industory==3">selected="selected"</s:if>>商业服务</option>
                                                <option value="4" <s:if test="#request.paramMap.industory==4">selected="selected"</s:if>>贸易|批发|零售|租赁业</option>
                                                <option value="5" <s:if test="#request.paramMap.industory==5">selected="selected"</s:if>>文体教育|工艺美术</option>
                                                <option value="6" <s:if test="#request.paramMap.industory==6">selected="selected"</s:if>>生产|加工|制造</option>
                                                <option value="7" <s:if test="#request.paramMap.industory==7">selected="selected"</s:if>>交通|运输|物流|仓储</option>
                                                <option value="8" <s:if test="#request.paramMap.industory==8">selected="selected"</s:if>>服务业</option>
                                                <option value="9" <s:if test="#request.paramMap.industory==9">selected="selected"</s:if>>文化|传媒|娱乐|体育</option>
                                                <option value="10" <s:if test="#request.paramMap.industory==10">selected="selected"</s:if>>能源|矿产|环保</option>
                                                <option value="11" <s:if test="#request.paramMap.industory==11">selected="selected"</s:if>>政府|非盈利机构</option>
                                                <option value="12" <s:if test="#request.paramMap.industory==12">selected="selected"</s:if>>农|林|牧|渔</option>
                                                <option value="13" <s:if test="#request.paramMap.industory==13">selected="selected"</s:if>>其他</option>
											 </select>
											 <span style="color: red; float: none;" id="u_industory" class="formtips"></span>
					                        </td>
					                      </tr>
					
							            <tr>
								           <td align="right">
						                         <span style="color: red; float: none;" class="formtips">*</span>企业性质：
					                        </td>
					                        <td>
						                     
						                     <select name="paramMap.companyType" id="companyType"  >
											    <option value="-1" >请选择</option>
                	                            <option value="0" <s:if test="#request.paramMap.company_type==0">selected="selected"</s:if>>民营</option>
                                                <option value="1" <s:if test="#request.paramMap.company_type==1">selected="selected"</s:if>>合资</option>
                                                <option value="2" <s:if test="#request.paramMap.company_type==2">selected="selected"</s:if>>外商独资</option>
                                                <option value="3" <s:if test="#request.paramMap.company_type==3">selected="selected"</s:if>>股份制企业</option>
                                                <option value="4" <s:if test="#request.paramMap.company_type==4">selected="selected"</s:if>>上市公司</option>
                                                <option value="5" <s:if test="#request.paramMap.company_type==5">selected="selected"</s:if>>代表处</option>
                                                <option value="6" <s:if test="#request.paramMap.company_type==6">selected="selected"</s:if>>国家机关</option>
                                                <option value="7" <s:if test="#request.paramMap.company_type==6">selected="selected"</s:if>>事业单位</option>
                                                <option value="8" <s:if test="#request.paramMap.company_type==7">selected="selected"</s:if>>其他</option>
											 </select>
											 <span style="color: red; float: none;" id="u_companyType" class="formtips"></span>
					                        </td>
							            </tr>
						
						
										 <tr>
										    <td align="right">
						                         <span style="color: red; float: none;" class="formtips">*</span>公司规模：
					                        </td>
					                        <td>
						                     
						                     <select name="paramMap.companyScale" id="companyScale"  >
											   <option value="-1" >请选择</option>
                	                           <option value="0" <s:if test="#request.paramMap.company_scale==0">selected="selected"</s:if>>20人以下</option>
                                               <option value="1" <s:if test="#request.paramMap.company_scale==1">selected="selected"</s:if>>20-99人</option>
                                               <option value="2" <s:if test="#request.paramMap.company_scale==2">selected="selected"</s:if>>100-499人</option>
                                               <option value="3" <s:if test="#request.paramMap.company_scale==3">selected="selected"</s:if>>500-999人</option>
                                               <option value="4" <s:if test="#request.paramMap.company_scale==4">selected="selected"</s:if>>1000-9999人</option>
                                               <option value="5" <s:if test="#request.paramMap.company_scale==5">selected="selected"</s:if>>10000人以上</option>
											 </select>
											 <span style="color: red; float: none;" id="u_companyScale" class="formtips"></span>
					                        </td>
										 </tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>成立时间：
												</td>
												<td>
													<input type="text" class="form-control w200 Wdate" name="paramMap.foundDate" value="${paramMap.found_date}" id="foundDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  />
													
													<span style="color: red; float: none;" id="u_foundDate" class="formtips"></span>
												</td>
											</tr>
											<tr>
                                              <td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>法人代表：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.legalPerson" value="${paramMap.legal_person}" id="legalPerson" />
													<span style="color: red; float: none;" id="u_legalPerson" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>工商注册号：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.regNum" value="${paramMap.reg_num}" id="regNum" />
													<span style="color: red; float: none;" id="u_regNum" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>组织机构代码：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.organizationCode" value="${paramMap.organization_code}" id="organizationCode" />
													<span style="color: red; float: none;" id="u_organizationCode" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													注册资本：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.registeredCapital" value="${paramMap.registered_capital}" id="registeredCapital" />
													<span style="color: red; float: none;" id="u_registeredCapital" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业开户行：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.bankName" value="${paramMap.bank_name}" id="bankName" />
													<span style="color: red; float: none;" id="u_bankName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业开户名称：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.accountName" value="${paramMap.account_name}" id="accountName" />
													<span style="color: red; float: none;" id="u_accountName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业对公银行账户：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.publicBankaccount" value="${paramMap.public_bank_account}" id="publicBankaccount" />
													<span style="color: red; float: none;" id="u_publicBankaccount" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业网址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.website" value="${paramMap.website}" id="website" />
													<span style="color: red; float: none;" id="u_website" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系电话：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.phone" value="${paramMap.phone}" id="phone" />&nbsp;格式：0755-29556666 或 010-83881140&nbsp;
													<span style="color: red; float: none;" id="u_phone" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>邮件地址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.email" value="${paramMap.email}" id="email_s" />
													<span style="color: red; float: none;" id="u_email" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系人姓名：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.linkmanName" value="${paramMap.linkman_name}" id="linkmanName" />
													<span style="color: red; float: none;" id="u_linkmanName" class="formtips"></span>
												</td>
											</tr>
											<s:if test="#request.paramMap.linkman_mobile==null || #request.paramMap.linkman_mobile=='' || #request.paramMap.linkman_mobile.isEmpty()">
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系人手机：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.linkmanMobile"  value="${paramMap.linkman_mobile}" id="linkmanMobile" />
													<span style="color: red; float: none;" id="u_linkmanMobile" class="formtips"></span>
												</td>
											</tr>
											<tr>
											    <td align="right">手机验证码：<span class="fred">*&nbsp;&nbsp;</span></td>
											    <td >
											    	<input style="width: 110px;" type="text" class="form-control w200" id="cellcode"  name="paramMap.cellcode" />&nbsp;&nbsp;
											    	<img id="codeImg" src="images/llz/sjYzm1.jpg" title="获取手机验证码"  style="cursor: pointer;" width="70" height="22" onclick="checkCode(this.id,'codeImg2')" />
											    	<span id="codeSpan" style="color: blue;float: none;" class="formtips"></span>
											    	<span style="color: red; float: none;" id="s_cellcode" class="formtips"></span>
											    </td>
											</tr>
											</s:if>
											<s:else>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系人手机：
												</td>
												
												<td>
													<input type="text" readonly="readonly" class="formtips" name="paramMap.linkmanMobile"  value="${paramMap.linkman_mobile}" id="linkmanMobile" />
													<span style="color: red; float: none;" id="u_linkmanMobile" class="formtips"></span>
												</td>
											</tr>
											</s:else>
											
											
											
											
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>公司简介：
												</td>
												<td>
												
													<textarea  rows="3" cols="27"  name="paramMap.companyDescription"  id="companyDescription" >${paramMap.company_description}</textarea>
													<span style="color: red; float: none;" id="u_companyDescription" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right" id="personalHead">
													<span style="color: red; float: none;" class="formtips">*</span>个人头像：
												</td>
												<td>
													<input type="button" id="btn_personalHead" class="scbtn"
														style="cursor: pointer;" value="点击上传" />
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td class="tx">
                                                  <img id="img" src="${paramMap.head_jpg}" width="62" height="62" name="paramMap.headJpg" />
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td class="tishi">
													请确保您填写的资料真实有效，所有资料将会严格保密。
													<br />
													一旦被发现所填资料有虚假，将会影响您在三好资本的信用，对以后借款造成影响。
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td style="padding-top: 20px;">
													<input type="button" value="保存并继续" class="button w140" id="jc_btn" />
												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							<!-- -- -->
							<!-- 是工作认证资料 -->
						   <!-- -- -->
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
			<script type="text/javascript" src="common/date/calendar.js"></script>

<script>
$(function(){
               $("#li_geren").click(function(){
		          var userType='${session.user.userType}';//用户是否是企业用户
		          var from = '${from}';
		          if(userType==2){
		          	window.location.href='queryCompayData.mht?from='+from;
		          }else
		            window.location.href='querBaseData.mht?from='+from;
		        });
		        
               $("#li_vip").click(function(){
					var from = '${from}';
					window.location.href='quervipData.mht?from='+from;
               });
		        
		 });



var isCode=0;//标志验证码是否匹配 ，0代表不用修改手机号码，1代表匹配，-1代表不匹配
//ajax验证手机验证码是否正确
function checkRegister(str){
	var param = {};
	param["paramMap.cellcode"] = $("#cellcode").val();
	$.post("ajaxCheckRegister.mht",param,function(data){
		if(data==33){
			document.getElementById("s_cellcode").style.color='green';
			$("#s_cellcode").html("√  验证码匹配");
			isCode=1;
		}else{
			document.getElementById("s_cellcode").style.color='red';
			$("#s_cellcode").html("验证码不匹配");
			isCode=-1;
		}
	});
}

//发送验证码间隔60秒
var i=180;
function timedCount(){
	i=i-1;
	document.getElementById("codeSpan").innerHTML=i+"秒后重新获取";
	if(i<=0){
		document.getElementById("codeImg").style.display='inline';
		document.getElementById("codeSpan").innerHTML="";
		i=180;
	}else{
		setTimeout("timedCount()",1000)
	}
}
//ajax发送验证码
function checkCode(x,y){
	return;
	var param = {};
	if(x=="codeImg"){
		param["paramMap.userName"] = '';
		param["paramMap.cellphone"] = $("#linkmanMobile").val();
		$.post("ajaxSendSMS1.mht",param,function(data){
			if(data=="-1"){
				alert("验证码发送失败,请重试");
			}else if(data=="-2"){
				$("#u_linkmanMobile").html("请输入您的手机号码");
			}else if(data=="1"){
				document.getElementById(x).style.display='none';
				timedCount();
			}
			
		});
	}
}
$(function(){
      $("#BaseDataform :input").blur(function(){
            //企业全称
             if($(this).attr("id")=="organizationName"){             
                if(this.value==""){
					$("#u_organizationName").attr("class", "formtips onError");
					$("#u_organizationName").html("请输入企业全称");
				}else if(this.value.length<2 || this.value.length>100){ 
					$("#u_organizationName").attr("class", "formtips onError");
					$("#u_organizationName").html("企业全称长度为2-100个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#u_organizationName").attr("class", "formtips onError");
					$("#u_organizationName").html("企业全称不能含有特殊字符"); 
				} else{
					$("#u_organizationName").html(""); 
					$("#u_organizationName").attr("class", "formtips");
				}
             } 
             //企业地址
             if($(this).attr("id")=="address"){
                if(this.value==""){
					$("#u_address").attr("class", "formtips onError");
					$("#u_address").html("请输入企业全称");
				}else{
					$("#u_address").html(""); 
					$("#u_address").attr("class", "formtips");
				}
             } 
              
              //行业类别
              if($(this).attr("id")=="industory"){
                 if($(this).val() ==-1){
                 	$("#u_industory").attr("class", "formtips onError");
                    $("#u_industory").html("请选择行业类别");
                 }else{
                 	$("#u_industory").attr("class", "formtips");
                  	$("#u_industory").html("");
                 }
              } 
              //企业性质
               if($(this).attr("id")=="companyType"){
                 if($(this).val() ==-1){
                 	$("#u_companyType").attr("class", "formtips onError");
                    $("#u_companyType").html("请选择企业性质");
                 }else{
                 	$("#u_companyType").attr("class", "formtips");
                  	$("#u_companyType").html("");
                 }
              } 
              //公司规模
               if($(this).attr("id")=="companyScale"){
                 if($(this).val() ==-1){
                 	$("#u_companyScale").attr("class", "formtips onError");
                    $("#u_companyScale").html("请选择公司规模");
                 }else{
                 	$("#u_companyScale").attr("class", "formtips");
                  	$("#u_companyScale").html("");
                 }
              } 
              //成立时间
             if($(this).attr("id")=="foundDate"){
                 if($(this).val() ==""){
                	 $("#u_companyScale").attr("class", "formtips onError");
                     $("#u_foundDate").html("请填写成立时间");
                 }else{
                 	$("#u_foundDate").attr("class", "formtips");
                  	$("#u_foundDate").html("");
                 }
             } 
             //法人代表
             if($(this).attr("id")=="legalPerson"){
                if(this.value==""){
					$("#u_legalPerson").attr("class", "formtips onError");
					$("#u_legalPerson").html("请输入法人代表");
				}else if(this.value.length<2 || this.value.length>8){ 
					$("#u_legalPerson").attr("class", "formtips onError");
					$("#u_legalPerson").html("法人代表长度为2-8个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#u_legalPerson").attr("class", "formtips onError");
					$("#u_legalPerson").html("法人代表不能含有特殊字符"); 
				} else{
					$("#u_legalPerson").html(""); 
					$("#u_legalPerson").attr("class", "formtips");
				}
             } 
             //工商注册号
             if($(this).attr("id")=="regNum"){
                 if($(this).val() ==""){
                	 $("#u_regNum").attr("class", "formtips onError");
                     $("#u_regNum").html("请填写工商注册号");
                 }else{
                	 $("#u_regNum").attr("class", "formtips");
                     $("#u_regNum").html("");
                 }
             } 
             //组织机构代码
             if($(this).attr("id")=="organizationCode"){
                 if($(this).val() ==""){
                 	 $("#u_organizationCode").attr("class", "formtips onError");
                     $("#u_organizationCode").html("请填写组织机构代码");
                 }else{
                	 $("#u_organizationCode").attr("class", "formtips");
                     $("#u_organizationCode").html("");
                 }
             } 
             /*
             //注册资本
             if($(this).attr("id")=="registeredCapital"){
                 if($(this).val() ==""){
                 	 $("#u_registeredCapital").attr("class", "formtips onError");
                     $("#u_registeredCapital").html("请填写注册资本");
                 }else{
                	 $("#u_registeredCapital").attr("class", "formtips");
                     $("#u_registeredCapital").html("");
                 }
             } 
             //企业开户行
              if($(this).attr("id")=="bankName"){
                 if($(this).val() ==""){
                 	 $("#u_bankName").attr("class", "formtips onError");
                     $("#u_bankName").html("请填写企业开户行");
                 }else{
                	 $("#u_bankName").attr("class", "formtips");
                     $("#u_bankName").html("");
                 }
             } 
             //企业开户名称
              if($(this).attr("id")=="accountName"){
                 if($(this).val() ==""){
                 	 $("#u_accountName").attr("class", "formtips onError");
                     $("#u_accountName").html("请填写企业开户行");
                 }else{
                	 $("#u_accountName").attr("class", "formtips");
                     $("#u_accountName").html("");
                 }
             } 
             //企业对公银行账户
             if($(this).attr("id")=="publicBankaccount"){
                 if($(this).val() ==""){
                 	 $("#u_publicBankaccount").attr("class", "formtips onError");
                     $("#u_publicBankaccount").html("请填写企业开户行");
                 }else{
                	 $("#u_publicBankaccount").attr("class", "formtips");
                     $("#u_publicBankaccount").html("");
                 }
             } 
             //企业网址
             if($(this).attr("id")=="website"){
                 if($(this).val() ==""){
                 	 $("#u_website").attr("class", "formtips onError");
                     $("#u_website").html("请填写企业开户行");
                 }else{
                	 $("#u_website").attr("class", "formtips");
                     $("#u_website").html("");
                 }
             } */
             //联系电话
             if($(this).attr("id")=="phone"){
				var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				if($(this).val() ==""){
						$("#u_phone").attr("class", "formtips onError");
						$("#u_phone").html("请填写联系电话");
				}else if(!mdd.test($(this).val())){
						$("#u_phone").attr("class", "formtips onError");
						$("#u_phone").html("联系电话格式错误");
				}else{
						$("#u_phone").html("");
				}
				
             } 
             //email
             if($(this).attr("id")=="email_s"){
                if(this.value==""){
					$("#u_email").attr("class", "formtips onError")  
					$("#u_emailu_email").html("请输入邮箱地址");
				}else if(!/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){
					$("#u_email").attr("class", "formtips onError") 
					$("#u_email").html("邮箱格式错误");
				}else{
					$("#u_email").html("");
					$("#u_email").attr("class", "formtips") 
				}
             } 
             //联系人姓名
             if($(this).attr("id")=="linkmanName"){
                 if(this.value==""){
					$("#u_linkmanName").attr("class", "formtips onError");
					$("#u_linkmanName").html("请输入联系人姓名");
				}else if(this.value.length<2 || this.value.length>8){ 
					$("#u_linkmanName").attr("class", "formtips onError");
					$("#u_linkmanName").html("联系人姓名长度为2-8个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#u_linkmanName").attr("class", "formtips onError");
					$("#u_linkmanName").html("联系人姓名不能含有特殊字符"); 
				} else{
					$("#u_linkmanName").html(""); 
					$("#u_linkmanName").attr("class", "formtips");
				}
             } 
             
             //联系人手机
              if($(this).attr("id")=="linkmanMobile"){
                 if(this.value==""){
					$("#u_linkmanMobile").attr("class", "formtips onError");
					$("#u_linkmanMobile").html("请输入联系人手机");
				}else if((!/^\d{11}$/.test(this.value))){
					$("#u_linkmanMobile").attr("class", "formtips onError");
					$("#u_linkmanMobile").html("联系人手机格式错误"); 
				}else{
					$("#u_linkmanMobile").attr("class", "formtips");
					$("#u_linkmanMobile").html(""); 
				}
             } 
              //手机验证码
             if($(this).attr("id")=="cellcode"){
             	if(this.value==""){
					$("#s_cellcode").attr("class", "formtips onError");
					$("#s_cellcode").html("请输入手机验证码");
				}else{
					$("#s_cellcode").attr("class", "formtips");
					checkRegister('cellphone');
					$("#u_linkmanMobile").html(""); 
				}
             }
             
             
             //公司简介
             if($(this).attr("id")=="companyDescription"){
                if(this.value==""){
					$("#u_companyDescription").attr("class", "formtips onError");
					$("#u_companyDescription").html("请输入公司简介");
				}else{
					$("#u_companyDescription").html(""); 
					$("#u_companyDescription").attr("class", "formtips");
				}
             } 
     });


	  $("#jc_btn").click(function(){
	     
	     if($("#organizationName").val()==""){
	        $("#u_organizationName").html("请填写企业全称");
            return false ;
	     }
	     
	     if($("#address").val()==""){
	     	alert("请填写企业地址");
	        $("#u_address").html("请填写企业地址");
            return false;
	      }
	      
	     if($("#industory").val()==-1){
	    	 alert("请选择行业类别");
	         $("#u_industory").html("请选择行业类别");
             return false ;
	     }
	     
	     if($("#companyType").val()==-1){
	    	 alert("请选择企业性质");
	        $("#u_companyType").html("请选择企业性质");
	  
	        return false;
	      }
	      
	      if($("#companyScale").val()==-1){ 
	    	 alert("请选择公司规模");
	        $("#u_companyScale").html("请选择公司规模");
	  
	        return false;
	      }
	      
	      
	      if($("#foundDate").val()==""){
	    	 alert("请填写成立时间");
	        $("#u_foundDate").html("请填写成立时间");
            return false;
	      }
	      
	       if($("#legalPerson").val()==""){
	    	 alert("请填写法人代表");
	        $("#u_legalPerson").html("请填写法人代表");
            return false;
	      }
	      
	      
	      if($("#regNum").val()==""){
	    	 alert("请填写工商注册号");
	        $("#u_regNum").html("请填写工商注册号");
            return false;
	      }
	      
	      if($("#organizationCode").val()==""){
	    	 alert("请填写组织机构代码");
	        $("#u_organizationCode").html("请填写组织机构代码");
            return false;
	      }
	      /*
	      if($("#registeredCapital").val()==""){
	    	 alert("请填写注册资本");
	        $("#u_registeredCapital").html("请填写注册资本");
            return false;
	      }
	      
	       if($("#bankName").val()==""){
	    	 alert("请填写企业开户行");
	        $("#u_bankName").html("请填写企业开户行");
            return false;
	      }
	      
	       if($("#accountName").val()==""){
	    	 alert("请填写企业开户名称");
	        $("#u_accountName").html("请填写企业开户名称");
            return false;
	      }
	      
	       if($("#publicBankaccount").val()==""){
	    	 alert("请填写企业对公银行账户");
	        $("#u_publicBankaccount").html("请填写企业对公银行账户");
            return false;
	      }
	      
	      
	       if($("#website").val()==""){
	    	 alert("请填写企业网址");
	        $("#u_website").html("请填写企业网址");
            return false;
	      }
	      */
	      	
	       if($("#phone").val()==""){
	    	 alert("请填写联系电话");
	        $("#u_phone").html("请填写联系电话");
            return false;
	      }
	      
	      if($("#email_s").val()==""){
	    	 alert("请填写邮件地址");
	        $("#u_email").html("请填写邮件地址");
            return false;
	      }
	      
	      
	      if($("#linkmanName").val()==""){
	    	 alert("请填写联系人姓名");
	        $("#u_linkmanName").html("请填写联系人姓名");
            return false;
	      }
	      
	      
	       if($("#linkmanMobile").val()==""){
	    	 alert("请填写联系人手机");
	        $("#u_linkmanMobile").html("请填写联系人手机");
            return false;
	      }
	      
	      if($("#companyDescription").val()==""){
	    	 alert("请填写公司简介");
	        $("#u_companyDescription").html("请填写公司简介");
            return false;
	      }
	   
	  // if($("#img").attr("src")==""){
	    //   alert("请上传你的个人头像");
	      // return false;
	   //}
	   var param = {};
	    param["paramMap.id"]=$("#companyId").val();
	    param["paramMap.organizationName"]=$("#organizationName").val();
	    param["paramMap.address"]=$("#address").val();
	    param["paramMap.industory"]=$("#industory").val();
	    param["paramMap.companyType"]=$("#companyType").val();
	    param["paramMap.companyScale"]=$("#companyScale").val();
	    param["paramMap.foundDate"]=$("#foundDate").val();
	    param["paramMap.legalPerson"]=$("#legalPerson").val();
	    param["paramMap.regNum"]=$("#regNum").val();
	    param["paramMap.organizationCode"]=$("#organizationCode").val();
	    param["paramMap.registeredCapital"]=$("#registeredCapital").val();
	    param["paramMap.bankName"]=$("#bankName").val();
	    param["paramMap.accountName"]=$("#accountName").val();
	    param["paramMap.publicBankaccount"]=$("#publicBankaccount").val();
	    
	    param["paramMap.website"]=$("#website").val();
	    param["paramMap.phone"]=$("#phone").val();
	    param["paramMap.email"]=$("#email_s").val();
	    param["paramMap.linkmanName"]=$("#linkmanName").val();
	    param["paramMap.linkmanMobile"]=$("#linkmanMobile").val();
	    param["paramMap.companyDescription"]=$("#companyDescription").val();
	    param["paramMap.headJpg"]=$("#img").attr("src");
	    

	    $.post("updateUserCompanyData.mht",param,function(data){
	       if(data.success){
	         alert("修改成功");
	         //如果不是从发布借款跳转过来，那么按照正常的步骤一步一步走
	         <s:if test="#session.user.vipStatus == 2">
	         	window.location.href='companyPictureDate.mht';
	         </s:if>
	         <s:else>
	         	window.location.href='quervipData.mht';
	         </s:else>
	       }else{
	          alert(data.msg);
	       }
	    });
	    
	});
	    
});
</script>
<script>

            //图片上传
			KindEditor.ready(function(K) {		
				//图片上传弹出框			---start		1,需要设置编码：kindeditor/lang/zh_CN.js
				
				var editor = K.editor({
					uploadJson : 'myFileUpload.mht?p=<%=des.encrypt("0,1,8,1,"+((User)session.getAttribute(IConstants.SESSION_USER)).getId()) %>',
					fileManagerJson : 'myKindEditorManager.mht?p=<%=des.encrypt("0,1,8,1,"+((User)session.getAttribute(IConstants.SESSION_USER)).getId()) %>',
					imageSizeLimit : "2MB",
					allowFileManager : true
				});
		
		        //商家图片上传
				K('#btn_personalHead').click(function() {
					editor.loadPlugin('image', function() {
						editor.plugin.imageDialog({
							showRemote : false,
							imageUrl : K('#imgPath').val(),
							clickFn : function(url, title, width, height, border, align) {	
							   
							    $("#imgPath").val(url);							
								K("#img").attr("src",url);
								editor.hideDialog();
							}
						});
					});
				});
			});
		
		</script>
		<script type="text/javascript">
		  $(function(){
		     //样式选中
           dqzt(2);
           var sd=parseInt($(".l-nav").css("height"));
           var sdf=parseInt($(".r-main").css("height"));
	       $(".l-nav").css("height",sd>sdf?sd:sdf-15);

		  });
	   </script>
	</body>
</html>
