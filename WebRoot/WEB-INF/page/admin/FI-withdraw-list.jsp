<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	
	<div> 
		<table id="help" style=" color: #333333;width:100%"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th scope="col">
						<input type="checkbox" id="checkAll" onclick="checkAll(this)" />
					</th>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户名
					</th>
					<th scope="col">
						用户类型
					</th>
					<th scope="col">
						开户名称
					</th>
					<th scope="col">
						提现账号
					</th>
					<th scope="col">
						提现银行
					</th>
					<th scope="col">
						支  行
					</th>
					<th scope="col">
						提现总额
					</th>
					<th scope="col">
						到账金额
					</th>
					<th scope="col">
						手续费
					</th>
					<th scope="col">
						提现时间
					</th>
					<th scope="col">
						状态
					</th>
					<s:if test="#request.status==1"> <th scope="col"> 初审时间 </th> </s:if>
					<s:elseif test="#request.status==2"> <th scope="col"> 复审时间 </th> </s:elseif>
					<s:elseif test="#request.status==3"> <th scope="col"> 初审时间 </th>  <th scope="col"> 复审时间 </th></s:elseif>
					<s:elseif test="#request.status==4"> <th scope="col"> 初审时间 </th> </s:elseif>
					<s:elseif test="#request.status==5"> <th scope="col"> 初审时间 </th> <th scope="col"> 复审时间 </th></s:elseif>
					<s:else>
					<th scope="col"> 初审时间 </th>
					<th scope="col"> 复审时间 </th>
					</s:else>
					<th scope="col">
						操作
					</th>
					<th style="width: 80px;" scope="col">
						推荐人
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="16">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
							<input type="checkbox" name="auditOne" value="${bean.id }"  />
							</td>
							<td align="center" >
							<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.name}
							</td>
							<td>
								<s:if test="#bean.userType==1">个人</s:if>
								<s:if test="#bean.userType==2">企业</s:if>
							</td>
							<td>
								${bean.realName}
							</td>
							<td>
							  	${bean.acount}
							</td>
							<td>
								${bean.bankName}
							</td>
							<td>
								${bean.branchBankName}
							</td>		
							<td>
								${bean.sum}
							</td>
							<td>
								${bean.realMoney}
							</td>
							<td>
								${bean.poundage }
							</td>
							<td>
							  <s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" ></s:date>
							</td>
							
							  <td>
							  <s:if test="#bean.status==1">审核中</s:if>
							  <s:elseif test="#bean.status==2">已提现</s:elseif>
							  <s:elseif test="#bean.status==3">取消</s:elseif>
							  <s:elseif test="#bean.status==4">转账中</s:elseif>
							  <s:elseif test="#bean.status==5">失败</s:elseif>
							  </td>
							<s:if test="#request.status==1"> <td> <s:date name="#bean.firstCheck" format="yyyy-MM-dd HH:mm:ss" /> </td> </s:if>
					<s:elseif test="#request.status==2"> <td> <s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" /></td> </s:elseif>
					<s:elseif test="#request.status==3"> <td> <s:date name="#bean.firstCheck" format="yyyy-MM-dd HH:mm:ss" /> </td>  <td> <s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" /></td></s:elseif>
					<s:elseif test="#request.status==4"> <td> <s:date name="#bean.firstCheck" format="yyyy-MM-dd HH:mm:ss" /> </td> </s:elseif>
					<s:elseif test="#request.status==5"> <td> <s:date name="#bean.firstCheck" format="yyyy-MM-dd HH:mm:ss" /></td> <td> <s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" /> </td></s:elseif>
					<s:else>
					<td> <s:date name="#bean.firstCheck" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td> <s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
					</s:else>
							
							
							
							
								<td>
								 <s:if test="#bean.status==1">
							     <a href="javascript:withdraw_check(${bean.id });">审核</a>
							     </s:if>
								 <s:elseif test="#bean.status==4">
							     <a href="javascript:withdraw_trans(${bean.id });"> 转账</a>
							     </s:elseif>
							     <s:else>
							      <a href="javascript:withdraw_show(${bean.id });">查看</a>
							     </s:else>
								</td>
							<td>
							 ${redName }
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="2">总计：</td>
						<td colspan="2">提现总额：${map.sums }</td>
						<td colspan="2">到帐金额：${map.realMoneys }</td>
						<td colspan="2">手续费：${map.poundages }</td>
						<td colspan="2"><input type="button" value="批量审核通过" onclick="batchAudit( )"/></td>
					</tr>
				  </s:else>
				  <tr class="gvItem"><td colspan="16" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>

</div>
		
	<script type="text/javascript" src="../css/admin/popom.js"></script>
	<script>
	  function withdraw_check(wid){
			    var url = "queryWithdrawInfo.mht?wid="+wid;
               ShowIframe("提现审核",url,600,600);
			}
			
			function withdraw_trans(wid){
			    var url = "queryWithdrawTransInfo.mht?wid="+wid;
               ShowIframe("转账审核",url,600,600);
			}
			
			function withdraw_show(wid){
			    var url = "queryWithdrawShowInfo.mht?wid="+wid;
               ShowIframe("提现查看",url,600,600);
			}

	function checkAll(e){
		if(e.checked){
			$("input[name='auditOne']").attr("checked","checked");
		}else{
			$("input[name='auditOne']").removeAttr("checked");
		}
	}
	function batchAudit( ){
		var ids="";
		var audits = $("input[name='auditOne']");
		for (var i=0; i < audits.length; i++){
			
			var aone = audits[i];
			if (aone.checked){
				var av = aone.value;
				ids += av+",";
			}
		}
		if (ids.length > 1){
			ids=ids.substring(0,ids.length-1);
		}
		if (ids.length == 0){
			alert("请选择记录");
			return;
		}

		if(!confirm("确定批量提交吗？")){
			return;
		}

		
		param["paramMap.ids"] = ids;
		$.dispatchPost("updatesWithdrawAudit.mht",param,initCallBack2);
	}

 	function initCallBack2(data){
		alert("批量审核成功");
		location.reload();
	}
	



	</script>