<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						用户名
					</th>
					<th style="width: 100px;" scope="col">
						用户类型
					</th>
					<th style="width: 100px;" scope="col" >
						真实姓名
					</th>
					<th style="width: 100px;" scope="col">
						企业全称
					</th>
					<th  scope="col">
						申请vip时间
					</th>
						<th  scope="col">
						到期vip时间
					</th>
						<th style="width: 100px;" scope="col">
						付费状态
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
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
								${username }
							</td>
							<td>
								<s:if test="#bean.userType==1">个人</s:if>
								<s:if test="#bean.userType==2">企业</s:if>
							</td>
							<td>
								${realName }
							</td>
							<td>
								<s:if test="#bean.orgName!=null">
									${orgName }
							 	</s:if>
								<s:else>
							  		--
							 	</s:else>
							</td>
							<td>
								<s:date name="#bean.vipCreateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
								<td>
								<s:date name="#bean.vip" format="yyyy-MM-dd HH:mm:ss" />
							</td>
								<td>
								<s:if test="#bean.feeStatus==1">未扣除</s:if>
								<s:if test="#bean.feeStatus==2">已付费</s:if>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<script>

</script>

	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>