<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三好资本</title>
		<link rel="stylesheet" type="text/css" href="../css/css.css" />
		<script src="../script/jquery-2.0.js" type="text/javascript"></script>
		<script>
	
</script>
	</head>
	<body>
		<div class="">
			<div class="nymain">
				<div class="wdzh">
					<div class="r_main">
						<div class="box">
							<h2 class="otherh2x">
								三好资本认证
							</h2>
							<div class="boxmain2">
								<div class="biaoge" style="margin-top: 0px;">

								</div>
								<div class="biaoge">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th colspan="4">
												${map.orgName} &nbsp;&nbsp;&nbsp;&nbsp;信用信息
											</th>
										</tr>
										<tr>
											<th colspan="4">
												信用总分:
												<a>${map.creditrating}</a>&nbsp;&nbsp;&nbsp;&nbsp; 信用额度 ：
												<a>${map.creditLimit }</a>
											</th>
										</tr>
										<tr>
											<td align="center">
												&nbsp;
											</td>
											<td align="center">
												项目
											</td>
											<td align="center">
												状态
											</td>
											<td align="center">
												信用分数
											</td>
										</tr>
										<tr>
											<td align="center">
												基本信息
											</td>
											<td align="center">
												企业详细信息
											</td>
											<td align="center">
												<s:if test="#request.map.auditStatus==1">待审核</s:if>
												<s:elseif test="#request.map.auditStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.auditStatus==3">通过</s:elseif>
												<s:else>未填写</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.auditStatus==1">0</s:if>
												<s:elseif test="#request.map.auditStatus==2">0</s:elseif>
												<s:elseif test="#request.map.auditStatus==3">10</s:elseif>
												<s:else>0</s:else>
											</td>
										</tr>
										<tr>
											<td rowspan="3" align="center">
												必要信用认证
											</td>
											<td align="center">
												营业执照
											</td>
											<td align="center">
												<s:if test="#request.map.tmIdentityauditStatus==3">
         										通过
     											 </s:if>
												<s:elseif test="#request.map.tmLicenseStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmLicenseStatus==1">待审核</s:elseif>
												<s:elseif test="#request.map.tmLicenseStatus==3">通过</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmLicenseCriditing==null">
     											    0
    											</s:if>
												<s:else> ${map.tmLicenseCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												税务登记证
											</td>
											<td align="center">

												<s:if test="#request.map.tmTaxFormStatus==3"> 通过</s:if>
												<s:elseif test="#request.map.tmTaxFormStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmTaxFormStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmTaxFormCriditing==null">0</s:if>
												<s:else> ${map.tmTaxFormCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												组织机构代码证
											</td>
											<td align="center">
												<s:if test="#request.map.tmOrgCodeStatus==3">
        											 通过
      											</s:if>
												<s:elseif test="#request.map.tmOrgCodeStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmOrgCodeStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmOrgCodeCriditing==null">0</s:if>
												<s:else> ${map.tmOrgCodeCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td rowspan="15" align="center">
												可选认证
											</td>
											<td align="center">
												公司证明
											</td>
											<td align="center">
												<s:if test="#request.map.tmCompanyProveStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmCompanyProveStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmCompanyProveStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmCompanyProveCriditing==null">0</s:if>
												<s:else> ${map.tmCompanyProveCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												央行信用报告
											</td>
											<td align="center">
												<s:if test="#request.map.tmCredirReportStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmCredirReportStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmCredirReportStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmCredirReportCriditing==null">0</s:if>
												<s:else> ${map.tmCredirReportCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												近两年及今年完税证明
											</td>
											<td align="center">
												<s:if test="#request.map.tmTaxProveStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmTaxProveStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmTaxProveStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmTaxProveCriditing==null">0</s:if>
												<s:else> ${map.tmTaxProveCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												增值税纳税申请表
											</td>
											<td align="center">
												<s:if test="#request.map.tmVatFormStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmVatFormStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmVatFormStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmVatFormCriditing==null">0</s:if>
												<s:else> ${map.tmVatFormCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												所得税纳税申请表
											</td>
											<td align="center">
												<s:if test="#request.map.tmIncomeTaxFormStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmIncomeTaxFormStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmIncomeTaxFormStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmIncomeTaxFormCriditing==null">0</s:if>
												<s:else> ${map.tmIncomeTaxFormCriditing}</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												营业税纳税申请表
											</td>
											<td align="center">
												<s:if test="#request.map.tmBizTaxFormStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmBizTaxFormStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmBizTaxFormStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmBizTaxFormCriditing==null">0</s:if>
												<s:else> ${map.tmBizTaxFormCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												开户许可证（基本开户行）
											</td>
											<td align="center">
												<s:if test="#request.map.tmBaseAccPermitStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmBaseAccPermitStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmBaseAccPermitStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmBaseAccPermitCriditing==null">0</s:if>
												<s:else> ${map.tmBaseAccPermitCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												开户许可证（主要结算行）
											</td>
											<td align="center">
												<s:if test="#request.map.tmMainClearingPermitStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmMainClearingPermitStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmMainClearingPermitStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmMainClearingPermitCriditing==null">0</s:if>
												<s:else> ${map.tmMainClearingPermitCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												房产证明
											</td>
											<td align="center">
												<s:if test="#request.map.tmHouseProveStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmHouseProveStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmHouseProveStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmHouseProveCriditing==null">0</s:if>
												<s:else> ${map.tmHouseProveCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												项目承包合同书
											</td>
											<td align="center">
												<s:if test="#request.map.tmProjectContractStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmProjectContractStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmProjectContractStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmProjectContractCriditing==null">0</s:if>
												<s:else> ${map.tmProjectContractCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												相关营运资质
											</td>
											<td align="center">
												<s:if test="#request.map.tmOperatingAptStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmOperatingAptStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmOperatingAptStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmOperatingAptCriditing==null">0</s:if>
												<s:else> ${map.tmOperatingAptCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												授信申请书及附件
											</td>
											<td align="center">
												<s:if test="#request.map.tmCreditApplyStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmCreditApplyStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmCreditApplyStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmCreditApplyCriditing==null">0</s:if>
												<s:else> ${map.tmCreditApplyCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												年检纪录
											</td>
											<td align="center">
												<s:if test="#request.map.tmAnnualInspectionStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmAnnualInspectionStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmAnnualInspectionStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmAnnualInspectionCriditing==null">0</s:if>
												<s:else> ${map.tmAnnualInspectionCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												贷款卡
											</td>
											<td align="center">
												<s:if test="#request.map.tmLoanCardStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmLoanCardStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmLoanCardStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmLoanCardCriditing==null">0</s:if>
												<s:else> ${map.tmLoanCardCriditing }</s:else>
											</td>
										</tr>
										<tr>
											<td align="center">
												担保
											</td>
											<td align="center">
												<s:if test="#request.map.tmGuaranteeStatus==3">通过</s:if>
												<s:elseif test="#request.map.tmGuaranteeStatus==2">不通过</s:elseif>
												<s:elseif test="#request.map.tmGuaranteeStatus==1">待审核</s:elseif>
												<s:else>待上传</s:else>
											</td>
											<td align="center">
												<s:if test="#request.map.tmGuaranteeCriditing==null">0</s:if>
												<s:else> ${map.tmGuaranteeCriditing }</s:else>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="footer02">
				<p>
					<img src="images/maitl_bottom1.jpg" />
					版权所有 © 2014 ${sitemap.companyName} ${sitemap.regionName } 备案号：
					<span>${sitemap.certificate }</span>
					<br />
					客服热线（9：00-18：00）: ${sitemap.servicePhone }
					<div style="clear: both;"></div>
				</p>
			</div>
	</body>
</html>
