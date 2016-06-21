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
<div class="kinds-list wrap">
	<div class="w950">
	<div class="ad_center_up"><img src="images/lendmoney_p.png" alt="个人借款" title="个人借款"/></div>
	<div class="tz-step"><img src="images/wyjk_guide1.jpg" alt="个人借款流程" title="个人借款流程" /></div>
    <div class="sqxyed"><div>如果您不急于贷款，可以通过这里先申请信用额度，这将方便您日后迅速获得额度内的贷款</div>
    	<s:if test="#session.user ==null || #session.user.userType==1" >
        	<a href="creditingInit.mht" class="button w140">提升信用额度</a>
        </s:if>
    </div>
    <ul class="kindsnr-list">
    <s:if test="#request.worth==1">
       <li><div class="kindsnr-cnet">
        <h3 class="kind4">净值借款</h3>
        借款人可以发布的不超过其平台账号净值额度的借款金额，但借款人提现将受到限制。这是一种安全系数相对较高的借款标，因此利率方面可能比较低。净值标通常用于临时周转，使资金利用率最大化。
        <div class="kindsnr-link4">
        	
      		<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a href="addBorrowInit.mht?t=1"  class="button w140">净值借款</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>净值借款</span>
        	</s:else>
        	
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.seconds==1">
        <li><div class="kindsnr-cnet">
        <h3 class="kind5">秒还借款</h3>
       借款人标满瞬间送出利息，免手续费、自动审核、自动还款，不定期送出秒还标。
        <div class="kindsnr-link5">
        	<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a href="addBorrowInit.mht?t=2" class="button w140">秒还标</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>秒还标</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.ordinary==1">
          <li><div class="kindsnr-cnet">
        <h3 class="kind2">信用借款</h3>
       信用借款标是一种将个人的信用运用于借款之中，免抵押、免担保的小额个人信用贷款标，SP2P网贷通过严格审核，对借款人给予信用评级，授予信用额度，允许其在平台发布贷款信息。主要面向对象一般为18-60周岁有稳定收入，具有较强偿还能力的公民。
        <div class="kindsnr-link2">
        	<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a href="addBorrowInit.mht?t=3" class="button w140">信用借款</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>信用借款</span>
        	</s:else>
        </div>
        </div></li>
         </s:if>
        <s:if test="#request.field==1">
 <!--        <li><div class="kindsnr-cnet">
        <h3 class="kind3">实地考察借款</h3>
       小微企业现场考察审批借款；汽车、房产、货物等借款；逾期24小时内赔付。
        <div class="kindsnr-link3">
        	<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a href="addBorrowInit.mht?t=4">实地考察借款</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>实地考察借款</span>
        	</s:else>
        </div>
        </div></li>-->   
         </s:if>
        <s:if test="#request.institutions==1">
    	<li><div class="kindsnr-cnet">
        <h3 class="kind1">担保借款</h3>
        是指三好资本的合作伙伴为相应的借款提供连带保证，并负有连带保证责任的借款。
（机构担保标需要通过机构担保认证） 

        <div class="kindsnr-link">
       		<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a href="addBorrowInit.mht?t=5" class="button w140">担保借款</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>担保借款</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.transfer==1">
    	<li><div class="kindsnr-cnet">
        <h3 class="kind1">债权转让</h3>
        债权转让（CreditAssignment）债权转让又称“债权让与”，是指在不改变合同内容的合同转让，债权人通过债权转让第三人订立合同将债权的全部或部分转移于第三人。
        <div class="kindsnr-link fr">
        	<s:if test="#session.user ==null || #session.user.userType==1" >
        		<a class="button w200" href="addBorrowInit.mht?t=7">债权转让</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>债权转让</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <%--
         <li><div class="kindsnr-cnet">
        <h3 class="kind5">流转标</h3>
       流转标投标即成功,到期网站垫付,零风险
        <div class="kindsnr-link5"><a href="addBorrowInit.mht?t=6">流转标</a></div>
        </div></li>
    --%></ul>
    </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

</body>
</html>
