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
					    期号
					</th>
					<th style="width: 80px;" scope="col">
					   抽奖主题
					</th>	
					<th style="width: 80px;" scope="col">
					   开始时间
					</th>
					<th style="width: 180px;" scope="col">
					   结束时间
					</th>									
					<th style="width: 100px;" scope="col">
						创建时间
					</th>
					<th style="width: 100px;" scope="col">
                        红包剩余个数
                    </th>
					<th style="width: 100px;" scope="col">
						操作
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
								${(pageBean.pageNum-1)*pageBean.pageSize+s.index+1 }
							</td>
							<td>
								第${bean.termNo}期
							</td>
							<td>
								${bean.luckTheme}
							</td>
							<td>
								${bean.startTime}
							</td>
							<td>
							  ${bean.endTime}
							</td>
							<td>
								${bean.createTime}
							</td>
							<td>
                                ${bean.awardNo}
                            </td>
							<td>
								<a href="javascript:awardEdit(${bean.id})" >编辑</a>&nbsp;&nbsp;<a href="javascript:awardParamUser(${bean.termNo})"  onclick="">抽奖明细</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
