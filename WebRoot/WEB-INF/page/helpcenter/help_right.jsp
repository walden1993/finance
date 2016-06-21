<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>

<!--我的主页-->
<div class="myhelpcenter02">
	<div class="myhelpcenter03">
		<span>${list[0].typeDescribe}</span>
	</div>
</div>
<!--我的主页end-->
<div class=" clearfx hjh">
	<!--帮助中心右边1-->
	
		<div class="panel-group left" id="accordion" role="tablist" aria-multiselectable="true">
			<s:iterator value="#request.list" var="list" status="index">
			  <div class="panel panel-default">
			    <div class="panel-heading" role="tab" id="headingOne_${index.index+1}">
			      <h4 class="panel-title">
			        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_${index.index+1}"  <s:if test="%{#index.index==0}">aria-expanded='false'</s:if>  aria-controls="collapseOne_${index.index+1}">
			          <font>${index.index+1}.</font>${question}</span>
			        </a>
			      </h4>
			    </div>
			    <div id="collapseOne_${index.index+1}" class="panel-collapse collapse <s:if test='%{#index.index==0}'>in</s:if>" role="tabpanel" aria-labelledby="headingOne_${index.index+1}">
			      <div class="panel-body">${anwer}
			      </div>
			    </div>
			  </div>
			</s:iterator>
	</div>
	
	
	
	<!--帮助中心右边1end-->

	<!--帮助中心右边2-->
	<jsp:include page="/WEB-INF/page/helpcenter/static_right.jsp"></jsp:include>
	<!--帮助中心右边2end-->
</div>
</div>
