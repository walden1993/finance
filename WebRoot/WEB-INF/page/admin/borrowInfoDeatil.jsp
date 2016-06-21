<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<s:if test="#request.map!=null">
    <div class="pageDivClass">借款总金额：${map.a } 元&nbsp;&nbsp;借款总利息：${map.b } 元</div>
</s:if>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						标的名称
					</th>
					<th style="width: 150px;" scope="col">
						借款人用户名
					</th>
					<th style="width: 200px;" scope="col">
						借款人真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						借款类型
					</th>
					<th style="width: 150px;" scope="col">
						借款金额
					</th>
					<th style="width: 150px;" scope="col">
						年利率
					</th>
					<th style="width: 150px;" scope="col">
						借款期限
					</th>
					<th style="width: 150px;" scope="col">
						发标日期
					</th>
					<th style="width: 150px;" scope="col">
						起息日期
					</th>
					<th style="width: 150px;" scope="col">
                        标的到期日
                    </th>
                    <th style="width: 150px;" scope="col">
                        管理费
                    </th>
                    <th style="width: 150px;" scope="col">
                        应收回款
                    </th>
                    <th style="width: 150px;" scope="col">
                        应付利息
                    </th>
                    <th style="width: 150px;" scope="col">
                        已还本金
                    </th>
                    <th style="width: 150px;" scope="col">
                       已还利息
                    </th>
                    <th style="width: 150px;" scope="col">
                       未还本金
                    </th>
                    <th style="width: 150px;" scope="col">
                       未还利息
                    </th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="18">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${s.index+1+pageBean.pageSize*(pageBean.pageNum-1) }
						</td>
						<td align="center">
							${bean.a2}
						</td>
						<td align="center">
							${bean.a3}
						</td>
						<td align="center">
							${bean.a4}
						</td>
						<td align="center">
							${bean.a5}
						</td>
						<td align="center">
							${bean.a7}
						</td>
						<td align="center">
							${bean.a8}
						</td>
						<td align="center">
							${bean.a9}
						</td>
						<td align="center">
                            ${bean.a10}
                        </td>
                        <td align="center">
                            ${bean.a11}
                        </td>
                        <td align="center">
                             ${bean.a12}
                        </td>
                        <td align="center">
                            ${bean.a13}
                        </td>
                        <td align="center">
                            ${bean.a14}
                        </td>
                        <td align="center">
                            ${bean.a15}
                        </td>
                        <td align="center">
                            ${bean.a16}
                        </td>
                        <td align="center">
                            ${bean.a17}
                        </td>
                        <td align="center">
                            ${bean.a18}
                        </td>
                        <td align="center">
                            ${bean.a19}
                        </td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>