<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>银行卡审核-修改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript">
			
			$(function(){
				//提交表单
				$("#btn_save").click(function(){
					if(isBlank($("#province").val()) || isBlank($("#city").val())){
				           $("#u_Pro_City").html("请填写省市");
				           return false;
				     }
					$(this).hide();
					$("#updateUserBankInfo").submit();
					return false;
				});
	            $("#province").change(function(){
	                var provinceId = $("#province").val();
	                citySelectInit(provinceId, 2);
	            });
	                 $("#registedPlacePro").change(function(){
	                var provinceId = $("#registedPlacePro").val();
	                registedPlaceCity(provinceId, 2);
	            });
			});
			
			function citySelectInit(parentId, regionType){
			    var _array = [];
			    _array.push("<option value=''>--请选择--</option>");
			    var param = {};
			    param["regionType"] = regionType;
			    param["parentId"] = parentId;
			    $.post("ajaxqueryRegion.mht",param,function(data){
			        for(var i = 0; i < data.length; i ++){
			            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			        }
			        $("#city").html(_array.join(""));
			    });
			}
			function registedPlaceCity(parentId, regionType){
			    var _array = [];
			    _array.push("<option value=''>--请选择--</option>");
			    var param = {};
			    param["regionType"] = regionType;
			    param["parentId"] = parentId;
			    $.post("ajaxqueryRegion.mht",param,function(data){
			        for(var i = 0; i < data.length; i ++){
			            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			        }
			        $("#registedPlaceCity").html(_array.join(""));
			    });
			}

		</script>
		
	</head>
	<body>
		<!--  <form id="updateHelp" name="updateHelp" action="updateHelp.mht" method="post"> -->
		<form id="updateUserBankInfo" name="updateUserBankInfo" action="updateUserBankInfo.mht" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="120" height="28" class="xxk_all_a">
								<a href="queryUserBankInit.mht">用户银行卡管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="120" class="xxk_all_a">
									<a href="queryModifyBankInit.mht">用户银行卡变更</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
                                <td width="120" class="xxk_all_a">
                                    <a href="queryNoAuditBank.mht">未审核的银行卡</a>
                                </td>
                                <td width="2">
                                    &nbsp;
                                </td>								
								<td width="120" class="main_alll_h2">
                                    <a href="queryBankCardInfo.mht?bankId=${paramMap.id}">审核银行卡</a>
                                </td>
                                <td width="2">
                                    &nbsp;
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
					 
					</div>
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 120px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_username" name="paramMap.username"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66">
								<s:textfield id="tb_realName" name="paramMap.realName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<!-- tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								    审核人：
										</td>
								 <td align="left" class="f66">
								 <s:select id="checkUser" list="checkers" name="paramMap.userName" listKey="id" listValue="userName"  />
										<span class="require-field">*<s:fielderror fieldName="paramMap['userName']"></s:fielderror></span>
								 
								 </td>
							</tr> -->
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									身份证：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_idNo" name="paramMap.idNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
								</td>
							</tr>
							
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									开户行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBankName"></s:property>-->
									<s:textfield id="tb_modifiedBankName" name="paramMap.bankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 开户支行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBranchBankName"></s:property>-->
									<s:textfield id="tb_modifiedBranchBankName" name="paramMap.branchBankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 银行卡号：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedCardNo"></s:property></td>-->
									<s:textfield id="tb_modifiedCardNo" name="paramMap.cardNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
								  <s:hidden  id="tb_modifiedTime" name="paramMap.modifiedTime"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										
										<s:hidden  id="tb_id" name="paramMap.bankId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										</td>
							</tr>
				          <tr id="jg">
				                    <td style="width: 120px;  height: 25px;" align="right"
                                    class="blue12">开户行所在地：
				                    </td>
				                    <td>
				                        <s:select id="province" name="paramMap.provinceId"
				                            cssStyle="width:107px;"
				                            list="#session.provinceList" listKey="regionId"
				                            listValue="regionName" headerKey="" headerValue="--请选择--"
				                            onchange="javascript:if($('#province').val()!=''){$('#u_Pro_City').html('')}" />
				                        <s:select id="city" name="paramMap.cityId"
				                            cssStyle="width:107px;"
				                            list="#session.cityList" listKey="regionId"
				                            listValue="regionName" headerKey="" headerValue="--请选择--"
				                            onchange="javascript:if($('#city').val()!=''){$('#u_Pro_City').html('')}" />
				                        <span style="color:red">*</span>
				                        <span style="color: red; float: none;" id="u_Pro_City"
				                            class="formtips"></span>
				                    </td>
				                </tr>		
							<tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								审核状态：
								</td>
								<td align="left" class="f66">
							       <s:radio id="radio1" name="paramMap.status" list="#{1:'审核通过',3:'审核不通过'}" value="1"></s:radio>
								 <span class="require-field">*<s:fielderror fieldName="paramMap['status']"></s:fielderror></span>
								 
								 </td>
								</tr>
								<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
								  审核意见：
							       
								 </td>
								<td align="left" class="f66">
								<s:textarea id="remark" cssStyle="margin-left:80px;" name="paramMap.remark" value="" cols="30" rows="5"></s:textarea>
								<span class="require-field">*<s:fielderror fieldName="paramMap['remark']"></s:fielderror></span>
								 
								</td>
								</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
		</form>
	</body>
</html>
