<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>用户可选认证界面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									可选资料认证
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
									用户名：${map.username }
								</td>
								<td class="f66 leftp200">
									企业全称：${map.orgName }
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
								跟踪审核员：${map.tausername }
								</td>
							</tr>
							<tr>
							 <td colspan="2" class="blue12 left">基本认证：通过了 <a  style="color:  red;"> ${totalPass.total } </a> 项</td>
							</tr>
							<tr>
							    <td class="blue12 left">
							     营业执照： 
							     <s:if test="#request.map.tmLicenseStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.tmLicenseStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.tmLicenseStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
								<td class="f66 leftp200">
								税务登记证：
								 <s:if test="#request.map.tmTaxRegStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.tmTaxRegStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.tmTaxRegStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									组织机构代码证：
									  <s:if test="#request.map.tmOrgCodeStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.tmOrgCodeStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.tmOrgCodeStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									可选认证：通过了 <a style="color:  red;">${SelectPassTotal.total}</a> 项
								</td>
							</tr>
							<tr>
							</tr>
							</table>
						<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
							<tr class="gvHeader" style="height: 22px;"><th class="blue12 center">认证</th><th class="blue12 center">认证状态</th ><th class="blue12 center">认证积分</th><th class="blue12 center">审核观点</th><th class="blue12 center">审核时间</th><th class="blue12 center">操作</th></tr>
							<tr class="gvItem">
							<td>现场</td><td>
							<s:if test="#request.SelectCledit.tmCompanyProveStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmCompanyProveStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmCompanyProveStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else>
							</td>
							<td>
							${SelectCledit.companycriditing }
						    </td>
						    <td>${SelectCledit.companyoption}</td><td>${SelectCledit.companyauthTime }</td>
						    <td>
						    <s:if test="#request.SelectCledit.tmCompanyProveStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=20">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmCompanyProveStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=20">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmCompanyProveStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=20">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!-- -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>央行信用报告</td>
							<td><s:if test="#request.SelectCledit.tmCredirReportStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmCredirReportStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmCredirReportStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
							<td>
							${SelectCledit.creditreportcriditing }
						    </td>
						    <td>${SelectCledit.creditreportoption}</td><td>${SelectCledit.creditreportauthTime }</td>
						    <td><s:if test="#request.SelectCledit.tmCredirReportStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=21">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmCredirReportStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=21">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmCredirReportStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=21">审核</a></s:elseif>
						    <s:else>--</s:else></td>
							<!--  -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>近两年及今年完税证明</td><td><s:if test="#request.SelectCledit.tmTaxProveStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmTaxProveStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmTaxProveStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						 ${SelectCledit.taxprovecriditing }
						    </td>
						    <td>${SelectCledit.taxproveoption}</td><td>${SelectCledit.taxproveauthTime }</td><td>
						    <s:if test="#request.SelectCledit.tmTaxProveStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=22">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmTaxProveStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=22">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmTaxProveStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=22">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
						<tr class="gvItem">
							<!--  -->
							<td>增值税纳税申请表</td><td><s:if test="#request.SelectCledit.tmVatFormStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmVatFormStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmVatFormStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.vatformcriditing }
						    </td>
						    <td>${SelectCledit.vatformoption}</td><td>${SelectCledit.vatformauthTime }</td><td>
						     <s:if test="#request.SelectCledit.tmVatFormStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=23">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmVatFormStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=23">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmVatFormStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=23">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
							<tr class="gvItem">
						    <!--  -->
							<td>所得税纳税申请表</td><td><s:if test="#request.SelectCledit.tmIncomeTaxFormStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmIncomeTaxFormStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmIncomeTaxFormStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.imcometaxcriditing }
						    </td>
						    <td>${SelectCledit.imcometaxoption}</td><td>${SelectCledit.imcometaxauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmIncomeTaxFormStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=24">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmIncomeTaxFormStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=24">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmIncomeTaxFormStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=24">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>营业税纳税申请表</td><td><s:if test="#request.SelectCledit.tmBaseAccPermitStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.biztaxcriditing }
						    </td>
						    <td>${SelectCledit.biztaxoption}</td><td>${SelectCledit.biztaxauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmBizTaxFormStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=25">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmBizTaxFormStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=25">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmBizTaxFormStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=25">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>开户许可证（基本开户行）</td><td><s:if test="#request.SelectCledit.tmBaseAccPermitStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.baseAccpermitcriditing }
						    </td>
						    <td>${SelectCledit.baseAccpermitoption}</td><td>${SelectCledit.baseAccpermitauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmBaseAccPermitStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=26">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=26">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmBaseAccPermitStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=26">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>开户许可证（主要结算行）</td><td><s:if test="#request.SelectCledit.tmMainClearingPermitStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmMainClearingPermitStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmMainClearingPermitStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.mainclearingpermitscriditing }
						    </td>
						    <td>${SelectCledit.mainclearingpermitsoption}</td><td>${SelectCledit.mainclearingpermitsauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmMainClearingPermitStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=27">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmMainClearingPermitStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=27">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmMainClearingPermitStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=27">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>房产证明</td><td><s:if test="#request.SelectCledit.tmHouseProveStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmHouseProveStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmHouseProveStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.houseprovecriditing }
						    </td>
						    <td>${SelectCledit.houseproveoption}</td><td>${SelectCledit.houseproveauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmHouseProveStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=28">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmHouseProveStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=28">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmHouseProveStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=28">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>项目承包合同书</td><td><s:if test="#request.SelectCledit.tmProjectContractStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmProjectContractStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmProjectContractStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.projectcontractcriditing }
						    </td>
						    <td>${SelectCledit.projectcontractoption}</td><td>${SelectCledit.projectcontractauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmProjectContractStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=29">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmProjectContractStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=29">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmProjectContractStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=29">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>相关营运资质</td><td><s:if test="#request.SelectCledit.tmOperatingAptStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmOperatingAptStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmOperatingAptStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.pperatingaptcriditing }
						    </td>
						    <td>${SelectCledit.pperatingaptoption}</td><td>${SelectCledit.pperatingaptauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmOperatingAptStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=30">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmOperatingAptStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=30">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmOperatingAptStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=30">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>授信申请书及附件</td><td><s:if test="#request.SelectCledit.tmCreditApplyStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmCreditApplyStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmCreditApplyStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.creditapplycriditing }
						    </td>
						    <td>${SelectCledit.creditapplyoption}</td><td>${SelectCledit.creditapplyauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmCreditApplyStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=31">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmCreditApplyStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=31">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmCreditApplyStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=31">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>年检纪录</td><td><s:if test="#request.SelectCledit.tmAnnualInspectionStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmAnnualInspectionStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmAnnualInspectionStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.annualinspectioncriditing }
						    </td>
						    <td>${SelectCledit.annualinspectionoption}</td><td>${SelectCledit.annualinspectionauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmAnnualInspectionStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=32">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmAnnualInspectionStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=32">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmAnnualInspectionStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=32">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>贷款卡</td><td><s:if test="#request.SelectCledit.tmLoanCardStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmLoanCardStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmLoanCardStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.loancardcriditing }
						    </td>
						    <td>${SelectCledit.loancardoption}</td><td>${SelectCledit.loancardauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmLoanCardStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=33">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmLoanCardStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=33">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmLoanCardStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=33">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    <tr class="gvItem">
						    <!--  -->
							<td>担保</td><td><s:if test="#request.SelectCledit.tmGuaranteeStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmGuaranteeStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmGuaranteeStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.guaranteecriditing }
						    </td>
						    <td>${SelectCledit.guaranteeoption}</td><td>${SelectCledit.guaranteeauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmGuaranteeStatus==3"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=34">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmGuaranteeStatus==2"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=34">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmGuaranteeStatus==1"><a href="queryselectindex.mht?userId=${map.id}&userType=${map.userType}&TypeId=34">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    </tbody>
							
							</table>
							
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
	</body>	
</html>