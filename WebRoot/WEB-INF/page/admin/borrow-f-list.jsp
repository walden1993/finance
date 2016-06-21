<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 10px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 80px;" scope="col">
						序号
					</th>
					<th style="width: 80px;" scope="col">
						用户名
					</th>
					<th style="width: 80px;" scope="col">
						真实姓名
					</th>
					<th style="width: 80px;" scope="col">
						企业全称
					</th>
					<th style="width: 80px;" scope="col">
						用户类型
					</th>
					<th style="width: 120px;" scope="col">
						通过认证数量
					</th>
					<th style="width: 80px;" scope="col">
						标的类型
					</th>
					<th style="width: 80px;" scope="col">
						借款标题
					</th>
					<th style="width: 80px;" scope="col">
						借款金额
					</th>
					<th style="width: 70px;" scope="col">
						利率
					</th>
					<th style="width: 70px;" scope="col">
						期限
					</th>
					<th style="width: 80px;" scope="col">
				    	筹标期限
					</th>
					<th style="width: 60px;" scope="col">
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
				<s:set name="acounts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#acounts"/>
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							<s:if test="#bean.realName!=null&&#bean.realName!=''">${realName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
							<s:if test="#bean.orgName!=null&&#bean.orgName!=''">${orgName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
						</td>
						<td>
						   <s:if test="#bean.counts==null">
							<span class="require-field">0</span>个
							</s:if>
							<s:else>
							 ${counts}个
							</s:else>
						</td>
						<td>
							<s:if test="#bean.borrowWay==1">
							净值借款
							</s:if>
							<s:elseif test="#bean.borrowWay==2">
							秒还借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==3">
							信用借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==4">
							实地考察借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==5">
							机构担保借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==6">
							流转标借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==7">
							债权转让借款
							</s:elseif>
						</td>
						<td>
						<a href="../financeDetail.mht?id=${id}" target="_blank">${borrowTitle}</a>
						</td>
						<td>
								${borrowAmount}
						</td>
						<td>
								${annualRate}%
						</td>
						<td>
								${deadline }
								<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
						</td>
						<td>
								<s:if test="%{#bean.borrowShow==2}">${deadline }个月</s:if>
								<s:else>
								<s:if test="%{#bean.raiseTerm ==0}">无期限</s:if>
								<s:else>
								${raiseTerm }天 </s:else>
								</s:else>
						</td>
						<td>
								初审中
						</td>
						<td>
				
						 <a href="borrowfdetail.mht?id=${id}">查看</a>
						<a href="javascript:rebackAction(${id});">撤消</a>
						
					</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
				<td  colspan="2"><strong>合计项</strong></td>
				<td colspan="4" align="right" >当前页的初审借款金额合计:</td>
				<td  align="center" >￥${fistAuditAmount}</td>	
				<td colspan="3" align="right" >初审借款金额合计:</td>
				<td  colspan="2" >￥
				<s:if test="%{#request.repaymentMap.totalFirstBorrowAmount==''}">0.00</s:if>
				<s:else> <s:property value="#request.repaymentMap.totalFirstBorrowAmount" default="0"/></s:else>
				
				</td>		
				</tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
