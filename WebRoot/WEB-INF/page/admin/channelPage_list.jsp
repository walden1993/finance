<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 10px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 80px;" scope="col">
						序号
					</th>
					<th style="width: 80px;" scope="col">
						渠道编号
					</th>
					<th style="width: 80px;" scope="col">
						渠道简称
					</th>
					<th style="width: 80px;" scope="col">
						页面名称
					</th>
					<th style="width: 80px;" scope="col">
						广告位个数
					</th>
					<th style="width: 120px;" scope="col">
						操作
					</th>
					
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="acounts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#acounts"/>
						</td>
						<td align="center">
							${channelCode}
						</td>
						<td align="center">
							${channelDesc}
						</td>
						<td>
							${pageName}
						</td>
						<td>
							0--
						</td>
						
					<td>
						 <a href="gotoChannelPageEdit.mht?id=${id}&channelId=${channelId}">编辑</a> 
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
