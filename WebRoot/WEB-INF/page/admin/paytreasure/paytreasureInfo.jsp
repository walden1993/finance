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
							 param["paramMap.username"] = $("#userName").val();
							 param["paramMap.realName"] = $("#realName").val();
							 param["paramMap.cellPhone"] = $("#phone").val();
							window.location.href = "paytreasureExcel.mht?paramMap.username="
									+ $("#userName").val()
									+ "&paramMap.realName="
									+ $("#realName").val()
									+ "&paramMap.cellPhone="
                                    + $("#phone").val();
							setTimeout("test()", 4000);
						});
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
</script>
<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css">
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class="gvHeader">
				<th  style="width: 5%"scope="col">序号</th>
				<th style="width: 5%" scope="col">用户名</th>
				<th style="width: 5%" scope="col">真实姓名</th>
				<th style="width: 10%" scope="col">手机号码</th>
				<th style="width: 5%" scope="col">涨薪宝余额</th>
				<th style="width: 5%" scope="col">涨薪宝总收益</th>
				<th style="width: 15%" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="7">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${s.index+1+pageBean.pageSize*(pageBean.pageNum-1) }</td>
						<td>${username }</td>
						<td>${realName }</td>
						<td>${cellPhone }</td>
						<td>${investamount }</td>
						<td>${allprofit }</td>
						<td><a href="javascript:profitRecord('${id}')">查看收益记录</a>&nbsp;<a href="javascript:payInvestRecord('${id}')">资金记录</a></td>
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
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script>

     function payInvestRecord(id){
   	  $.jBox("iframe:payInvestRecord.mht?id="+id, {
   	        title: "资金操作记录",
   	        width: 800,
   	        height: 660,
   	        buttons: { '关闭': true }
   	    });
     }
	 
	 function profitRecord(id){
   	  $.jBox("iframe:profitRecord.mht?id="+id, {
   	        title: "收益记录",
   	        width: 800,
   	        height: 660,
   	        buttons: { '关闭': true }
   	    });
     }
</script>