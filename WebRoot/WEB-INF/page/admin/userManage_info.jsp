<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 100px;" scope="col">
					序号
				</th>
				<th style="width: 100px;" scope="col">
					用户名
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
					信用积分
				</th>
				<th style="width: 100px;" scope="col">
					会员积分
				</th>
				<th style="width: 100px;" scope="col">
					投资信息
				</th>
				<th style="width: 100px;" scope="col">
					基本信息
				</th>
				<th style="width: 100px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#counts" />
						</td>
						<td>
							${username }
						</td>
						<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
						</td>
						<td>
							<s:if test="#bean.realName!=null">
							${realName }
							 </s:if>
							<s:else>
							  --
							</s:else>
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
							${creditrating }
						</td>
						<td>
							${rating }
						</td>
						<td>
							<s:if test="#bean.totalAmount!=null">
							投资总额为 ${totalAmount }
							 </s:if>
							<s:else>
							  无投资信息
							 </s:else>
						</td>
						<td>
							<s:if test="#bean.tpauditStatus!=null">基本信息完整</s:if>
							<s:if test="#bean.tpauditStatus==null">基本信息未填写</s:if>
						</td>
						<td>
							<a href="queryUserManageBaseInfoinner.mht?i=${id }&userType=${userType}">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
