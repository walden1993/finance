<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/include/taglib.jsp"%>
<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 5%;" scope="col">
						序号
					</th>
					<th style="width: 7%;" scope="col">
						标题
					</th>
					<th style="width: 7%;" scope="col">
						体验总金额
					</th>
					<th style="width: 7%;" scope="col">
					              实投金额
					</th>
					<th style="width: 5%;" scope="col">
						利率
					</th>
					<th style="width: 6%;" scope="col">
						期限
					</th>
					<th style="width: 7%;" scope="col">
						筹标期限
					</th>
					<th style="width: 8%;" scope="col">
						应还利息总额 
					</th>
					<th style="width: 8%;" scope="col">
						已还利息总额
					</th>
					<th style="width: 7%;" scope="col">
						待还款日期
					</th>
					<th style="width: 9%;" scope="col">
						已还数/总期数 
					</th>
					<th style="width: 7%;" scope="col">
				       	发布时间	
					</th>
					<th style="width: 4%;" scope="col">
				       	状态	
					</th>
					<th style="width: 12%;" scope="col">
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
						<td>
							<s:property value="#s.count+#counts" />
						</td>
						<td align="center">
							${borrowTitle }
						</td>
						<td>
							${borrowAmount }
						</td>
						<td>
							${hasInvestAmount }
						</td>
						<td >
							${annualRate }%
						</td>
						<td>
							${deadline }个月
						</td>
						<td>
							${raiseTerm }天
						</td>
						<td>
							${interestAmount }
						</td>
						<td>
						<s:if test="#bean.status==0">--</s:if>
                         <s:else>${repaymentedInterestAmount }</s:else>
					    </td>
						<td>
						<s:if test="#bean.status==0">--</s:if>
                         <s:else>${nextRepaymentDate }</s:else>
						</td>
						<td>
							${repaymentedCount }/${deadline }
						</td>
						<td>
							${fn:substring(publishTime,0,10)}&nbsp;${fn:substring(publishTime,10,19)}
						</td>
						<td>
							<s:if test="#bean.status==0">招标中</s:if>
							<s:if test="#bean.status==1">还款中</s:if>
							<s:if test="#bean.status==2">已还完</s:if>
						</td>
					<td>
						<a href="viewExperience.mht?id=${id }">查看</a>
						<s:if test="#bean.status==1">
							<a href="javascript:investTrial('${id}')">还款</a>
						</s:if>
						<s:if test="#bean.status==2">
                            <a href="javascript:investTrial('${id}')">查看还款</a>
                        </s:if>
                        <a href="experienceBorrowInit.mht?id=${id}">体验记录</a>
					</td>	
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
		<p/>
		<script type="text/javascript"  src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
		<script>
		      function investTrial(id){
		    	  $.jBox("iframe:experienceInvestInit.mht?id="+id, {
		    	        title: "体验标还款",
		    	        width: 700,
		    	        height: 260,
		    	        buttons: { '关闭': true }
		    	    });
		      }
		</script>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize}" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
