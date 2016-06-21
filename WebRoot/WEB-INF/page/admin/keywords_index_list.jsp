<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						关键字
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td align="center">
							${bean.id}
						</td>
						<td>
							${bean.keyword}
						</td>
						<td>
							<s:if test="#bean.isuse == 0"><a style="cursor: pointer;" href="updateKeywordsState.mht?id=${bean.id }&isuse=1">启用</a></s:if>
							<s:if test="#bean.isuse == 1"><a style="cursor: pointer;" href="updateKeywordsState.mht?id=${bean.id }&isuse=0">禁用</a></s:if>
						</td>
						</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
