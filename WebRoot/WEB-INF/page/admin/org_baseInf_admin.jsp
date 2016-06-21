<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css" />
		<link rel="stylesheet" type="text/css" href="../css/admin/admin_css.css" />
		<link rel="stylesheet" type="text/css" href="../css/css.css" />
		<script src="../script/jquery-2.0.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						   <td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">基本资料审核</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
						<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
				<hr/>						
						</tr>
					</table>
				</div>
		
		
		<!-- 引用头部公共部分 -->
		<!--=======================-->
		<div class="nymain" style="margin-top: 0px;">
			<div class="bigbox">
				<div class="sqdk" style="background: none; padding:0px">
					<div class="r-main" style="margin-left:100px">
						<div class="til01">
							<ul>
								<li class="on" id="li_geren">
									企业详细信息
								</li>
							</ul>
						</div>
						<div class="rmainbox">
						<!-- 基础资料div -->
							<div class="box01" id="jibenziliao">
								<div class="tab">
									<form id="BaseDataform">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="right">
													企业全称：<span style="color: red; float: none;" 
														class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.realName"
														id="orgName" value="${map.orgName}" />
															<span style="color: red; float: none;" id="u_orgName"
																class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业地址：<span style="color: red; float: none;" 
														class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.address"
														id="address" value="${map.address }" />
													<span style="color: red; float: none;" id="u_address"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
					                        	<td align="right">
						                         	行业类别：<span style="color: red; float: none;" class="formtips">*</span>
					                         	</td>
					                        <td>
						                     <select name="paramMap.industory" id="industory"  >
											    <option value="-1"  >请选择</option>
                                                <option value="0"  <s:if test="#request.map.industory==0">selected="selected"</s:if>>IT|通信|电子|互联网</option>
                                                <option value="1" <s:if test="#request.map.industory==1">selected="selected"</s:if>>金融业</option>
                                                <option value="2" <s:if test="#request.map.industory==2">selected="selected"</s:if>>房地产|建筑业</option>
                                                <option value="3" <s:if test="#request.map.industory==3">selected="selected"</s:if>>商业服务</option>
                                                <option value="4" <s:if test="#request.map.industory==4">selected="selected"</s:if>>贸易|批发|零售|租赁业</option>
                                                <option value="5" <s:if test="#request.map.industory==5">selected="selected"</s:if>>文体教育|工艺美术</option>
                                                <option value="6" <s:if test="#request.map.industory==6">selected="selected"</s:if>>生产|加工|制造</option>
                                                <option value="7" <s:if test="#request.map.industory==7">selected="selected"</s:if>>交通|运输|物流|仓储</option>
                                                <option value="8" <s:if test="#request.map.industory==8">selected="selected"</s:if>>服务业</option>
                                                <option value="9" <s:if test="#request.map.industory==9">selected="selected"</s:if>>文化|传媒|娱乐|体育</option>
                                                <option value="10" <s:if test="#request.map.industory==10">selected="selected"</s:if>>能源|矿产|环保</option>
                                                <option value="11" <s:if test="#request.map.industory==11">selected="selected"</s:if>>政府|非盈利机构</option>
                                                <option value="12" <s:if test="#request.map.industory==12">selected="selected"</s:if>>农|林|牧|渔</option>
                                                <option value="13" <s:if test="#request.map.industory==13">selected="selected"</s:if>>其他</option>
											 </select>
											 <span style="color: red; float: none;" id="u_industory" class="formtips"></span>
					                        </td>
					                      </tr>
					
							            <tr>
								           <td align="right">
						                        	企业性质： <span style="color: red; float: none;" class="formtips">*</span>
					                        </td>
					                        <td>
						                     <select name="paramMap.companyType" id="companyType"  >
											    <option value="-1" >请选择</option>
											    <option value="0" <s:if test="#request.map.companyType==0">selected="selected"</s:if>>国企</option>
												<option value="1" <s:if test="#request.map.companyType==1">selected="selected"</s:if>>民营</option>
												<option value="2" <s:if test="#request.map.companyType==2">selected="selected"</s:if>>合资</option>
												<option value="3" <s:if test="#request.map.companyType==3">selected="selected"</s:if>>外商独资</option>
												<option value="4" <s:if test="#request.map.companyType==4">selected="selected"</s:if>>股份制企业</option>
												<option value="5" <s:if test="#request.map.companyType==5">selected="selected"</s:if>>上市公司</option>
												<option value="6" <s:if test="#request.map.companyType==6">selected="selected"</s:if>>代表处</option>
												<option value="7" <s:if test="#request.map.companyType==7">selected="selected"</s:if>>国家机关</option>
												<option value="8" <s:if test="#request.map.companyType==8">selected="selected"</s:if>>事业单位</option>
												<option value="9" <s:if test="#request.map.companyType==9">selected="selected"</s:if>>其他</option>
											 </select>
											 <span style="color: red; float: none;" id="u_companyType" class="formtips"></span>
					                        </td>
							            </tr>
										 <tr>
										    <td align="right">
						                         	公司规模：<span style="color: red; float: none;" class="formtips">*</span>
					                        </td>
					                        <td>
						                     <select name="paramMap.companyScale" id="companyScale"  >
											   <option value="-1" >请选择</option>
                	                           <option value="0" <s:if test="#request.map.companyScale==0">selected="selected"</s:if>>20人以下</option>
                                               <option value="1" <s:if test="#request.map.companyScale==1">selected="selected"</s:if>>20-99人</option>
                                               <option value="2" <s:if test="#request.map.companyScale==2">selected="selected"</s:if>>100-499人</option>
                                               <option value="3" <s:if test="#request.map.companyScale==3">selected="selected"</s:if>>500-999人</option>
                                               <option value="4" <s:if test="#request.map.companyScale==4">selected="selected"</s:if>>1000-9999人</option>
                                               <option value="5" <s:if test="#request.map.companyScale==5">selected="selected"</s:if>>10000人以上</option>
											 </select>
											 <span style="color: red; float: none;" id="u_companyScale" class="formtips"></span>
					                        </td>
										 </tr>
											<tr>
												<td align="right">
													成立时间：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188 Wdate" name="paramMap.foundDate" value="${map.foundDate}" id="foundDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  />
													<span style="color: red; float: none;" id="u_foundDate" class="formtips"></span>
												</td>
											</tr>
											<tr>
                                              <td align="right">
													法人代表：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.legalPerson" value="${map.legalPerson}" id="legalPerson" />
													<span style="color: red; float: none;" id="u_legalPerson" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													工商注册号：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.regNum" value="${map.regNum}" id="regNum" />
													<span style="color: red; float: none;" id="u_regNum" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													组织机构代码：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.organizationCode" value="${map.organizationCode}" id="organizationCode" />
													<span style="color: red; float: none;" id="u_organizationCode" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													注册资本：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.registeredCapital" value="${map.registeredCapital}" id="registeredCapital" />
													<span style="color: red; float: none;" id="u_registeredCapital" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业开户行：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.bankName" value="${map.bankName}" id="bankName" />
													<span style="color: red; float: none;" id="u_bankName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业开户名称：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.accountName" value="${map.accountName}" id="accountName" />
													<span style="color: red; float: none;" id="u_accountName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业对公银行账户：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.publicBankaccount" value="${map.publicBankAccount}" id="publicBankaccount" />
													<span style="color: red; float: none;" id="u_publicBankaccount" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													企业网址：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.website" value="${map.website}" id="website" />
													<span style="color: red; float: none;" id="u_website" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													联系电话：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.phone" value="${map.phone}" id="phone" />
													<span style="color: red; float: none;" id="u_phone" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													邮件地址：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.email" value="${map.email}" id="email" />
													<span style="color: red; float: none;" id="u_email" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													联系人姓名：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.linkmanName" value="${map.linkmanName}" id="linkmanName" />
													<span style="color: red; float: none;" id="u_linkmanName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													联系人手机：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.linkmanMobile"
														id="linkmanMobile" value="${map.linkmanMobile}" />
													<span style="color: red; float: none;" id="u_linkmanMobile class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司简介：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<textarea  rows="3" cols="27"  name="paramMap.companyDescription"  id="companyDescription" >${map.companyDescription}</textarea>
													<span style="color: red; float: none;" id="u_companyDescription" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right" id="headJpg">
													公司头像：<span style="color: red; float: none;" class="formtips">*</span>
												</td>
												<td>
													<input type="button" id="btn_headJpg" class="scbtn"
														style="cursor: pointer;" value="点击上传" />
												</td>
											</tr>
											<tr>
												<td align="right">
													&nbsp;
												</td>
												<td class="tx">
                                                 	<s:if test="#request.map.headJpg==null">	
                                                  	<img id="img" src="${headImg}" width="62" height="62"
														name="paramMap.headJpg" />
													</s:if>
                                                    <s:elseif test="#request.map.headJpg!=null">
                                                    	<img id="img" src="${map.headJpg}" width="62" height="62"
														name="paramMap.headJpg" />
                                                    </s:elseif>
                                                    <s:else>
                                                      <img id="img" src="${headImg}" width="62" height="62"
														name="paramMap.headJpg" />
                                                    </s:else>
												</td>
											</tr>
											<tr>
												<td align="right">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="right">
													&nbsp;
												</td>
												<td style="padding-top: 20px;">
												<span style="color: red;" id="msg">
												  <s:if test="#request.map.auditStatus==1">该项等待审核</s:if>
												  <s:elseif test="#request.map.auditStatus==2">该项审核未通过</s:elseif>
												  <s:elseif test="#request.map.auditStatus==3">该项审核已通过</s:elseif>
												  <s:else>该用户没有填写任何基本资料信息,无法进行审核</s:else>
												  </span>
												  <a href="javascript:void(0)" id="h">
													<input type="button" value="审核通过" class="bcbtn"
														id="jc_yes"/>
															<input type="button" value="审核不通过" class="bcbtn"
														id="jc_no" />
															<input type="button" value="修改并保存" class="bcbtn"
														id="jc_btn" />
													<input type="hidden" value="${request.map.auditStatus}" id="hh"/>
													</a>
												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
				</div>
			</div>
		<!-- 引用底部公共部分 -->
		<script type="text/javascript" src="../script/nav-jk.js"></script>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script src="../script/jquery-2.0.js" type="text/javascript"></script>
		<script >
		if($("#hh").val() == 3){
			 $("#jc_yes").hide(); 
			 $("#jc_no").val("取消审核");
		}
		//如果没有填写基本信息的时候 将所有
		var flag = '${flag}';//标识用户时候填写了基本信息
		if(flag=="1"){
	       //将所有的输入框给禁用掉
	       $("#orgName").attr("disabled","true");
	       $("#address").attr("disabled","true");
	       $("#industory").attr("disabled","true");
	       $("#companyType").attr("disabled","true");
	       $("#companyScale").attr("disabled","true");
	       $("#foundDate").attr("disabled","true");
	       $("#legalPerson").attr("disabled","true");
	       $("#regNum").attr("disabled","true");
	       $("#organizationCode").attr("disabled","true");
	       $("#registeredCapital").attr("disabled","true");
	       $("#bankName").attr("disabled","true");
	       $("#accountName").attr("disabled","true");
	       $("#publicBankAccount").attr("disabled","true");
	       $("#website").attr("disabled","true");
	       $("#phone").attr("disabled","true");  
	       $("#email").attr("disabled","true"); 
	       $("#linkmanName").attr("disabled","true"); 
	       $("#linkmanMobile").attr("disabled","true"); 
	       $("#companyDescription").attr("disabled","true"); 
	       $("#btn_headJpg").attr("disabled","true"); 
	       $("#h").hide();
		}
		</script>
