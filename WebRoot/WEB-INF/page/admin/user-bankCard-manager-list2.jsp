<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div> 
		<table id="help" style=" color: #333333;width:100%"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th style="width: 85px;" scope="col">
                       选中
                    </th>
					<th style="width: 30px;" scope="col">
						序号
					</th>
					<th style="width: 120px;" scope="col">
						用户名
					</th>
					<th style="width: 60px;" scope="col">
						用户类型
					</th>
					<th style="width: 100px;" scope="col">
						开户名称
					</th>
					<th style="width: 100px;" scope="col">
						开户行
					</th>
					<th style="width: 100px;" scope="col">
						支行
					</th>
					<th style="width: 120px;" scope="col">
						卡号
					</th>
					<th style="width: 30px;" scope="col">
                        省
                    </th>
                    <th style="width: 30px;" scope="col">
                        市
                    </th>
					<th style="width: 120px;" scope="col">
						提交时间
					</th>
					<th style="width: 40px;" scope="col">
						状态
					</th>
					<th style="width: 80px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="14">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						    <td align="center">
                                <input  type="checkbox"  name="b_ids"  class="auditing"  value="${bankId }"/>
                            </td>
							<td align="center">
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
								<s:if test="#bean.userType==1">个人</s:if>
								<s:if test="#bean.userType==2">企业</s:if>
							</td>
							<td>
								${bean.realName}
							</td>
							<td>
								${bean.bankName}
							</td>
							<td>
								${bean.branchBankName}
							</td>
							<td>
								${bean.cardNo}
							</td>
							<td>
                                ${bean.province}
                            </td>
                            <td>
                                ${bean.city}
                            </td>
							<td>
								<!--  ${bean.modifiedTime}-->
								<s:date name="#bean.commitTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<s:if test="#bean.isDelete==2">
								<s:if test="#bean.cardStatus==2">
								  <td>审核中</td>
								   <td> <a href="queryBankCardInfo.mht?bankId=${bean.id }" target="main">
									审核
									</a></td>
									&nbsp;&nbsp;
								</s:if>
								<s:elseif test="#bean.cardStatus==3">
								 <td><a href="#" onclick="changeCardes('${bean.username}','${bean.bankId}',1,'suc_opr')">失败</a></td>
									<td>--</td>
								</s:elseif>
								<s:else>
								 <td>
								 <a href="#" onclick="changeCardes('${bean.username}','${bean.bankId}',3,'err_opr')">成功</a></td>
									<td><a href="javascript:show(${bean.id });" target="main">
									查看
									</a></td>
								</s:else>
							</s:if>
							<s:else>
								<td>已删除</td>
									<td><a href="javascript:show(${bean.id });" target="main">
									查看
									</a></td>
							</s:else>
						</tr>
					</s:iterator>
					<tr class="gvItem">
                          <td>全选</td>
                          <td><input type="checkbox"  id="all"  onclick="checkAll(this)" /></td>
                          <!--  <td colspan="5">审核意见：<input type="text"  id="remark" /></td>-->
                          <td colspan="11" align="right"><input  type="button"  onclick="allAuditing()" value="批量审核" /></td>
                        </tr>
				</s:else>
			</tbody>
		</table>
	

	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>

<script type="text/javascript">
function changeCardes(userName,bankId,status,remark){
	var statusStr = "失败";
	if (status == 1){
		statusStr = "成功";
	}
	if (confirm("确定要置为"+statusStr+"吗？")){
		param["paramMap.username"]= userName;
		param["paramMap.bankId"]= bankId;
		param["paramMap.status"]= status;
		param["paramMap.remark"]= remark;
		$.dispatchPost("updateUserBankInfo.mht",param,initCallBacke);
	}
}
function initCallBacke(){
	alert("完成状态设置！");
	window.location.reload();
}
</script>