<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<table  id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
  <tr class=gvHeader>
    <th align="center">序号</th>
    <th align="center">用户名</th>
    <th align="center">真名 </th>
    <th align="center">目前级别 </th>
    <th align="center"> 申请级别</th>
    <th align="center">个人投资 </th>
    <th align="center">银牌下线数 </th>
    <th align="center">铜牌下线数 </th>
    <th align="center">申请时间</th>
    <th align="center">审核</th>
  </tr>
 <s:if test="pageBean.page!=null && pageBean.page.size>0">
 <s:set name="counts" value="#request.pageNum"/>
	<s:iterator value="pageBean.page" var="bean" status="s">
		<tr class="gvItem">
			<td align="center">
				<s:property value="#s.count+#counts"/>
			</td>
          <td align="center">${bean.username}</td>
          <td align="center">${bean.realName} </td>
          <td align="center">
          <s:if test="%{#bean.myLevel==0}">普通会员</a></s:if>
          <s:elseif test="%{#bean.myLevel==1}">铜牌理财师</s:elseif>
          <s:elseif test="%{#bean.myLevel==2}">银牌理财师</s:elseif>
          <s:elseif test="%{#bean.myLevel==3}">金牌理财师</s:elseif>
          </td>
          <td align="center">
          <s:if test="%{#bean.myLevel==0 && #bean.appLevel==1}"><a href="javascript:void(0);" onclick="audit('${id}',0)">铜牌理财师</a></s:if>
          <s:elseif test="%{#bean.myLevel==1 && #bean.appLevel==1}"><a href="javascript:void(0);" onclick="audit('${id}',1)">银牌理财师</a></s:elseif>
          <s:elseif test="%{#bean.myLevel==2 && #bean.appLevel==1}"><a href="javascript:void(0);" onclick="audit('${id}',2)">金牌理财师</a></s:elseif>
          <s:elseif test="%{#bean.myLevel==3 && #bean.appLevel==1}"></s:elseif>
          </td>
          <td align="center">${bean.sumMoney} </td>
          <td align="center">${bean.ct2} </td>
          <td align="center">${bean.ct1} </td>
          <td align="center"><s:date name="#bean.createTime" format="yyyy-MM-dd HH:mm:ss" />	 </td>
          <td align="center">
          <s:if test="%{#bean.appLevel==1}">
          <a href="#" onclick="auditFinancial(${bean.id})">通过</a> / <a href="#" onclick="auditFinancialNo(${bean.id})">不通过</a>
          </s:if>
          <s:else>
          <s:if test="%{#bean.myLevel!=0}">
          <a href="#" onclick="auditLow(${bean.id})">降级</a>
          </s:if>
          </s:else>
          </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="7" align="center">没有数据</td>
  </s:else>
</table>

<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
function auditFinancial(id){
	if (confirm("是否审核通过？")){
		var url = "auditFinancial.mht"//?id=+id;
		param["paramMap.id"]=id;
		param["paramMap.pass"]="1";
		$.dispatchPost(url,param,initCallBack2);
	}
}
function auditFinancialNo(id){
	var url = "auditFinancial.mht"//?id=+id;
	param["paramMap.id"]=id;
	if (confirm("是否审核为不通过？")){
		param["paramMap.pass"]="0";
	} else {
		return;
	}
	$.dispatchPost(url,param,initCallBack3);
}
function initCallBack2(){
	alert("审核通过");
	window.location.href="financeAudit.mht";
}
function initCallBack3(){
	alert("审核不通过");
	window.location.href="financeAudit.mht";
}
function initCallBack4(){
	alert("成功降级");
	window.location.href="financeAudit.mht";
}
function auditLow(id){
	if (confirm("是否降级？")){
		var url = "auditFinancial.mht"//?id=+id;
		param["paramMap.id"]=id;
		param["paramMap.pass"]="3";
		$.dispatchPost(url,param,initCallBack4);
	}
}
function audit(id,level){
	  var url= "queryAuditCondition.mht?";
      $.jBox("iframe:"+url, {
      title: "级别说明",
      width: 800,
      height: 450,
      buttons: { '关闭': false }});
}
</script>
