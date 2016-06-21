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
						渠道简称
					</th>
					<th style="width: 80px;" scope="col">
						类型
					</th>
					<th style="width: 80px;" scope="col">
						系统编码
					</th>
					<th style="width: 80px;" scope="col">
						渠道编号
					</th>
					<th style="width: 120px;" scope="col">
						操作
					</th>
					
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="6">暂无数据</td>
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
							${channelDesc}
						</td>
						<td>
							<s:if test="#bean.ctype==1">硬广</s:if>
							<s:elseif test="#bean.ctype==2">网盟</s:elseif>
							<s:elseif test="#bean.ctype==3">搜索引擎</s:elseif>
							<s:elseif test="#bean.ctype==4">CPS</s:elseif>
							<s:elseif test="#bean.ctype==5">社会化媒体</s:elseif>
						</td>
						<td>
							${sysCode}
						</td>
						<td>
							${channelCode}
						</td>
						
					<td>
						 <a href="#.mht?id=${id}">编辑</a> 
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
