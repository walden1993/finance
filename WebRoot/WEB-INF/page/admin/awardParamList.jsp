<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<<s:if test="#request.list==null || #request.list.size()==0">
<tr><td colspan="9">暂无数据</td></tr>
</s:if>
<s:iterator value="#request.list"  var="bean"  status="index">
<tr>
    <td>${index.index+1}</td>
    <td>
        <s:if test="#bean.status==1">
          中奖
        </s:if>
        <s:else>
        未中奖
        </s:else>
    </td>
    <td>${bean.awardName}</td>
    <td>
    <s:if test="#bean.isUsable==1">
          <font color="red">√</font>
        </s:if>
        <s:else>
        x
        </s:else>
    </td>
    <td>${bean.hitRate}%</td>
    <td>${bean.byOrder}</td>
    <td>${bean.hitTime}</td>
    <td>${bean.realMoney}元</td>
    <td><a href="javascript:deleteParam(${bean.id})">删除</a></td>
</tr>
</s:iterator>