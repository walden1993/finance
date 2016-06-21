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
			                 param["paramMap.logincount"] = $("#logincount").val();
			                 param["paramMap.loginTimeStart"] = $("#createTimeStart").val();
			                 param["paramMap.loginTimeEnd"] = $("#createTimeEnd").val();
							window.location.href = "exportEmployeeLivInfo.mht?pageBean.pageNum=1000 &&paramMap.userName="
									+ $("#userName").val()
									+ "&paramMap.realName="
									+ $("#realName").val()
									+ "&paramMap.department="
                                    + $("#department").val()
                                    + "&paramMap.logincount="
                                    + $("#logincount").val()
                                    + "&paramMap.loginTimeStart="
                                    + $("#createTimeStart").val()
                                    + "&paramMap.loginTimeEnd="
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
				<th style="width: 8%" scope="col">员工用户名</th>
				<th style="width: 10%" scope="col">员工真实姓名</th>
				<th style="width: 5%" scope="col">部门</th>
				<th style="width: 7%" scope="col">总推荐人数</th>
				<th style="width: 6%" scope="col">充值人数</th>
				<th style="width:7%" scope="col">充值活跃度</th>
				<th style="width: 6%" scope="col">投标人数</th>
				<th style="width:7%" scope="col">投标活跃度</th>
				<th style="width: 7%" scope="col">无动作人数</th>
				<th style="width: 5%" scope="col">静止度</th>
				<th style="width: 16%" scope="col">某时段登陆<s:property value="#request.logincountI_in"  default="10"  />次以上的人数</th>
				<th style="width: 8%" scope="col">登陆活跃度</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">暂无数据</td>
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
						<td>${rcdcount }</td>
						<td>${rechargecount }</td>
						<td>${rechargeActive }</td>
						<td>${investcount }</td>
						<td>${investActive }</td>
						<td>${staticscount }</td>
						<td>${statics }</td>
						<td>${logincount }</td>
						<td>${loginActive }</td>
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
