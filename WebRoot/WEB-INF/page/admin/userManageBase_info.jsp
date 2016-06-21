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
							window.location.href = encodeURI("exportusermanageinfo.mht?paramMap.userName="
									+ $("#userName").val()
									+ "&&paramMap.orgName="
									+ $("#orgName").val()
									+ "&&paramMap.realName="
                                    + $("#realName").val()
									+ "&&paramMap.userType="
                                    + $("#userType").val()
                                    + "&&paramMap.createTimeStart="
                                    + $("#createTimeStart").val()
                                    + "&&paramMap.lastLoginTimeStart="
                                    + $("#lastLoginTimeStart").val()
                                    + "&&paramMap.createTimeEnd="
                                    + $("#createTimeEnd").val()
                                    + "&&paramMap.sms="
                                    + $("#sms").val()
                                    + "&&paramMap.lastLoginTimeEnd="
                                    + $("#lastLoginTimeEnd").val()
                                    + "&&paramMap.refereName="
                                    + $("#refereName").val());
							setTimeout("test()", 4000);
						});
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0"
	>
		<tbody>
			<tr class=gvHeader>
				<th  style="width: 1%"scope="col">序号</th>
				<th style="width: 5%" scope="col">用户名</th>
				<th style="width: 5%" scope="col">手机号码</th>
				<th style="width:8%" scope="col">用户类型</th>
				<th style="width: 8%" scope="col">真实姓名</th>
				<th style="width: 8%" scope="col">企业全称</th>
				<th style="width: 8%" scope="col">邮箱</th>
				<!-- 
					<th style="width: 100px;" scope="col">
						QQ号码
					</th>
					<th style="width: 100px;" scope="col">
						手机号码
					</th>
					 -->
				<th style="width: 12%" scope="col">注册时间</th>
				<th style="width: 10%" scope="col">最后登录IP</th>
				<th style="width:12%" scope="col">最后登陆时间</th>
				<th style="width: 10%" scope="col">邀请人用户名</th>
				<th style="width: 12%" scope="col">邀请人真实姓名</th>
				<th style="width: 8%" scope="col">操作</th>
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
						<td><s:property value="#s.count+#counts" /></td>
						<td>${username }</td>
						<td>${cellPhone }</td>
						<td><s:if test="#bean.userType==1">个人</s:if> <s:if
								test="#bean.userType==2"
							>企业</s:if></td>
						<td>${realName }</td>
						<td><s:if test="#bean.orgName!=null">
									${orgName }
							 	</s:if> <s:else>
							  		--
							 	</s:else></td>
						<td>${email }</td>
						<!-- 
							<td>
								${qq }
							</td>
							<td>
								${cellPhone }
							</td>
							 -->
						<td>${createTime }</td>
						<td>${lastIP }</td>
						<td>${lastDate }</td>
						<td>${refferee }</td>
						<td>${ref_realname }${ref_orgname }</td>
						<td><a href="javascript:withdraw_check('${id}','${refferee}');">修改</a> <s:if
								test="#bean.isLoginLimit==2"
							>
								<a href="javascript:isLoginLimit(${id })">解除登录限制</a>
							</s:if></td>
					</tr>
				</s:iterator>
			</s:else>
			<tr>
				<td><input id="excel" type="button" value="导出Excel"	name="excel"/>
				</td>
				<td>总条数：</td>
				<td>${pageBean.totalNum}</td>
			</tr>
		</tbody>
	</table>
</div>
<script>
	function withdraw_check(wid,retUser) {
		var url = "queryUserInfo.mht?id=" + wid+"&retUser="+retUser;
		ShowIframe("基本信息查看/修改", url, 600, 600);
	};
	function isLoginLimit(id) {
		window.location.href="isLoginLimit.mht?id="+id;
		$.post("isLoginLimit.mht", null, function(data) {
			if (data == 1) {
				alert("已解除登录限制");
			} else {
				alert("解除登录限制失败");
			}
			window.location.reload();
		})
	};
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}" 
></shove:page>
