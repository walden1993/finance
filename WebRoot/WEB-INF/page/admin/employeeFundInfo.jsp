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
							 param["paramMap.userName"] = $("#userName").val();
			                 param["paramMap.realName"] = $("#realName").val();
			                 param["paramMap.department"] = $("#department").val();
			                 param["paramMap.phone"] = $("#phone").val();
			                 param["paramMap.acountBalanceTime"] = $("#acountBalanceTime").val();
			                 param["paramMap.investTimeStart"] = $("#investTimeStart").val();
			                 param["paramMap.investTimeEnd"] = $("#investTimeEnd").val();
			                 param["paramMap.rechargeTimeStart"] = $("#rechargeTimeStart").val();
			                 param["paramMap.rechargeTimeEnd"] = $("#rechargeTimeEnd").val();
							window.location.href = "exportEmployeeFundInfo.mht?pageBean.pageNum=1000 &&paramMap.userName="
									+ $("#userName").val()
									+ "&paramMap.realName="
									+ $("#realName").val()
									+ "&paramMap.department="
                                    + $("#department").val()
									+ "&paramMap.phone="
                                    + $("#phone").val()
                                    + "&paramMap.acountBalanceTime="
                                    + $("#acountBalanceTime").val()
                                    + "&paramMap.investTimeStart="
                                    + $("#investTimeStart").val()
                                    + "&paramMap.investTimeEnd="
                                    + $("#investTimeEnd").val()  + "&paramMap.rechargeTimeStart="
                                    + $("#rechargeTimeStart").val()  + "&paramMap.rechargeTimeEnd="
                                    + $("#rechargeTimeEnd").val();
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
				<th style="width: 5%" scope="col">用户名</th>
				<th style="width: 10%" scope="col">真实姓名</th>
				<th style="width: 5%" scope="col">部门</th>
				<th style="width: 5%" scope="col">职位</th>
				<th style="width: 10%" scope="col">注册时间</th>
				<th style="width:10%" scope="col">账户余额</th>
				<th style="width: 10%" scope="col">充值金额</th>
				<th style="width: 12%" scope="col">投资金额</th>
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
						<td>${realName }</td>
						<td>${selectName }</td>
						<td>${job }</td>
						<td>${createTime }</td>
						<td>${acoumtMoney }</td>
						<td>${rechargeMoney }</td>
						<td>${investMoney }</td>
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
