<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
 $(function(){
   $("#excel").click(function(){  
   window.location.href=encodeURI("exportAwardParamUserList.mht?paramMap.termNo="+$("#termNo").val()+"&&paramMap.username="+$("#username").val()+"&&paramMap.status="+$("#status").val()+"&&paramMap.paramId="+$("#paramId").val()+"&&paramMap.sendAward="+$("#sendAward").val()+"&&paramMap.startTime="+$("#startTime").val()+"&&paramMap.endTime="+$("#endTime").val());
   });
 });
</script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				 <th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
                        期数
                    </th>
					<th style="width: 200px;" scope="col">
						用户名
					</th>
					<th style="width: 200px;" scope="col">
                       中奖状态
                    </th>
					<th style="width: 150px;" scope="col">
						奖品名称
					</th>
					<th style="width: 150px;" scope="col">
						抽奖时间
					</th>
					<th style="width: 100px;" scope="col">
						派奖状态
					</th>
					<th style="width: 80px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						  <td>
								<s:if test="#bean.sendAward==0">
								    <input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
                                    value="${bean.id}" name="cb_ids" />
								</s:if><s:else>--</s:else>
							</td>
							<td align="center" style="width:100px;">
								${(pageBean.pageNum-1)*pageBean.pageSize+s.index+1 }
							</td>
							<td>
                                ${bean.termNo}
                            </td>
							<td>
								${bean.username}
							</td>
							<td>
								${bean.statusStr}
							</td>
							<td>
								${bean.awardName}
							</td>
							<td>
                                ${bean.createTime}
                            </td>
                            <td>
                                ${bean.sendAwardStr}
                            </td>
                            <td>
                                <s:if test="#bean.sendAward==0"><a href="javascript:sendAward(${bean.id})">派奖</a></s:if><s:else>--</s:else>
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
				<input id="chkAll" onclick="checkAll(this); " value="checkbox" name="chkAll" type="checkbox">全选&nbsp;&nbsp;
					<font size="2">${pageBean.totalNum}条记录</font>
				</td>
				<td class="blue12" style="padding-left: 8px" align="right">
                    <font size="2">
                        <input type="button"  onclick="sendAwards()"   value="选中批量派奖"/>
                    </font>
                </td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
