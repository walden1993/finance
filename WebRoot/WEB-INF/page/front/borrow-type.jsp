<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
<div class="w950">
<form id="form" action="addBorrow.mht" method="post">
<div class="nymain">
<div class="bigbox" style="border:none">
<div class="sqdk" style="background: none;">
	<div class="l-nav w030 fl">
    <ul>
    <li><a href="querBaseData.mht"><span>step1</span> 基本资料</a></li>
    <li>
    	<s:if test="#session.user.userType==2">
    		<a href="companyPictureDate.mht?from=1"><span>step2</span>上传资料</a>
    	</s:if>
    	<s:else>
    		<a href="userPassData.mht"><span>step2</span>上传资料</a>
    	</s:else>
    </li>
    <li class="on last"><a href="javascript:void(0);"><span>step3</span> 发布贷款</a></li>
    </ul>
    </div>
    <div class="r-main w070 fl">
   <div class="rmainbox">
		<div class="fbdk">
        <ul><li>
          <div class="btn">
          <a href="addBorrowInit.mht?t=3" class="button w140">信用借款</a>
          </div>
          <p>纯信用贷款，贷款年利率在${minRate }-${maxRate }%之间，额度较小。</p>
        </li><li>
          <div class="btn">
          <a href="addBorrowInit.mht?t=4" class="button w140">实地考察借款</a>
          </div>
          <p>小微企业现场考察审批，需要通过现场认证。（实地认证借款能选择性添加抵押标志）</p>
        </li><li>
          <div class="btn">
          <a href="addBorrowInit.mht?t=5" class="button w140">机构担保借款</a>
          </div>
          <p>是指三好资本的合作伙伴为相应的借款提供连带保证，并负有连带保证责任的借款。</p>
                         （机构担保标需要通过机构担保认证）
        </li>
        </ul>
        </div>
    </div>
    </div>
  </div>
 </div>
 </div>
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/xl.js"></script>
<script>
$(function(){
    //样式选中
  var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
    dqzt(2);
});		     
</script>
</body>
</html>
