<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div class="pub_tb bottom"><i></i>最近三天充值记录</div>
   <div class="tab_b">
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="leve_titbj">                                                    
              <td width="31%">充值单号</td>
              <td width="21%">充值金额(元)</td>
              <td width="13%">充值类型</td>
              <td width="27%">充值时间</td>
              <td width="8%">状态</td>
            </tr>
            
		<s:iterator value="pageBean.page" var="finance" >
		 <tr>
           <td><s:property value="#finance.id" default="0"/> </td>
           <td><s:property value="#finance.rechargeMoney" default="0"/> </td>
           <td><s:property value="#finance.rechargeType" default="充值"/> </td>
           <td><s:date name="#finance.rechargeTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
           <td> <s:property value="#finance.result" default="成功"/> </td>
         </tr>
        </s:iterator>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
		<tr>
           <td colspan="5" align="center">暂无充值记录</td>
         </tr>
		</s:if>
	</table>
<div align="right">
<shove:page url="money3Days.mht" curPage="${pageBean.pageNum}" showPageCount="5" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>