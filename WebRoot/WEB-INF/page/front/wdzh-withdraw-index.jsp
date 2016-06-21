<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
   <%@include file="/include/taglib.jsp" %>
<div class="tab_a">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="leve_titbj">
    <th>申请时间</th>
    <th> 提现金额</th>
    <th> 手续费</th>
    <th> 提现银行</th>
    <th>卡号后4位 </th>
    <th>状态 </th>
    <th>操作 </th>
    </tr>
    
    <s:if test="pageBean.page!=null && pageBean.page.size>0">
       <s:iterator value="pageBean.page"  var="bean">
	    <tr>
	       <td align="center"><s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
	       <td align="center">${bean.sum}</td>
	       <td align="center">${bean.poundage}</td>
	       <td align="center">${bean.bankName}</td>
	       <td align="center">${bean.acount4}</td>
	      
	       <s:if test="#bean.status == 1">
	          <td align="center">审核中</td>
	       	  <td align="center"><a href="javascript:void(0);" onclick="deleteWithdraw(${bean.id},${bean.sum})">取消</a></td>
	       </s:if>
	       <s:else>
	         <s:if test="#bean.status ==2">
	             <td align="center">已提现</td>
	             <td align="center">--</td>
	         </s:if>
	         <s:elseif test="#bean.status ==3">
	                <td align="center">已取消</td>
	                <td align="center">--</td>
	           </s:elseif>
	            <s:elseif test="#bean.status ==4">
	               <td align="center">转账中</td>
	              <td align="center">--</td>
	            </s:elseif>
	            <s:else>
	                <td align="center">失败</td>
	               <td align="center"><a href="javascript:void(0);" onclick="forReasonAlert('${bean.remark}' )">原因</a></td>
	            </s:else>
	         </s:else>
	    </tr>
		</s:iterator>
	</s:if>
	<s:else>
	    <tr><td colspan="9" align="center">暂无信息</td></tr>
	</s:else>
</table>
</div>
	<div class="page">
	<p>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </p>
    </div>

<script>
   function deleteWithdraw(id,sum){
      param["paramMap.wId"] = id;
      param["paramMap.money"] = sum;
      if(!window.confirm("确定要取消提现记录")){
        return;
      }
      $.dispatchPost("deleteWithdraw.mht",param,function(data){
           if(data.msg == 1){
             alert("取消成功");
             jumpUrl('withdrawLoad.mht');
           }else{
             alert(data.msg);
             return;
           }
      });
   }

   function forReasonAlert(reason){
	  alert(reason);
   }
  

   
</script>