<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						日期
					</th>
					<th style="width: 150px;" scope="col">
						IP
					</th>
					<th style="width: 150px;" scope="col">
						UV
					</th>
					<th style="width: 150px;" scope="col">
						PV
					</th>
					<th style="width: 150px;" scope="col">
						新注册用户数
					</th>
					<th style="width: 150px;" scope="col">
						累计注册用户数
					</th>
					<th style="width: 150px;" scope="col">
						老用户访问人数
					</th>
					<th style="width: 150px;" scope="col">
						新用户充值人数
					</th>
					<th style="width: 150px;" scope="col">
						有效客户数
					</th>
					<th style="width: 200px;" scope="col">
						新用户充值金额
					</th>
					<th style="width: 150px;" scope="col">
						当日充值金额
					</th>
					<th style="width: 150px;" scope="col">
						新用户投资金额
					</th>
					<th style="width: 150px;" scope="col">
						投标金额
					</th>
					
					<th style="width: 150px;" scope="col">
						提现金额 
					</th>
					<th style="width: 150px;" scope="col">
						提现手续费
					</th>
					<th style="width: 150px;" scope="col">
						发标金额
					</th>
					<th style="width: 150px;" scope="col">
						访问注册转化率
					</th>
					<th style="width: 150px;" scope="col">
						新用户投资转化率
					</th>
					<th style="width: 150px;" scope="col">
						新用户充值转化率
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="18">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td><s:property value="#bean.theDate" /> </td>
						<td> ${ip }</td>
						<td ><s:property value="#bean.uv"  /> </td>
						<td> ${pv }</td>
						<td> ${todayRegNum} </td>
						<td> ${ regNum } </td>
						<td> ${oldVisit } </td>
						<td> ${todayregRecharge } </td>
						<td> ${effectReg } </td>
						<td> ${newUserRecharge } </td>
						<td> ${todayregMoney } </td>
						<td> ${newInvestSum } </td>
						<td> ${ allInvest}  </td>
						<td> ${allWithdraw }</td>
						<td> ${ poundage}  </td>
						<td> ${ borrowAmount}</td>
						<td> ${ uvRate}</td>
						<td> ${ investRate}</td>
						<td>${ rechargeRate} </td>
					</tr>
				</s:iterator>
				</s:else>
				<tr class="gvItem">
						<td>总计： </td>
						<td> ${retMap.ipSum }</td>
						<td >${retMap.uvSum }</td>
						<td>  </td>
						<td> ${retMap.todayRegNumSum }  </td>
						<td>  </td>
						<td>   </td>
						<td> ${retMap.todayregRechargeSum } </td>
						<td> ${retMap.effectRegSum } </td>
						<td> ${retMap.newUserRechargeSum } </td>
						<td> ${retMap.todayregMoneySum } </td>
						<td> </td>
						<td> ${ retMap.allInvestSum}  </td>
						<td> ${ retMap.allRechargeSum}</td>
						<td> </td>
						<td> </td>
						<td> </td>
						<td> </td>
						<td> </td>
					</tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="31" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
