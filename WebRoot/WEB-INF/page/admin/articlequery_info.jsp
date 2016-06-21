<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th scope="col">
						选中
					</th>
					<th scope="col">
						序号
					</th>
					<th scope="col">
					  动态类型
					</th>
					<th scope="col">
					   新闻标题
					</th>								
					<th scope="col">
						来源
					</th>
					<th scope="col">
						新闻链接地址
					</th>
					<th scope="col">
                        发布时间
                    </th>
					<th scope="col">
						状态
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						    <td>
								<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td align="center">
								<s:if test="#bean.newsType==1">
								  		三好资本动态
								</s:if>
								<s:elseif test="#bean.newsType==2">
								  		互联网金融
								</s:elseif>
								<s:else>三好资本问答</s:else>
							</td>
							<td align="left">
								${bean.title}
							</td>
							<td>
								${bean.source}
							</td>
							<td  align="left">
								${bean.url}
							</td>
							<td  align="left">
							<fmt:formatDate value="${bean.publishTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
							<td>
								<s:if test="#bean.state==1">
								  		隐藏
								</s:if>
								<s:else>开启</s:else>
							</td>
								
							<td>
								
								&nbsp;&nbsp;
								<a href="updateArticleInit.mht?id=${bean.id}" >
								    编辑
								</a>
								&nbsp;&nbsp;
								<a href="javascript:del(${bean.id})" >
								删除
								</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDel" onclick="dels();" type="button"
						value="删除选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
