<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript">
	$(function() {
		$("#excel")
				.click(
						function() {
							$("#excel").attr("disabled", true);
							param["pageBean.pageNum"] = 1;
							param["paramMap.userName"] = $("#userName").val();
			                 param["paramMap.department"] = $("#department").val();
			                 param["paramMap.hasRecharge"] = $("#hasRecharge").val();
			                 param["paramMap.hasInvest"] = $("#hasInvest").val();
			                 param["paramMap.createTimeStart"] = $("#createTimeStart").val();
			                 param["paramMap.createTimeEnd"] = $("#createTimeEnd").val();
							window.location.href = "exportEmployeeRefInfo.mht?pageBean.pageNum=1000 &&paramMap.userName="
									+ $("#userName").val()
									+ "&paramMap.department="
									+ $("#department").val()
									+ "&paramMap.hasRecharge="
                                    + $("#hasRecharge").val()
                                    + "&paramMap.hasInvest="
                                    + $("#hasInvest").val()
                                    + "&paramMap.createTimeStart="
                                    + $("#createTimeStart").val()
                                    + "&paramMap.createTimeEnd="
                                    + $("#createTimeEnd").val();
							setTimeout("test()", 4000);
						});
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th  style="width: 5%"scope="col">序号</th>
				<th style="width: 10%" scope="col">会员用户名</th>
				<th style="width: 10%" scope="col">会员真实姓名</th>
				<th style="width: 10%" scope="col">会员注册时间</th>
				<th style="width: 5%" scope="col">是否有充值</th>
				<th style="width: 5%" scope="col">是否有投资</th>
				<th style="width:10%" scope="col">推荐人用户名</th>
				<th style="width: 10%" scope="col">推荐人姓名</th>
				<th style="width:10%" scope="col">推荐人部门</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="9">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${s.index+1+pageBean.pageSize*(pageBean.pageNum-1) }</td>
						<td>${username }</td>
						<td>${realname }</td>
						<td>${createTime }</td>
						<td>${hasRecharge }</td>
						<td>${hasInvest }</td>
						<td>${recommendUserName }</td>
						<td>${recommendUserRealName }</td>
						<td>${selectName }</td>
					</tr>
				</s:iterator>
			</s:else>
			
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}" 
></shove:page>
