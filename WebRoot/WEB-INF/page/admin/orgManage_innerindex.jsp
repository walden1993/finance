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
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>
		    $(function(){
		       $("#li_work").click(function() {
			   window.location.href = 'queryUserMangework.mht?uid=${map.userId}';
		});
		</script>

		
	</head>
	<body style="min-width: 1000px;">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<!-- div>
						<table width="10%" border="0" cellspacing="0" cellpadding="0">
							<tr >
							<td width="100%" height="28" class="main_alll_h2">
									<a href="javascript:void(0)">企业基本信息</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</tr>
						</table>
					</div> -->
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<!--第1个table  -->
						
						  <td  style="width: 18%"  valign="top">
						  <div style="margin-top: 0px;">
						<table  border="1" cellspacing="1" cellpadding="3"  width="100%" style="min-width: 200px">
						<tr >
							<td width="100%" height="28" class="main_alll_h2">
									<a href="javascript:void(0)">企业基本信息</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</tr>
							<tr class="gvItem">
								<td  align="right" 
									class="blue12" width="60">
									用户名：
								</td>
								<td align="left" width="140">
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
						<td id="secodetble" style="width: 60%">
						<table  border="0" cellspacing="1" cellpadding="3" width="60%">
							<tr>
							<td>
						    <table>
							 <tr><td class="main_alll_h2" width="100px" height="20"  align="center">
							<a style="cursor: pointer;">企业基本信息</a></td>
							<td>&nbsp;</td>
							<td class="xxk_all_a1" width="100px" height="20"  align="center">
							<a href="queryOrgManageInvest.mht?i=${map.user_id}&userType=${userType}&map=${map}" style="cursor: pointer;">投资信息</a>
							</td>
							</tr>
							</table>
							</td>
							</tr>
							<!-- 
								<td  align="center" colspan="2" height="20px;"
									 bgcolor="#CC0000" style=" font-size: 12px;font-family:serif;" >
								<span style="margin-right: 0px; " align="left" ><a style="cursor: pointer;">个人基本信息</a></span>
								<span> <a style="cursor: pointer;" id="li_work">工作信息</a></span>
								</td>
							 
								<td align="center" class="blue12" bgcolor="#8594A9" width="10" >
								
								</td>
								-->
							<tr>
							<td align="center">
							<table>
							<tr>
                                 <td height="36" align="right" class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>企业全称：
								</td>
								<td align="left" class="f66">			
										<input type="text" class="inp188" name="paramMap.organization_name"
														id="organization_name" value="${map.organization_name}" disabled="disabled"/>
								</td> 							
							
							</tr>
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>企业地址：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.address"
														id="address" value="${map.address }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							<tr>
								<td height="36" align="right" class="blue12">
							<span style="color: red; float: none;" class="formtips">*</span>行业类别：
								</td>
								<td>
									<select name="paramMap.industory" id="industory" disabled="disabled" >
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
								
								</td>
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
							<span style="color: red; float: none;" class="formtips">*</span>企业性质：
								</td>
								<td>
									<select name="paramMap.company_type" id="company_type" disabled="disabled" >
									    <option value="-1" >请选择</option>
									    <option value="0" <s:if test="#request.map.company_type==0">selected="selected"</s:if>>国企</option>
             	                            <option value="1" <s:if test="#request.map.company_type==1">selected="selected"</s:if>>民营</option>
                                           <option value="2" <s:if test="#request.map.company_type==2">selected="selected"</s:if>>合资</option>
                                           <option value="3" <s:if test="#request.map.company_type==3">selected="selected"</s:if>>外商独资</option>
                                           <option value="4" <s:if test="#request.map.company_type==4">selected="selected"</s:if>>股份制企业</option>
                                           <option value="5" <s:if test="#request.map.company_type==5">selected="selected"</s:if>>上市公司</option>
                                           <option value="6" <s:if test="#request.map.company_type==6">selected="selected"</s:if>>代表处</option>
                                           <option value="7" <s:if test="#request.map.company_type==7">selected="selected"</s:if>>国家机关</option>
                                           <option value="8" <s:if test="#request.map.company_type==8">selected="selected"</s:if>>事业单位</option>
                                           <option value="9" <s:if test="#request.map.company_type==9">selected="selected"</s:if>>其他</option>
									 </select>
								</td>
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
							<span style="color: red; float: none;" class="formtips">*</span>企业规模：
								</td>
								<td>
									<select name="paramMap.company_scale" id="company_scale" disabled="disabled" >
										   <option value="-1" >请选择</option>
               	                           <option value="0" <s:if test="#request.map.company_scale==0">selected="selected"</s:if>>20人以下</option>
                                           <option value="1" <s:if test="#request.map.company_scale==1">selected="selected"</s:if>>20-99人</option>
                                           <option value="2" <s:if test="#request.map.company_scale==2">selected="selected"</s:if>>100-499人</option>
                                           <option value="3" <s:if test="#request.map.company_scale==3">selected="selected"</s:if>>500-999人</option>
                                           <option value="4" <s:if test="#request.map.company_scale==4">selected="selected"</s:if>>1000-9999人</option>
                                           <option value="5" <s:if test="#request.map.company_scale==5">selected="selected"</s:if>>10000人以上</option>
							 		</select>
								</td>
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>成立时间：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.found_date"
														id="found_date" value="${map.found_date }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
								
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>法定代表人：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.legal_person"
														id="legal_person" value="${map.legal_person }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>工商注册号：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.reg_num"
														id="reg_num" value="${map.reg_num }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>组织机构代码：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.organization_code"
														id="organization_code" value="${map.organization_code }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									注册资本：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.registered_capital"
														id="registered_capital" value="${map.registered_capital }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									企业开户银行：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.bank_name"
														id="bank_name" value="${map.bank_name }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									企业开户名称：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.account_name"
														id="account_name" value="${map.account_name }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									企业对公银行账户：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.public_bank_account"
														id="public_bank_account" value="${map.public_bank_account }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									企业网址：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.website"
														id="website" value="${map.website }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>联系电话：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.phone"
														id="phone" value="${map.phone }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>联系人姓名：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.linkman_name"
														id="linkman_name" value="${map.linkman_name }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
							
							<tr>
							       <td height="36" align="right"
									class="blue12">
									<span style="color: red; float: none;" class="formtips">*</span>联系人手机：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.linkman_mobile"
														id="linkman_mobile" value="${map.linkman_mobile }" disabled="disabled"/>
								</td> 	
							
							</tr>
							<tr>
								<td align="right">
									<span style="color: red; float: none;" class="formtips">*</span>企业简介：
								</td>
								<td>
									<textarea  rows="3" cols="27"  name="paramMap.company_description"  id="company_description" >${map.company_description}</textarea>
									<span style="color: red; float: none;" id="u_company_description" class="formtips"></span>
								</td>
							</tr>
							<tr>
									<td align="right" id="head_jpg">
										<span style="color: red; float: none;" class="formtips">*</span>公司头像：
									</td>
									<td>
										<s:if test="#request.map.head_jpg==null||#request.map.head_jpg==''">
											<img id="img" src="../images/NoImg.GIF"
												style="display: block; width: 100px; height: 100px;" />
										</s:if>
										<s:else>
											<img id="img" src="../${map.head_jpg}" width="62" height="62"
														name="paramMap.head_jpg" />
										</s:else>
								
									</td>
								</tr>
								</table></td>
							</tr>
							
							
							
						</table>
						 </td>

						  </tr>
						</table>
						
						
						<br />
					</div>
				</div>
			</div>
			
		<script>
		  $(function(){
		     if(${message!=null}){
		       alert('${request.message}');
		      }
		  });
		</script>
		
	</body>
</html>
