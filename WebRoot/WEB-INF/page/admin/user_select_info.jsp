<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div><span class="f66">待审核的用户数量为<b style="color: red">&nbsp;${usercount }&nbsp;</b>个。总的待审核的认证数量为<b style="color: red">&nbsp;${byNamemap }&nbsp;</b>个</span></div>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						序号
					</th>
					<th style="width: 100px;" scope="col">
						用户账号
					</th>
					<th style="width: 100px;" scope="col">
						用户类型
					</th>
					<th style="width: 100px;" scope="col">
						真实姓名
					</th>
					<th style="width: 100px;" scope="col">
						企业全称
					</th>
					<th style="width: 100px;" scope="col">
						待审核认证
					</th>
					<th style="width: 100px;" scope="col">
						成功认证
					</th>
					<th style="width: 100px;" scope="col">
						失败认证
					</th>
					<th style="width: 100px;" scope="col">
						未申请认证	
					</th>
					<th style="width: 100px;" scope="col">
						跟踪审核
					</th>
						<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="11">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
				
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#counts"/>
						</td>
						<td>
							<a href="queryPerUserCreditAction.mht?userId=${id}&userType=${userType}">
								${username}
							</a>	
						</td>
						<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
						</td>
						<td>
							<s:if test="#bean.realName!=null && #bean.realName!=''">${realName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
							<s:if test="#bean.orgName!=null && #bean.orgName!=''">${orgName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
                      		${wait}
						</td>
						<td>
							${success}
						</td>
						<td>
							${fail}
						</td>
						<td>
							<s:if test="#bean.nosh!=null && #bean.nosh!=''">${nosh}</s:if>
							<s:elseif test="#bean.userType==1">11</s:elseif>
							<s:else>15</s:else>
						</td>
						<td>
					 		${tausername }
						</td>
						<td>
					  		<a href="queryselect.mht?userId=${id}&userType=${userType}">查看</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
