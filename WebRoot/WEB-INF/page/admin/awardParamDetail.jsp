<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 80px;" scope="col">
					    名称
					</th>
					<th style="width: 80px;" scope="col">
					   图片
					</th>	
					<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="4">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">						    
							<td>
								${(pageBean.pageNum-1)*20+s.index+1 }
							</td>
							<td>
								${bean.awardName}
							</td>
							<td>
								<img alt="${bean.awardName}" src="../${bean.picAddr}">
							</td>
							<td>
								<a href="javascript:deleteParamDetail(${bean.id})" >删除</a>&nbsp;&nbsp;<a href="javascript:updateParamDetail(${bean.id})"  onclick="">修改</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
