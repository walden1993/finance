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
				                 param["paramMap.realName"] = $("#realName").val();
				                 param["paramMap.department"] = $("#department").val();
				                 param["paramMap.timeStart"] = $("#timeStart").val();
				                 param["paramMap.investStartTime"] = $("#investStartTime").val();
				                 param["paramMap.investEndTime"] = $("#investEndTime").val();
							window.location.href = "exportEmployeePerformanceInfo.mht?pageBean.pageNum=1000 &&paramMap.userName="
									+ $("#userName").val()
									+ "&paramMap.realName="
									+ $("#realName").val()
									+ "&paramMap.department="
                                    + $("#department").val()
                                    + "&paramMap.timeStart="
                                    + $("#timeStart").val()
                                    + "&paramMap.investStartTime="
                                    + $("#investStartTime").val()
                                    + "&paramMap.investEndTime="
                                    + $("#investEndTime").val();
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
				<th  scope="col">序号</th>
				<th  scope="col">员工用户名</th>
				<th  scope="col">员工真实姓名</th>
				<th  scope="col">部门</th>
				<th  scope="col">账户可用余额</th>
				<th  scope="col">账户冻结金额</th>
                <th scope="col">账户总金额</th>
                <th  scope="col">投资总额</th>
                <th scope="col">被推荐人账户冻结金额</th>
                <th scope="col">被推荐人账户可用余额</th>
                <th scope="col">被推荐人账户总金额</th>
                <th scope="col">被推荐人投资总额</th>
				<th  scope="col">总推荐人数</th>
                <th scope="col">充值人数</th>
                <th  scope="col">充值活跃度</th>
                <th  scope="col">投标人数</th>
                <th  scope="col">投标活跃度</th>
                <th scope="col">无动作人数</th>
                <th  scope="col">静止度</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="19">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${s.index+1+pageBean.pageSize*(pageBean.pageNum-1) }</td>
						<td>${username }</td>
						<td>${realName }</td>
						<td>${selectName }</td>
						<td>${myAmount }</td>
						<td>${myFreeAmount }</td>
						<td>${mySumAmount }</td>
						<td>${myInvestAmount }</td>
						<td>${friendFreezeAmount }</td>
						<td>${friendAmount }</td>
						<td>${friendSumAmount }</td>
						<td>${friendInvestAmount }</td>
						<td>${rcdcount }</td>
                        <td>${rechargecount }</td>
                        <td>${rechargeActive }</td>
                        <td>${investcount }</td>
                        <td>${investActive }</td>
                        <td>${staticscount }</td>
                        <td>${statics }</td>
					</tr>
				</s:iterator>
			</s:else>
			
		</tbody>
	</table>
</div>
<script type="text/javascript">
	function leavework(id) {
		if(!window.confirm("确定要离职吗?")){
			return
		}
		$.post("leaveWork.mht?id=" + id,"",function(data){
			if(data==1){
				alert("操作成功!");
				window.location.reload();
			}else{
				alert("操作失败!");
			}
		})
	};
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}" 
></shove:page>