<script>

  //企业全称
  	if($(this).attr("id")=="orgName"){  
		if($(this).val() ==""){
			$("#u_orgName").attr("class", "formtips onError");
			$("#u_orgName").html("请填写企业全称！");
		}else if($(this).val().length <2 || $(this).val().length> 100){   
	   		$("#u_orgName").attr("class", "formtips onError");
	   		$("#u_orgName").html("企业全称长度为2-100个字符"); 
	    }else{   
	    	$("#u_orgName").attr("class", "formtips");
	    	$("#u_orgName").html(""); 
	    } 
    }
     //============企业地址
    if($(this).attr("id")=="address"){
    if($(this).val() ==""){
    	$("#u_address").attr("class", "formtips onError");
    	$("#u_address").html("请填写企业地址");
    }else{
    	$("#u_address").attr("class", "formtips");
      	$("#u_address").html("");
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
             } 
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
             if($(this).attr("id")=="email"){
                if(this.value==""){
					$("#u_email").attr("class", "formtips onError")  
					$("#u_email").html("请输入邮箱地址");
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

	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	     if($("#address").val()==""){
	        $("#u_address").html("请填写企业地址");
            return false;
	      }
	     if($("#industory").val()==-1){
	         $("#u_industory").html("请选择行业类别");
             return false ;
	     }
	     if($("#companyType").val()==-1){
	        $("#u_companyType").html("请选择企业性质");
	        return false;
	      }
	      if($("#companyScale").val()==-1){ 
	        $("#u_companyScale").html("请选择公司规模");
	        return false;
	      }
	      if($("#foundDate").val()==""){
	        $("#u_foundDate").html("请填写成立时间");
            return false;
	      }
	       if($("#legalPerson").val()==""){
	        $("#u_legalPerson").html("请填写法人代表");
            return false;
	      }
	      if($("#regNum").val()==""){
	        $("#u_regNum").html("请填写工商注册号");
            return false;
	      }
	      if($("#organizationCode").val()==""){
	        $("#u_organizationCode").html("请填写组织机构代码");
            return false;
	      }
	       if($("#phone").val()==""){
	        $("#u_phone").html("请填写联系电话");
            return false;
	      }
	      if($("#email").val()==""){
	        $("#u_email").html("请填写邮件地址");
            return false;
	      }
	      if($("#linkmanName").val()==""){
	        $("#u_linkmanName").html("请填写联系人姓名");
            return false;
	      }
	       if($("#linkmanMobile").val()==""){
	        $("#u_linkmanMobile").html("请填写联系人手机");
            return false;
	      }
	      if($("#companyDescription").val()==""){
	        $("#u_companyDescription").html("请填写公司简介");
            return false;
	      }
	  	 if($("#img").attr("src")==""){
	       alert("请上传你的个人头像");
	       return false;
	  	 }

	    var param = {};
	    param["paramMap.id"] = ${map.id};
	    param["paramMap.userId"] = ${map.userId};
	    param["paramMap.orgName"]=$("#orgName").val();
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
	    param["paramMap.email"]=$("#email").val();
	    param["paramMap.linkmanName"]=$("#linkmanName").val();
	    param["paramMap.linkmanMobile"]=$("#linkmanMobile").val();
	    param["paramMap.companyDescription"]=$("#companyDescription").val();
	    //用户头像路径
	    param["paramMap.headJpg"]=$("#img").attr("src");
	    $.post("updateOrgBaseDataAdmin.mht",param,function(data){
	       var callBack = data.msg;
	       if(callBack == undefined || callBack == ''){
		      $('#right').html(data);
		      $(this).show();
		   }
	       if(data.msg=="保存成功"){
	         alert("保存成功");
	         window.location.reload();
	       }else{
	       if(data.msg=="保存失败"){
	         alert("保存失败");
	       }
	       if(data.msg=="请填写企业地址"){
	         alert("请填写企业地址");
	       }
	       if(data.msg=="请选择行业类别"){
	          alert("请选择行业类别");
	       }
	       if(data.msg=="请选择企业性质"){
	        alert("请选择企业性质");
	       }
	       if(data.msg=="请选择公司规模"){
	        alert("请选择公司规模");
	       }
	       if(data.msg=="请填写成立时间"){
	         alert("请填写成立时间");
	       }
	       if(data.msg=="请填写法人代表"){
	         alert("请填写法人代表");
	       }
	       if(data.msg=="请填写工商注册号"){
	         alert("请填写工商注册号");
	       }
	       if(data.msg=="请填写组织机构代码"){
	         alert("请填写组织机构代码");
	       }
	       if(data.msg=="请填写联系电话"){
	            alert("请填写联系电话");
	       }
           if(data.msg=="请填写邮件地址"){
	          alert("请填写邮件地址");
	       }
	       if(data.msg=="请填写联系人姓名"){
	           alert("请填写联系人姓名");
	       }
	        if(data.msg=="请填写联系人手机"){
	        alert("请填写联系人手机");
	       }
	      if(data.msg=="请填写公司简介"){
	         alert("请填写公司简介");
	       }
	      if(data.msg=="请上传你的个人头像"){
	         alert("请上传你的个人头像");
	       }
	       }
	    });
	    
	});
</script>

		<script>
			$(function(){
				//上传图片
				$("#btn_headJpg").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					 //window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					 window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
					var headImgPath = $("#img").attr("src");
					if(headImgPath!=""){
						 $("#u_img").html("");
					}else{ 
						alert("上传失败！");	
					}
				});
			});
			function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "../upload/"+basepath+"/"+fileName;
					$("#img").attr("src",path);
					$("#setImg").attr("src",path);
		  		}
			}
			function getDirNum(){
		      var date = new Date();
		 	  var m = date.getMonth()+1;
		 	  var d = date.getDate();
		 	  if(m<10){
		 	  	m = "0"+m;
		 	  }
		 	  if(d<10){
		 	  	d = "0"+d;
		 	  }
		 	  var dirName = date.getFullYear()+""+m+""+d;
		 	  return dirName; 
			}
		</script>
			<script type="text/javascript">
		  $(function(){
		     //样式选中
     $("#jk_hover").attr('class','nav_first');
	 $("#jk_hover div").removeClass('none');
		  });
		</script>

   <script>
  	
      //审核
     $("#jc_yes").click(function(){
	   var param = {};
	   param["paramMap.flag"] = 3;
	   param["paramMap.id"] = ${map.userId};
	   param["paramMap.userType"] = ${map.userType};
	    $.post("userBaseDataCheck.mht",param,function(data){
	       var callBack = data.msg;
	      if(callBack == undefined || callBack == ''){
		                 $('#right').html(data);
		                 $(this).show();
		                 }
	       if(callBack=="保存成功"){
	        alert("审核成功");               
	        window.location.reload();
	       }else{
	         alert("审核失败");
	       }
	    });
	});
	
	 $("#jc_no").click(function(){
	   var param = {};
	   param["paramMap.flag"] = 2;
	   param["paramMap.id"] = ${map.userId};
	   param["paramMap.userType"] = ${map.userType};
	    $.post("userBaseDataCheck.mht",param,function(data){
	    var callBack = data.msg;
	      if(callBack == undefined || callBack == ''){
		                 $('#right').html(data);
		                 $(this).show();
		                 }
	       if(data.msg=="保存成功"){
	        alert("取消审核成功");	       
	        window.location.reload();
	       }else{
	         alert("取消审核失败");
	       }
	    });
	});
	
   </script>
	</body>
</html>
