<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
投资总金额：${investAmtAll} 元  &nbsp;&nbsp;&nbsp;&nbsp;
充值总金额：${rechargeMoneyAll} 元&nbsp;&nbsp;&nbsp;&nbsp;
可用余额总额：${usableSumAll} 元
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						会员用户名
					</th>
					<th style="width: 150px;" scope="col">
						会员真实姓名
					</th>
					<th style="width: 150px;" scope="col">
						会员手机号
					</th>
					<th style="width: 150px;" scope="col">
						会员邮箱
					</th>
					<th style="width: 150px;" scope="col">
						是否公司员工
					</th>
					<th style="width: 150px;" scope="col">
						注册时间
					</th>
					<th style="width: 200px;" scope="col">
						投资金额
					</th>
					<th style="width: 150px;" scope="col">
						充值金额
					</th>
					<th style="width: 150px;" scope="col">
						可用余额
					</th>
					<th style="width: 150px;" scope="col">
						时间点可用余额
					</th>
					<th style="width: 150px;" scope="col">
						推荐人
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="12">暂无数据</td>
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
							${username}
						</td>
						<td>
							${realName}
						</td>
						<td>
						${mobilePhone}
						</td>
						<td>
						${email} 
						</td>
						<td>
						<s:if test="isEmployee==0">
						非员工
						</s:if>
						<s:else>
						员工
						</s:else>
						</td>
						<td>
						${createTime}
						</td>
						<td>
						    ${investAmt}
						</td>
						<td>
						${rechargeMoney}
						</td>
						<td>
						${usableSum}
						</td>
						<td>
						${usableSumTime}
						</td>
						<td>
						${redName}
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
