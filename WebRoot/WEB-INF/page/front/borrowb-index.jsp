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
<div class="wrap kinds-list">
	<div class="w950">
	<div class="ad_center_up"><img src="images/lendmoney_b.png" alt="企业融资" title="企业融资"/></div>
	<div class="tz-step"><img src="images/wyjk_guide2.jpg" alt="企业融资流程" title="企业融资流程"/></div>
    <div class="sqxyed"><div>如果您不急于贷款，可以通过这里先申请信用额度，这将方便您日后迅速获得额度内的贷款</div>
    	<s:if test="#session.user ==null || #session.user.userType==2" >
        	<a href="creditingInit.mht" class="button w200">提升信用额度</a>
        </s:if>
    </div>
    <ul class="kindsnr-list">
    <s:if test="#request.worth==1">
       <li><div class="kindsnr-cnet">
        <h3 class="kind4">净值融资</h3>
        融资企业可以发布的不超过其平台账号净值额度的融资金额，但融资企业提现将受到限制。这是一种安全系数相对较高的融资标，因此利率方面可能比较低。净值标通常用于临时周转，使资金利用率最大化。
        <div class="kindsnr-link4 fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
        		<a class="button w200"  href="addBorrowInit.mht?t=1">净值融资</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>净值融资</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.seconds==1">
        <li><div class="kindsnr-cnet">
        <h3 class="kind5">秒还融资</h3>
       融资企业标满瞬间送出利息，免手续费、自动审核、自动还款，不定期送出秒还标。
        <div class="kindsnr-link5 fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
        		<a class="button w200"  href="addBorrowInit.mht?t=2">秒还标</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>秒还标</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.ordinary==1">
          <li><div class="kindsnr-cnet">
        <h3 class="kind2">信用融资</h3>
       信用融资标是一种将企业的信用运用于融资之中，免抵押、免担保的小额企业信用贷款标，SP2P网贷通过严格审核，对融资企业给予信用评级，授予信用额度，允许其在平台发布贷款信息。
        <div class="kindsnr-link2 fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
        		<a class="button w200"  href="addBorrowInit.mht?t=3">信用融资</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>信用融资</span>
        	</s:else>
        </div>
        </div></li>
         </s:if>
        <s:if test="#request.field==1">
          <li><div class="kindsnr-cnet">
        <h3 class="kind3">实地考察融资</h3>
       小微企业现场考察审批融资；汽车、房产、货物等融资；逾期24小时内赔付。
        <div class="kindsnr-link3 fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
        		<a class="button w200"  href="addBorrowInit.mht?t=4">实地考察融资</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>实地考察融资</span>
        	</s:else>
        </div>
        </div></li>
         </s:if>
        <s:if test="#request.institutions==1">
    	<li><div class="kindsnr-cnet">
        <h3 class="kind1">担保融资</h3>
        是指三好资本的合作伙伴为相应的融资提供连带保证，并负有连带保证责任的融资。
（机构担保标需要通过机构担保认证） 
        <div class="kindsnr-link fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
        		<a class="button w200" href="addBorrowInit.mht?t=5">担保融资</a>
        	</s:if>
        	<s:else>
        		<span style='display:block;float:right;text-align:center;color:#ffffff;background:url(images/zhuche_03_b.jpg) no-repeat;width:100px;height:34px;line-height:32px;'>担保融资</span>
        	</s:else>
        </div>
        </div></li>
        </s:if>
        <s:if test="#request.transfer==1">
    	<li><div class="kindsnr-cnet">
        <h3 class="kind1">债权转让</h3>
        债权转让（CreditAssignment）债权转让又称“债权让与”，是指在不改变合同内容的合同转让，债权人通过债权转让第三人订立合同将债权的全部或部分转移于第三人。
        <div class="kindsnr-link fr">
        	<s:if test="#session.user ==null || #session.user.userType==2" >
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
<script>
$(function(){
    //样式选中
dqzt(2);
});		     
</script>
</body>
</html>
