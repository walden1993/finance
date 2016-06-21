<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
  <ul class="ofical_ul" id="investRank">
          <s:if test="#request.rankList!=null || #request.rankList.size>0">
            <s:iterator value="#request.rankList" var="rank" status="step">
              <li><span class="top_loan_bg top_rest"></span><span class="top_name">${rank.username}</span><span class="top_count">￥${rank.sumMoney }</span></li>
            </s:iterator>
          </s:if>
          <s:else>
            <li><span>暂无排名</span></li>
          </s:else>
</ul>