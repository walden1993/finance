<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
  <div><span style="color:#A9B5C3;font-size: 14px; ">待审核的用户数量为<b style="color: red">${usercount}</b> 个。总的待审核的认证数量为<b style="color: red">${byNamemap}</b>个</span></div>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody >
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户账号
					</th>
					<th scope="col" >
						企业全称
					</th>
					<th scope="col" >
						用户类型
					</th>
					<th scope="col">
						营业执照
					</th>
					<th scope="col">
						税务登记证
					</th>
					<th scope="col">
						组织机构代码
					</th>
					<th scope="col">
						跟踪审核
					</th>
					<th scope="col">
						查看
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
				
				<tr class="gvItem">
					<td>
					<s:property value="#s.count+#counts"/>
					</td>
					<td align="center">
							<a href="queryPerUserCreditAction.mht?userId=${id }&userType=${userType}" target="_blank" style="color: blue;">${usrename}</a>
					</td>
					<td>
						<s:if test="#bean.orgName!=null && #bean.orgName!=''">${orgName}</s:if>
						<s:else>--</s:else>
					</td>
					<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
					</td>
					<td>
						<s:if test="#bean.tmLicenseStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmLicenseStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmLicenseStatus==3">
						通过
						</s:elseif>
						<s:else>
					   	  未申请
						</s:else>
					</td>
					<td>
						<s:if test="#bean.tmTaxRegStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmTaxRegStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmTaxRegStatus==3">
						通过
						</s:elseif>
						<s:else>
					          未申请
						</s:else>
					</td>
					<td>
						<s:if test="#bean.tmOrgCodeStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmOrgCodeStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmOrgCodeStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
					</td>
					<td>
						<s:if test="#bean.serviceManName!=null">
						${serviceManName}
						</s:if>
						<s:else>
						未分配
						</s:else>
					</td>
					<td>
						<a href="queryPerUserPicturMsginitindex.mht?userId=${id }&userType=${userType}" style="color: blue;">	查看</a>
					</td>
				</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}" ></shove:page>
