<%@page import="com.sp2p.entity.User"%>
<%@page import="com.shove.util.DesSecurityUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<% 
	//加密/解密 工具类
	DesSecurityUtil des = new DesSecurityUtil();
%>
<html lang="zh-cn">
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
							  <a href='queryCompayData.mht?from=${from }' ><span>step2 </span>上传资料</a>
							 </li>
							<li>
							  <a><span>step3 </span> 发布融资</a>							  
							</li>
						</ul>
					</div>
					<div class="r-main">
						<div class="til01">
							<ul>
								<li class="on" id="li_geren">
									基本信息
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
													<span style="color: red; float: none;" class="formtips">*</span>企业全称：
												</td>
												<td>
												 <input type="text" class="form-control w200" name="paramMap.organizationName" id="organizationName"  />
											     <span style="color: red; float: none;" id="u_organizationName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业地址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.address"id="address" />
													<span style="color: red; float: none;" id="u_address" class="formtips"></span>
												</td>
											</tr>
										<tr>
					                        <td align="right">
						                         <span style="color: red; float: none;" class="formtips">*</span>行业类别：
					                         </td>
					                        <td>
						                     
						                     <select name="paramMap.industory" id="industory"  >
											    <option value="-1" >请选择</option>
                                                <option value="0" >计算机/互联网/软件</option>
                                                <option value="1" >通信/电子</option>
                                                <option value="2" >金融/法律/会计/保险</option>
                                                 <option value="3" >政府机关</option>
                                                <option value="4" >公共事业</option>
                                                <option value="5" >教育/培训</option>
                                                <option value="6" >媒体/广告</option>
                                                <option value="7" >零售/批发</option>
                                                <option value="8" >餐饮/酒店/旅馆</option>
                                                <option value="9" >交通运输</option>
                                                <option value="10" >房地产业</option>
                                                <option value="11" >能源业</option>
                                                <option value="12" >医疗/制药/卫生/保健</option>
                                                <option value="13" >建筑工程</option>
                                                <option value="14" >娱乐服务业</option>
                                                <option value="15" >体育/艺术</option>
                                                <option value="16" >农业</option>
                                                <option value="17" >公益组织</option>
                                                <option value="18" >其他</option>
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
                	                            <option value="0" >私营企业</option>
                                                <option value="1">个体经营者</option>
                                                <option value="2" >国营企业</option>
                                                <option value="3" >外资企业</option>
                                                <option value="4" >合资企业</option>
                                                <option value="5" >政府机关</option>
                                                <option value="6" >事业单位</option>
                                                <option value="7" >其他</option>
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
                	                           <option value="0" >10人以下</option>
                                               <option value="1" >10-50人</option>
                                               <option value="2" >50-100人</option>
                                               <option value="3" >100-500人</option>
                                               <option value="4">500-2000人</option>
                                               <option value="5" >2000人以上</option>
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
													<input type="text" class="form-control w200 Wdate" name="paramMap.foundDate" id="foundDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  />
													
													<span style="color: red; float: none;" id="u_foundDate" class="formtips"></span>
												</td>
											</tr>
											<tr>
                                              <td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>法人代表：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.legalPerson" id="legalPerson" />
													<span style="color: red; float: none;" id="u_legalPerson" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>工商注册号：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.regNum" id="regNum" />
													<span style="color: red; float: none;" id="u_regNum" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>组织机构代码：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.organizationCode" id="organizationCode" />
													<span style="color: red; float: none;" id="u_organizationCode" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>注册资本：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.registeredCapital" id="registeredCapital" />
													<span style="color: red; float: none;" id="u_registeredCapital" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业开户行：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.bankName" id="bankName" />
													<span style="color: red; float: none;" id="u_bankName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业开户名称：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.accountName" id="accountName" />
													<span style="color: red; float: none;" id="u_accountName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业对公银行账户：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.publicBankaccount" id="publicBankaccount" />
													<span style="color: red; float: none;" id="u_publicBankaccount" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>企业网址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.website" id="website" />
													<span style="color: red; float: none;" id="u_website" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系电话：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.phone" id="phone" />
													<span style="color: red; float: none;" id="u_phone" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>邮件地址：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.email" id="email_s" />
													<span style="color: red; float: none;" id="u_email" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系人姓名：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.linkmanName" id="linkmanName" />
													<span style="color: red; float: none;" id="u_linkmanName" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>联系人手机：
												</td>
												<td>
													<input type="text" class="form-control w200" name="paramMap.linkmanMobile" id="linkmanMobile" />
													<span style="color: red; float: none;" id="u_linkmanMobile" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" class="formtips">*</span>公司简介：
												</td>
												<td>
												
													<textarea  rows="3" cols="27"  name="paramMap.companyDescription" id="companyDescription" ></textarea>
													<span style="color: red; float: none;" id="u_companyDescription" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right" id="personalHead">
													个人头像：
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
                                                  <img id="img" src="images/default-img.jpg" width="62" height="62" name="paramMap.headJpg" />
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


$(document).ready(function(){

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
					$("#u_address").html("请输入企业地址");
				}else if(this.value.length<5 || this.value.length>50){ 
					$("#u_address").attr("class", "formtips onError");
					$("#u_address").html("企业地址长度为5-50个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#u_address").attr("class", "formtips onError");
					$("#u_address").html("企业地址不能含有特殊字符"); 
				} else{
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
           /*  //注册资本
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
					checkRegister('email');
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
					checkRegister('cellphone');
					$("#u_linkmanMobile").html(""); 
				}
             } 
             //公司简介
             if($(this).attr("id")=="companyDescription"){
                if(this.value==""){
					$("#u_companyDescription").attr("class", "formtips onError");
					$("#u_companyDescription").html("请输入公司简介");
				}else if(this.value.length<20 || this.value.length>2000){ 
					$("#u_companyDescription").attr("class", "formtips onError");
					$("#u_companyDescription").html("公司简介长度为20-2000个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#u_companyDescription").attr("class", "formtips onError");
					$("#u_companyDescription").html("公司简介不能含有特殊字符"); 
				} else{
					$("#u_companyDescription").html(""); 
					$("#u_companyDescription").attr("class", "formtips");
				}
             } 
              
     });
     
     

	  $("#jc_btn").click(function(){
	 
	     if($("#organizationName").val()==""){
	     	alert("请填写企业全称");
	        $("#u_organizationName").html("请填写企业全称");
            return false ;
	     }
	     
		if($("#address").val()==""){
			alert("请填写企业地址");
		    $("#u_address").html("请填写企业地址");
		    return false;
		}else if($("#address").val().length<5 || $("#address").val().length>50){ 
			alert("企业地址长度为5-50个字符");
			$("#u_address").html("企业地址长度为5-50个字符"); 
			return false;
		}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test($("#address").val())){
			alert("企业地址不能含有特殊字符");
			$("#u_address").html("企业地址不能含有特殊字符"); 
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
		  		alert("请选择成立时间");
		  		$("#u_foundDate").html("请选择成立时间");
		    	 return false;
		  }
	      
          if($("#legalPerson").val()==""){
          		alert("请填写法人代表");
		        $("#u_legalPerson").html("请填写法人代表");
	            return false;
      	  }else if($("#legalPerson").val().length<2 || $("#legalPerson").val().length>8){ 
      	  		alert("法人代表长度为2-8个字符");
				$("#u_legalPerson").html("法人代表长度为2-8个字符"); 
				return false;
		  }else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test($("#legalPerson").val())){
		  alert("法人代表不能含有特殊字符");
				$("#u_legalPerson").html("法人代表不能含有特殊字符"); 
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
	      	}*/
	      
	      
		 	var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
			if($("#phone").val() ==""){
				alert("请填写联系电话");
				$("#u_phone").html("请填写联系电话");
				return false;
			}else if(!mdd.test($("#phone").val())){
				alert("联系电话格式错误");
				$("#u_phone").html("联系电话格式错误");
				return false;
			}
	      
	     	if($("#email_s").val()==""){
	     		alert("请填写邮件地址");
		        $("#u_email").html("请填写邮件地址");
	            return false;
	      	}else if(!/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#email_s").val())){
				alert("邮箱格式错误");
				$("#u_email").html("邮箱格式错误");
				return false;
			}
	      
	      
	      if($("#linkmanName").val()==""){
		        alert("请填写联系人姓名");
		        $("#u_linkmanName").html("请填写联系人姓名");
	            return false;
	      }else if($("#linkmanName").val().length<2 ||$("#linkmanName").val().length>8){ 
				alert("联系人姓名长度为2-8个字符");
				$("#u_linkmanName").html("联系人姓名长度为2-8个字符"); 
				return false;
		  }else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test($("#linkmanName").val())){
				alert("联系人姓名不能含有特殊字符");
				$("#u_linkmanName").html("联系人姓名不能含有特殊字符"); 
				return false;
		  }
	      
	      
	       if($("#linkmanMobile").val()==""){
		        alert("请填写联系人手机");
		        $("#u_linkmanMobile").html("请填写联系人手机");
	            return false;
	      }else if((!/^\d{11}$/.test($("#linkmanMobile").val()))){
				alert("联系人手机格式错误");
				$("#u_linkmanMobile").html("联系人手机格式错误"); 
				return false;
		  }
	      
	      if($("#companyDescription").val()==""){
	        alert("请填写公司简介");
	        $("#u_companyDescription").html("请填写公司简介");
            return false;
	      }
	   
	   if($("#img").attr("src")==""){
	       alert("请上传你的个人头像");
	       return false;
	   }
	   var param = {};
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
	    
		

	    $.post("addUserCompanyData.mht",param,function(data){
	       if(data.success){
	         alert("保存成功");
	         //如果不是从发布借款跳转过来，那么按照正常的步骤一步一步走
	         window.location.href='queryCompayData.mht';    
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
           var sd=parseInt($(".l-nav").css("height"));
           var sdf=parseInt($(".r-main").css("height"));
	       $(".l-nav").css("height",sd>sdf?sd:sdf-15);

		  });
	   </script>
	</body>
</html>
